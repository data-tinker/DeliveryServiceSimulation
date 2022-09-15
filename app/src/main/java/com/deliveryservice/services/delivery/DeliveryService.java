package com.deliveryservice.services.delivery;

import com.deliveryservice.models.domain.Courier;
import com.deliveryservice.models.domain.Order;
import com.deliveryservice.services.ScheduledService;

public interface DeliveryService extends ScheduledService {
    void startProcessingOrders();

    void onOrderFinished(Order order);

    void onCourierArrived(Courier courier);
}
