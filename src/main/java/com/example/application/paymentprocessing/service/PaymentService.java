package com.example.application.paymentprocessing.service;

import com.example.application.sharedkernel.domain.Money;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface PaymentService {

    void registerPayment(Money amount, String referenceNumber);

    List<Payment> list(Pageable pageable);
}
