package com.example.application.orderprocessing;

import com.example.application.orderprocessing.domain.OrderEntity;
import com.example.application.orderprocessing.domain.OrderRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.data.domain.Pageable;
import org.springframework.modulith.moments.support.Moments;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Component
public class OrderProcessing {

    private static final Logger log = LoggerFactory.getLogger(OrderProcessing.class);

    private final Moments moments;
    private final ApplicationEventPublisher eventPublisher;
    private final OrderRepository orderRepository;

    OrderProcessing(Moments moments, ApplicationEventPublisher eventPublisher, OrderRepository orderRepository) {
        this.moments = moments;
        this.eventPublisher = eventPublisher;
        this.orderRepository = orderRepository;
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public Order createOrder(OrderDetails orderDetails) {
        var entity = orderRepository.saveAndFlush(new OrderEntity(orderDetails.description(), orderDetails.amount(), orderDetails.paymentTime(), moments.instant()));
        var dto = createDto(entity);
        eventPublisher.publishEvent(new OrderCreatedEvent(dto));
        log.info("Created order: {}", dto);
        return dto;
    }

    @Transactional(readOnly = true)
    public List<Order> listOrders(Pageable pageable) {
        return orderRepository.findAll(pageable).map(this::createDto).toList();
    }

    private Order createDto(OrderEntity orderEntity) {
        return new Order(orderEntity.getOrderId(), new OrderDetails(orderEntity.getDescription(), orderEntity.getAmount(), orderEntity.getPaymentTime()), orderEntity.getOrderDate());
    }
}
