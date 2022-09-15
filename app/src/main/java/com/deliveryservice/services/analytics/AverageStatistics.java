package com.deliveryservice.services.analytics;

public class AverageStatistics implements Statistics {
    private int numberOfObjects = 0;
    private double sum = 0;
    private double average = 0;

    @Override
    public synchronized void submitValue(double value) {
        ++numberOfObjects;
        sum += value;
        average = sum / numberOfObjects;
    }

    @Override
    public double getStatistics() {
        return average;
    }
}
