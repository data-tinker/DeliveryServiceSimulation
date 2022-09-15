package com.deliveryservice.models.domain;

public class Courier {
    private final int id;
    private Long arriveTimeTimestamp = null;

    public Courier(int id) {
        this.id = id;
    }

    public Long getArriveTimeTimestamp() {
        return arriveTimeTimestamp;
    }

    public int getId() {
        return id;
    }

    public void setArriveTimeTimestamp(long arriveTimeTimestamp) {
        this.arriveTimeTimestamp = arriveTimeTimestamp;
    }
}
