package com.example.application.invoiceprocessing;

import com.aventrix.jnanoid.jnanoid.NanoIdUtils;
import com.example.application.invoiceprocessing.domain.InvoiceEntity;
import com.example.application.invoiceprocessing.domain.InvoiceRepository;
import com.example.application.invoiceprocessing.service.*;
import com.example.application.orderprocessing.service.OrderCreatedEvent;
import com.example.application.paymentprocessing.service.PaymentReceivedEvent;
import com.example.application.sharedkernel.domain.InterestRate;
import com.example.application.sharedkernel.domain.Money;
import jakarta.annotation.PreDestroy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.event.EventListener;
import org.springframework.data.domain.Pageable;
import org.springframework.modulith.events.ApplicationModuleListener;
import org.springframework.modulith.moments.DayHasPassed;
import org.springframework.modulith.moments.support.Moments;
import org.springframework.stereotype.Component;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.TransactionTemplate;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

@Component
class InvoiceProcessing implements InvoiceService {

    private static final Logger log = LoggerFactory.getLogger(InvoiceProcessing.class);

    private static final Money DEFAULT_LATE_FEE = new Money("15.00");
    private static final InterestRate DEFAULT_INTEREST_RATE = new InterestRate("0.05");

    private final Moments moments;
    private final ApplicationEventPublisher eventPublisher;
    private final InvoiceRepository invoiceRepository;
    private final ExecutorService executorService;
    private final TransactionTemplate transactionTemplate;

    InvoiceProcessing(Moments moments, ApplicationEventPublisher eventPublisher, InvoiceRepository invoiceRepository, PlatformTransactionManager transactionManager) {
        this.moments = moments;
        this.eventPublisher = eventPublisher;
        this.invoiceRepository = invoiceRepository;
        transactionTemplate = new TransactionTemplate(transactionManager);
        transactionTemplate.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRES_NEW);
        log.info("Starting executor service");
        executorService = Executors.newSingleThreadExecutor();
    }

    @PreDestroy
    void destroy() throws InterruptedException {
        log.info("Shutting down executor service");
        executorService.shutdown();
        executorService.awaitTermination(30, TimeUnit.SECONDS);
    }

    @ApplicationModuleListener
    void on(OrderCreatedEvent event) {
        var referenceNumber = NanoIdUtils.randomNanoId();
        var entity = invoiceRepository.saveAndFlush(new InvoiceEntity(event.order().id(), event.order().details().description(), referenceNumber, event.order().details().amount(),
                DEFAULT_LATE_FEE, DEFAULT_INTEREST_RATE, moments.today(), event.order().details().paymentTime()));
        var dto = createDto(entity);
        eventPublisher.publishEvent(new InvoiceCreatedEvent(dto));
        log.info("Created invoice: {}", dto);
    }

    @ApplicationModuleListener
    void on(PaymentReceivedEvent event) {
        invoiceRepository.findByReferenceNumber(event.payment().referenceNumber())
                .filter(invoice -> !invoice.isPaid())
                .ifPresent(invoice -> {
                    invoice.addPayment(event.payment().amount());
                    invoiceRepository.save(invoice);
                    var dto = createDto(invoice);
                    log.info("Received payment for invoice: {}", dto);
                    if (invoice.isPaid()) {
                        eventPublisher.publishEvent(new InvoicePaidEvent(dto));
                    }
                });
    }

    @EventListener
    void on(DayHasPassed event) {
        // This method uses its own executor service instead of @Async because of the time machine. If we jump ahead one
        // week, a lot of events will be fired at the same time. When using @Async, they may start executing at the
        // same time, resulting in some events being processed in the wrong order. By limiting the executor service to
        // one thread and a queue, we can be sure the events are always processed in the same order.
        //
        // Because we use programmatic event execution, we have to use programmatic transaction management as well,
        // since transactions are scoped to the current thread.
        executorService.execute(() -> {
            log.info("Checking for overdue invoices on: {}", event.getDate());
            transactionTemplate.executeWithoutResult(tx -> invoiceRepository.findOverdue(event.getDate())
                    .filter(invoice -> !invoice.isPaid())
                    .forEach(invoice -> {
                        invoice.addLateFee();
                        invoiceRepository.save(invoice);
                        var dto = createDto(invoice);
                        log.info("Late fee added to invoice: {}", dto);
                        eventPublisher.publishEvent(new InvoiceOverdueEvent(dto));
                    })
            );
        });
    }

    @Override
    @Transactional(readOnly = true)
    public List<Invoice> list(Pageable pageable) {
        return invoiceRepository.findAll(pageable).map(this::createDto).toList();
    }

    private Invoice createDto(InvoiceEntity entity) {
        return new Invoice(entity.getInvoiceId(), entity.getOrderId(), entity.getDescription(), entity.getReferenceNumber(), entity.getAmount(), entity.getInvoiceDate(), entity.getDueDate(), entity.isPaid());
    }
}
