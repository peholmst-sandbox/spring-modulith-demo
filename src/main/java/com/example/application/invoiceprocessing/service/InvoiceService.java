package com.example.application.invoiceprocessing.service;

import org.springframework.data.domain.Pageable;

import java.util.List;

public interface InvoiceService {
    List<Invoice> list(Pageable pageable);
}
