package com.deliveryservice.strategies;

import com.deliveryservice.config.StrategyType;
import com.deliveryservice.models.domain.Courier;
import com.deliveryservice.models.domain.Order;

import java.util.HashMap;
import java.util.Map;

public class MatchedStrategy implements Strategy {
    private final Map<Order, Courier> orderToCourier = new HashMap<>();
    private final Map<Courier, Order> courierToOrder = new HashMap<>();

    @Override
    public Courier getCourierForOrder(Order order) {
        return orderToCourier.get(order);
    }

    @Override
    public Order getOrderForCourier(Courier courier) {
        return courierToOrder.get(courier);
    }

    @Override
    public void submitNewOrderAndCourier(Order order, Courier courier) {
        orderToCourier.put(order, courier);
        courierToOrder.put(courier, order);
    }

    @Override
    public StrategyType getType() {
        return StrategyType.MATCHED;
    }
}
