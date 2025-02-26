package com.example.application.orderprocessing.service;

import org.springframework.data.domain.Pageable;

import java.util.List;

public interface OrderService {

    Order createOrder(OrderDetails orderDetails);

    List<Order> list(Pageable pageable);
}
