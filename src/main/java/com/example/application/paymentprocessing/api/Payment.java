package com.example.application.paymentprocessing.api;

import com.example.application.base.domain.Money;

import java.time.Instant;

public record Payment(Long paymentId, Money amount, String referenceNumber, Instant paymentDate) {
}
