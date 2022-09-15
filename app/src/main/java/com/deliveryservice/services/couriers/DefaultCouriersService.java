package com.deliveryservice.services.couriers;

import com.deliveryservice.config.AppConfig;
import com.deliveryservice.config.CourierRandomWindow;
import com.deliveryservice.models.domain.Courier;
import com.deliveryservice.services.delivery.DefaultDeliveryService;
import com.deliveryservice.services.delivery.DeliveryService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.time.Instant;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

public class DefaultCouriersService implements CouriersService {
    private DeliveryService deliveryService;
    private final AtomicInteger courierId = new AtomicInteger(1);
    private final AtomicInteger couriersInProcess = new AtomicInteger(0);
    private final static Logger logger = LogManager.getLogger(DefaultDeliveryService.class);

    @Override
    public void setDeliveryService(DeliveryService deliveryService) {
        this.deliveryService = deliveryService;
    }

    @Override
    public Courier dispatchCourier() {
        couriersInProcess.getAndIncrement();
        int courierDelay = computeCourierDelayInMs();
        Courier courier = new Courier(courierId.getAndIncrement());
        scheduler.schedule(() -> courierIsReady(courier), courierDelay, TimeUnit.MILLISECONDS);
        logger.info("Courier {} dispatched, he will arrive at kitchen in {} seconds", courier.getId(), courierDelay / 1000);
        return courier;
    }

    @Override
    public void courierIsReady(Courier courier) {
        couriersInProcess.getAndDecrement();
        courier.setArriveTimeTimestamp(Instant.now().toEpochMilli());
        deliveryService.onCourierArrived(courier);
    }

    @Override
    public boolean allCouriersProcessed() {
        return couriersInProcess.get() == 0;
    }

    private int computeCourierDelayInMs() {
        CourierRandomWindow window = AppConfig.getInstance().getCourierRandomWindowInSeconds();
        return ThreadLocalRandom.current().nextInt(
            window.start() * 1000,
            window.end() * 1000
        );
    }
}
