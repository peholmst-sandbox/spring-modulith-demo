package com.example.application.orderprocessing.api;

import com.example.application.base.domain.Money;

import java.time.Period;

public record OrderDetails(String description, Money amount, Period paymentTime) {
}
