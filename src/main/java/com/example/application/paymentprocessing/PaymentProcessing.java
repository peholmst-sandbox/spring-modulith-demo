package com.example.application.paymentprocessing;

import com.example.application.paymentprocessing.domain.PaymentEntity;
import com.example.application.paymentprocessing.domain.PaymentRepository;
import com.example.application.sharedkernel.domain.Money;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.data.domain.Pageable;
import org.springframework.modulith.moments.support.Moments;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Component
public class PaymentProcessing {

    private static final Logger log = LoggerFactory.getLogger(PaymentProcessing.class);

    private final Moments moments;
    private final ApplicationEventPublisher eventPublisher;
    private final PaymentRepository paymentRepository;

    PaymentProcessing(Moments moments, ApplicationEventPublisher eventPublisher, PaymentRepository paymentRepository) {
        this.moments = moments;
        this.eventPublisher = eventPublisher;
        this.paymentRepository = paymentRepository;
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void registerPayment(Money amount, String referenceNumber) {
        var entity = paymentRepository.saveAndFlush(new PaymentEntity(amount, referenceNumber, moments.instant()));
        var dto = createDto(entity);
        eventPublisher.publishEvent(new PaymentReceivedEvent(dto));
        log.info("Registered Payment: {}", dto);
    }

    @Transactional(readOnly = true)
    public List<Payment> list(Pageable pageable) {
        return paymentRepository.findAll(pageable).map(this::createDto).toList();
    }

    private Payment createDto(PaymentEntity entity) {
        return new Payment(entity.getPaymentId(), entity.getAmount(), entity.getReferenceNumber(), entity.getPaymentDate());
    }
}
