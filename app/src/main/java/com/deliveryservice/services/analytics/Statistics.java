package com.deliveryservice.services.analytics;

public interface Statistics {
    void submitValue(double value);

    double getStatistics();
}
