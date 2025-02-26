package com.example.application.orderprocessing.service;

import java.time.Instant;

public record Order(Long id, OrderDetails details, Instant orderDate) {
}
