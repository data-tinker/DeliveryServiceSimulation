package com.deliveryservice.services.analytics;

import com.deliveryservice.models.domain.Courier;
import com.deliveryservice.models.domain.Order;

public interface AnalyticsService {
    long sendCourierArrivalEvent(Order order, long currentTimestamp);

    long sendOrderPickupEvent(Courier courier, long currentTimestamp);

    double getAverageCourierWaitTimeInMs();

    double getAverageOrderWaitTimeInMs();
}
