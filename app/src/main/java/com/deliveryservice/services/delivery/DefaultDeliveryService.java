package com.deliveryservice.services.delivery;

import com.deliveryservice.config.AppConfig;
import com.deliveryservice.models.domain.Courier;
import com.deliveryservice.models.domain.Order;
import com.deliveryservice.services.analytics.AnalyticsService;
import com.deliveryservice.services.kitchen.KitchenService;
import com.deliveryservice.services.orders.OrdersService;
import com.deliveryservice.services.couriers.CouriersService;

import com.deliveryservice.strategies.Strategy;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.time.Instant;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

public class DefaultDeliveryService implements DeliveryService {
    private final KitchenService kitchenService;
    private final OrdersService ordersService;
    private final CouriersService couriersService;
    private final Strategy strategy;
    private final AnalyticsService analyticsService;
    private ScheduledFuture<?> orderProcessingTask;

    private final static Logger logger = LogManager.getLogger(DefaultDeliveryService.class);

    public DefaultDeliveryService(
        KitchenService kitchenService,
        OrdersService ordersService,
        CouriersService couriersService,
        Strategy strategy,
        AnalyticsService analyticsService
    ) {
        kitchenService.setDeliveryService(this);
        this.kitchenService = kitchenService;
        this.ordersService = ordersService;
        couriersService.setDeliveryService(this);
        this.couriersService = couriersService;
        this.strategy = strategy;
        this.analyticsService = analyticsService;
    }

    private void processOrders() {
        for (int i = 0; i < AppConfig.getInstance().getNumberOfOrdersPerSecond(); ++i) {
            if (ordersService.hasNext()) {
                Order order = ordersService.getNext();

                kitchenService.startWorkingOnOrder(order);
                Courier courier = couriersService.dispatchCourier();
                strategy.submitNewOrderAndCourier(order, courier);
            } else {
                orderProcessingTask.cancel(false);
            }
        }
    }
    
    @Override
    public void startProcessingOrders() {
        logger.info("Start processing orders using {} strategy", strategy.getType());
        orderProcessingTask = scheduler.scheduleAtFixedRate(this::processOrders, 1, 1, TimeUnit.SECONDS);
    }

    @Override
    public void onOrderFinished(Order order) {
        Courier courier = strategy.getCourierForOrder(order);
        if (courier != null && courier.getArriveTimeTimestamp() != null) {
            long courierWaitingTimeInMs = analyticsService.sendOrderPickupEvent(courier, Instant.now().toEpochMilli());
            logger.info(
                "Courier {} received order {}, courier spend {} ms waiting for order",
                courier.getId(),
                order.getId(),
                courierWaitingTimeInMs
            );
        }
        if (couriersService.allCouriersProcessed() && kitchenService.allOrdersProcessed()) {
                printStatistics();
                scheduler.shutdown();
        }
    }

    @Override
    public void onCourierArrived(Courier courier) {
        Order order = strategy.getOrderForCourier(courier);
        if (order != null && order.getReadyTimeTimestamp() != null) {
            long orderWaitingTimeInMs = analyticsService.sendCourierArrivalEvent(order, Instant.now().toEpochMilli());
            logger.info(
                "Courier {} received order {}, order spend {} ms waiting for courier",
                courier.getId(),
                order.getId(),
                orderWaitingTimeInMs
            );
        }
        if (couriersService.allCouriersProcessed() && kitchenService.allOrdersProcessed()) {
            printStatistics();
            scheduler.shutdown();
        }
    }

    private void printStatistics() {
        logger.info("{} strategy was used to process orders. Average food waiting time: {}, Average courier waiting time: {}", strategy.getType(), analyticsService.getAverageOrderWaitTimeInMs(), analyticsService.getAverageCourierWaitTimeInMs());
    }
}
