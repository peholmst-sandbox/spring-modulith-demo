package com.example.application.paymentprocessing.domain;

import com.example.application.base.domain.Money;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.Objects;

@Entity
public class PaymentEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long paymentId;
    private String currency;
    private BigDecimal amount;
    private String referenceNumber;
    private Instant paymentDate;

    protected PaymentEntity() {
    }

    public PaymentEntity(Money amount, String referenceNumber, Instant paymentDate) {
        this.referenceNumber = referenceNumber;
        this.amount = amount.getAmount();
        this.currency = amount.getCurrencyCode();
        this.paymentDate = paymentDate;
    }

    public Long getPaymentId() {
        return Objects.requireNonNull(paymentId);
    }

    public String getReferenceNumber() {
        return referenceNumber;
    }

    public Money getAmount() {
        return new Money(amount, currency);
    }

    public Instant getPaymentDate() {
        return paymentDate;
    }
}
