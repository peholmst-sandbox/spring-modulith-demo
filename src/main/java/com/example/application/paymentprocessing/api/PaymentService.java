package com.example.application.paymentprocessing.api;

import com.example.application.base.domain.Money;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface PaymentService {

    void registerPayment(Money amount, String referenceNumber);

    List<Payment> list(Pageable pageable);
}
