package com.deliveryservice.services.analytics;

import com.deliveryservice.models.domain.Courier;
import com.deliveryservice.models.domain.Order;

public class DefaultAnalyticsService implements AnalyticsService {
    private final Statistics couriersStatistics;
    private final Statistics ordersStatistics;

    public DefaultAnalyticsService(
        Statistics couriersStatistics,
        Statistics ordersStatistics
    ) {
        this.couriersStatistics = couriersStatistics;
        this.ordersStatistics = ordersStatistics;
    }

    @Override
    public synchronized long sendCourierArrivalEvent(Order order, long currentTimestamp) {
        long orderWaitingTimeInMs = currentTimestamp - order.getReadyTimeTimestamp();
        ordersStatistics.submitValue(orderWaitingTimeInMs);
        couriersStatistics.submitValue(0);
        return orderWaitingTimeInMs;
    }

    @Override
    public synchronized long sendOrderPickupEvent(Courier courier, long currentTimestamp) {
        long courierWaitingTimeInMs = currentTimestamp - courier.getArriveTimeTimestamp();
        couriersStatistics.submitValue(courierWaitingTimeInMs);
        ordersStatistics.submitValue(0);
        return courierWaitingTimeInMs;
    }

    @Override
    public double getAverageCourierWaitTimeInMs() {
        return couriersStatistics.getStatistics();
    }

    @Override
    public double getAverageOrderWaitTimeInMs() {
        return ordersStatistics.getStatistics();
    }
}
