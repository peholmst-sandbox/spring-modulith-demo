package com.example.application.paymentprocessing.domain;

import com.example.application.sharedkernel.domain.Money;
import com.example.application.sharedkernel.domain.MoneyAttributeConverter;
import jakarta.persistence.*;

import java.time.Instant;
import java.util.Objects;

@Entity
public class PaymentEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long paymentId;
    @Convert(converter = MoneyAttributeConverter.class)
    private Money amount;
    private String referenceNumber;
    private Instant paymentDate;

    protected PaymentEntity() {
    }

    public PaymentEntity(Money amount, String referenceNumber, Instant paymentDate) {
        this.referenceNumber = referenceNumber;
        this.amount = amount;
        this.paymentDate = paymentDate;
    }

    public Long getPaymentId() {
        return Objects.requireNonNull(paymentId);
    }

    public String getReferenceNumber() {
        return referenceNumber;
    }

    public Money getAmount() {
        return amount;
    }

    public Instant getPaymentDate() {
        return paymentDate;
    }
}
