package com.deliveryservice.mappers;

import com.deliveryservice.models.domain.Order;
import com.deliveryservice.models.entities.OrderEntity;

public class OrderMapper {
    static public Order mapOrderEntityToOrderDomain(OrderEntity orderEntity) {
        return new Order(
            orderEntity.id(),
            orderEntity.name(),
            orderEntity.prepTime()
        );
    }
}
