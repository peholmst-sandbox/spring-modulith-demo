package com.example.application.paymentprocessing.api;

import com.example.application.base.domain.Money;

public interface PaymentService {

    void registerPayment(Money amount, String referenceNumber);
}
