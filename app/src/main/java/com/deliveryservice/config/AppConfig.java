package com.deliveryservice.config;

import com.deliveryservice.App;
import com.deliveryservice.models.errors.UnknownStrategyError;
import com.deliveryservice.models.errors.WrongCourierRandomWindowSizeError;

import java.io.IOException;
import java.util.Properties;

public class AppConfig {
    public static final String CONFIG_FILE = "config.properties";

    private static final AppConfig INSTANCE = new AppConfig();

    private final String dataFilePath;
    private final StrategyType strategy;
    private final int numberOfOrdersPerSecond;
    private final CourierRandomWindow courierRandomWindowInSeconds;

    public static AppConfig getInstance() {
        return INSTANCE;
    }

    public String getDataFilePath() {
        return dataFilePath;
    }

    public StrategyType getStrategy() {
        return strategy;
    }

    public CourierRandomWindow getCourierRandomWindowInSeconds() {
        return courierRandomWindowInSeconds;
    }

    public int getNumberOfOrdersPerSecond() {
        return numberOfOrdersPerSecond;
    }

    private AppConfig() {
        Properties properties = new Properties();
        try {
            properties.load(App.class.getClassLoader().getResourceAsStream(CONFIG_FILE));
        } catch (IOException e) {
            e.printStackTrace();
        }
        this.dataFilePath = properties.getProperty("dataFilePath");
        String strategy = properties.getProperty("strategy");
        this.strategy = switch (strategy) {
            case "matched" -> StrategyType.MATCHED;
            case "fifo" -> StrategyType.FIFO;
            default -> throw new UnknownStrategyError(strategy);
        };
        this.numberOfOrdersPerSecond = Integer.parseInt(properties.getProperty("numberOfOrdersPerSecond"));
        int courierRandomWindowStart = Integer.parseInt(properties.getProperty("courierRandomWindowStartInSeconds"));
        int courierRandomWindowEnd = Integer.parseInt(properties.getProperty("courierRandomWindowEndInSeconds"));
        if (courierRandomWindowStart > courierRandomWindowEnd) {
            throw new WrongCourierRandomWindowSizeError(courierRandomWindowStart, courierRandomWindowEnd);
        }
        this.courierRandomWindowInSeconds = new CourierRandomWindow(courierRandomWindowStart, courierRandomWindowEnd);
    }
}
