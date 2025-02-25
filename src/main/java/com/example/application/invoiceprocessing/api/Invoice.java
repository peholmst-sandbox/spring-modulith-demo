package com.example.application.invoiceprocessing.api;

import com.example.application.base.domain.Money;

import java.time.LocalDate;

public record Invoice(Long invoiceId, Long orderId, String description, String referenceNumber, Money amount,
                      LocalDate invoiceDate, LocalDate dueDate, boolean paid) {
}
