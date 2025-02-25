package com.example.application.orderprocessing.api;

import org.springframework.data.domain.Pageable;

import java.util.List;

public interface OrderService {

    Order createOrder(OrderDetails orderDetails);

    List<Order> list(Pageable pageable);
}
