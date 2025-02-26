package com.example.application.invoiceprocessing.domain;

import com.example.application.sharedkernel.domain.InterestRate;
import com.example.application.sharedkernel.domain.InterestRateAttributeConverter;
import com.example.application.sharedkernel.domain.Money;
import com.example.application.sharedkernel.domain.MoneyAttributeConverter;
import jakarta.persistence.*;

import java.time.LocalDate;
import java.time.Period;
import java.util.Objects;

@Entity
public class InvoiceEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long invoiceId;
    private Long orderId;
    private String description;
    private String referenceNumber;
    @Convert(converter = MoneyAttributeConverter.class)
    private Money amount;
    @Convert(converter = MoneyAttributeConverter.class)
    private Money lateFee;
    @Convert(converter = InterestRateAttributeConverter.class)
    private InterestRate interestRate;
    private LocalDate invoiceDate;
    private String paymentTime;
    private LocalDate dueDate;
    private boolean paid;

    protected InvoiceEntity() {
    }

    public InvoiceEntity(Long orderId, String description, String referenceNumber, Money amount, Money lateFee, InterestRate interestRate, LocalDate invoiceDate, Period paymentTime) {
        this.orderId = orderId;
        this.description = description;
        this.referenceNumber = referenceNumber;
        this.amount = amount;
        this.lateFee = lateFee;
        this.interestRate = interestRate;
        this.invoiceDate = invoiceDate;
        this.paymentTime = paymentTime.toString();
        this.dueDate = invoiceDate.plus(paymentTime);
        this.paid = false;
    }

    public Long getInvoiceId() {
        return Objects.requireNonNull(invoiceId);
    }

    public Long getOrderId() {
        return orderId;
    }

    public String getDescription() {
        return description;
    }

    public String getReferenceNumber() {
        return referenceNumber;
    }

    public Money getAmount() {
        return amount;
    }

    public Money getLateFee() {
        return lateFee;
    }

    public InterestRate getInterestRate() {
        return interestRate;
    }

    public LocalDate getInvoiceDate() {
        return invoiceDate;
    }

    public Period getPaymentTime() {
        return Period.parse(paymentTime);
    }

    public LocalDate getDueDate() {
        return dueDate;
    }

    public boolean isPaid() {
        return paid;
    }

    public void addPayment(Money paidAmount) {
        if (paid) {
            throw new IllegalStateException("Invoice is already paid");
        }
        if (!paidAmount.isPositive()) {
            throw new IllegalArgumentException("Amount must be positive");
        }
        amount = amount.subtract(paidAmount);
        paid = !amount.isPositive();
    }

    public void addLateFee() {
        if (paid) {
            throw new IllegalStateException("Invoice is already paid");
        }
        amount = amount.add(interestRate.calculateInterest(this.amount)).add(lateFee);
        dueDate = dueDate.plus(getPaymentTime());
    }
}
