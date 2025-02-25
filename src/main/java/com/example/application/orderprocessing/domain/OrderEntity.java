package com.example.application.orderprocessing.domain;

import com.example.application.base.domain.Money;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.Period;
import java.util.Objects;

@Entity
public class OrderEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long orderId;
    private String description;
    private String currency;
    private BigDecimal amount;
    private String paymentTime;
    private Instant orderDate;

    protected OrderEntity() {
    }

    public OrderEntity(String description, Money amount, Period paymentTime, Instant orderDate) {
        this.description = description;
        this.amount = amount.getAmount();
        this.currency = amount.getCurrencyCode();
        this.paymentTime = paymentTime.toString();
        this.orderDate = orderDate;
    }

    public Long getOrderId() {
        return Objects.requireNonNull(orderId);
    }

    public String getDescription() {
        return description;
    }

    public Money getAmount() {
        return new Money(amount, currency);
    }

    public Period getPaymentTime() {
        return Period.parse(paymentTime);
    }

    public Instant getOrderDate() {
        return orderDate;
    }
}
