package com.example.application.orderprocessing.api;

import java.time.Instant;

public record Order(Long id, OrderDetails details, Instant orderDate) {
}
