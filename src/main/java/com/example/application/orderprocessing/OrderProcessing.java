package com.example.application.orderprocessing;

import com.example.application.orderprocessing.api.Order;
import com.example.application.orderprocessing.api.OrderCreatedEvent;
import com.example.application.orderprocessing.api.OrderDetails;
import com.example.application.orderprocessing.api.OrderService;
import com.example.application.orderprocessing.domain.OrderEntity;
import com.example.application.orderprocessing.domain.OrderRepository;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.time.Clock;

@Component
class OrderProcessing implements OrderService {

    private final Clock clock;
    private final ApplicationEventPublisher eventPublisher;
    private final OrderRepository orderRepository;

    OrderProcessing(Clock clock, ApplicationEventPublisher eventPublisher, OrderRepository orderRepository) {
        this.clock = clock;
        this.eventPublisher = eventPublisher;
        this.orderRepository = orderRepository;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public Order createOrder(OrderDetails orderDetails) {
        var entity = orderRepository.saveAndFlush(new OrderEntity(orderDetails.description(), orderDetails.amount(), orderDetails.paymentTime(), clock.instant()));
        var dto = createDto(entity);
        eventPublisher.publishEvent(new OrderCreatedEvent(dto));
        return dto;
    }

    private Order createDto(OrderEntity orderEntity) {
        return new Order(orderEntity.getOrderId(), new OrderDetails(orderEntity.getDescription(), orderEntity.getAmount(), orderEntity.getPaymentTime()), orderEntity.getOrderDate());
    }
}
