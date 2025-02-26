package com.example.application.paymentprocessing;

import com.example.application.sharedkernel.domain.Money;

import java.time.Instant;

public record Payment(Long paymentId, Money amount, String referenceNumber, Instant paymentDate) {
}
