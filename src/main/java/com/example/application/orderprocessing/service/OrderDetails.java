package com.example.application.orderprocessing.service;

import com.example.application.sharedkernel.domain.Money;

import java.time.Period;

public record OrderDetails(String description, Money amount, Period paymentTime) {
}
