package com.deliveryservice.services;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

public interface ScheduledService {
    ScheduledExecutorService scheduler =
        Executors.newSingleThreadScheduledExecutor();
}
