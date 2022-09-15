package com.deliveryservice.models.domain;

public class Order {
    private final String id;
    private final String name;
    private final int prepTime;
    private Long readyTimeTimestamp = null;

    public Order(
        String id,
        String name,
        int prepTime
    ) {
        this.id = id;
        this.name = name;
        this.prepTime = prepTime;
    }

    public Long getReadyTimeTimestamp() {
        return readyTimeTimestamp;
    }

    public void setReadyTimeTimestamp(long readyTimeTimestamp) {
        this.readyTimeTimestamp = readyTimeTimestamp;
    }

    public long getPrepTime() {
        return prepTime;
    }

    public String getId() {
        return id;
    }
}
