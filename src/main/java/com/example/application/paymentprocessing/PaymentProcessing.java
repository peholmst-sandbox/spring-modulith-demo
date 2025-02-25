package com.example.application.paymentprocessing;

import com.example.application.base.domain.Money;
import com.example.application.paymentprocessing.api.Payment;
import com.example.application.paymentprocessing.api.PaymentReceivedEvent;
import com.example.application.paymentprocessing.api.PaymentService;
import com.example.application.paymentprocessing.domain.PaymentEntity;
import com.example.application.paymentprocessing.domain.PaymentRepository;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.time.Clock;

@Component
class PaymentProcessing implements PaymentService {

    private final Clock clock;
    private final ApplicationEventPublisher eventPublisher;
    private final PaymentRepository paymentRepository;

    PaymentProcessing(Clock clock, ApplicationEventPublisher eventPublisher, PaymentRepository paymentRepository) {
        this.clock = clock;
        this.eventPublisher = eventPublisher;
        this.paymentRepository = paymentRepository;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void registerPayment(Money amount, String referenceNumber) {
        var entity = paymentRepository.saveAndFlush(new PaymentEntity(amount, referenceNumber, clock.instant()));
        var dto = createDto(entity);
        eventPublisher.publishEvent(new PaymentReceivedEvent(dto));
    }

    private Payment createDto(PaymentEntity entity) {
        return new Payment(entity.getPaymentId(), entity.getAmount(), entity.getReferenceNumber(), entity.getPaymentDate());
    }
}
