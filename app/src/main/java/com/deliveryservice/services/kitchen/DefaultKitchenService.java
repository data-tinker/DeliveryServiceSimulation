package com.deliveryservice.services.kitchen;

import com.deliveryservice.models.domain.Order;
import com.deliveryservice.services.delivery.DefaultDeliveryService;
import com.deliveryservice.services.delivery.DeliveryService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.time.Instant;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

public class DefaultKitchenService implements KitchenService {
    private DeliveryService deliveryService;
    private final AtomicInteger ordersInProcess = new AtomicInteger(0);
    private final static Logger logger = LogManager.getLogger(DefaultDeliveryService.class);

    @Override
    public void setDeliveryService(DeliveryService deliveryService) {
        this.deliveryService = deliveryService;
    }

    @Override
    public void startWorkingOnOrder(Order order) {
        ordersInProcess.getAndIncrement();
        long prepTime = order.getPrepTime();
        scheduler.schedule(() -> finishWorkingOnOrder(order), prepTime, TimeUnit.SECONDS);
        logger.info("Order {} started preparing, it will be ready in {} seconds", order.getId(), prepTime);
    }

    @Override
    public void finishWorkingOnOrder(Order order) {
        ordersInProcess.getAndDecrement();
        order.setReadyTimeTimestamp(Instant.now().toEpochMilli());
        deliveryService.onOrderFinished(order);
    }

    @Override
    public boolean allOrdersProcessed() {
        return ordersInProcess.get() == 0;
    }
}
