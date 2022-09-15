package com.deliveryservice.strategies;

import com.deliveryservice.config.StrategyType;
import com.deliveryservice.models.domain.Courier;
import com.deliveryservice.models.domain.Order;

import java.util.Queue;
import java.util.concurrent.LinkedBlockingQueue;

public class FifoStrategy implements Strategy {
    Queue<Order> ordersQueue = new LinkedBlockingQueue<>();
    Queue<Courier> couriersQueue = new LinkedBlockingQueue<>();

    @Override
    public Courier getCourierForOrder(Order order) {
        if (!couriersQueue.isEmpty()) {
            return couriersQueue.poll();
        }
        ordersQueue.offer(order);
        return null;
    }

    @Override
    public Order getOrderForCourier(Courier courier) {
        if (!ordersQueue.isEmpty()) {
            return ordersQueue.poll();
        }
        couriersQueue.offer(courier);
        return null;
    }

    @Override
    public void submitNewOrderAndCourier(Order order, Courier courier) {
    }

    @Override
    public StrategyType getType() {
        return StrategyType.FIFO;
    }
}
