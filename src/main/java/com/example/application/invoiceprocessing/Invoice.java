package com.example.application.invoiceprocessing;

import com.example.application.sharedkernel.domain.Money;

import java.time.LocalDate;

public record Invoice(Long id, Long orderId, String description, String referenceNumber, Money amount,
                      LocalDate invoiceDate, LocalDate dueDate, boolean paid) {
}
