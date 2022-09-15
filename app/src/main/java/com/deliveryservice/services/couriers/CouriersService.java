package com.deliveryservice.services.couriers;

import com.deliveryservice.models.domain.Courier;
import com.deliveryservice.services.ScheduledService;
import com.deliveryservice.services.delivery.DeliveryService;

public interface CouriersService extends ScheduledService {
    void setDeliveryService(DeliveryService deliveryService);

    Courier dispatchCourier();

    void courierIsReady(Courier courier);

    boolean allCouriersProcessed();
}
