package com.deliveryservice.strategies;

import com.deliveryservice.config.StrategyType;
import com.deliveryservice.models.domain.Courier;
import com.deliveryservice.models.domain.Order;

public interface Strategy {
    Courier getCourierForOrder(Order order);
    Order getOrderForCourier(Courier courier);
    void submitNewOrderAndCourier(Order order, Courier courier);
    StrategyType getType();
}
