package com.deliveryservice.services.kitchen;

import com.deliveryservice.models.domain.Order;
import com.deliveryservice.services.ScheduledService;
import com.deliveryservice.services.delivery.DeliveryService;

public interface KitchenService extends ScheduledService {
    void setDeliveryService(DeliveryService deliveryService);

    void startWorkingOnOrder(Order orderEntity);

    void finishWorkingOnOrder(Order order);

    boolean allOrdersProcessed();
}
