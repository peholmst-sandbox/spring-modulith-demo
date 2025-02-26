package com.example.application.orderprocessing.domain;

import com.example.application.sharedkernel.domain.Money;
import com.example.application.sharedkernel.domain.MoneyAttributeConverter;
import jakarta.persistence.*;

import java.time.Instant;
import java.time.Period;
import java.util.Objects;

@Entity
public class OrderEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long orderId;
    private String description;
    @Convert(converter = MoneyAttributeConverter.class)
    private Money amount;
    private String paymentTime;
    private Instant orderDate;

    protected OrderEntity() {
    }

    public OrderEntity(String description, Money amount, Period paymentTime, Instant orderDate) {
        this.description = description;
        this.amount = amount;
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
        return amount;
    }

    public Period getPaymentTime() {
        return Period.parse(paymentTime);
    }

    public Instant getOrderDate() {
        return orderDate;
    }
}
