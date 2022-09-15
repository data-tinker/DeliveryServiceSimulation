package com.deliveryservice.models.errors;

public class OrdersLoadError extends RuntimeException {
    public OrdersLoadError(String dataFileName) {
        super(String.format("Error when loading orders from file %s", dataFileName));
    }
}
