package com.deliveryservice.models.errors;

import com.deliveryservice.config.AppConfig;

public class UnknownStrategyError extends RuntimeException {
    public UnknownStrategyError(String strategy) {
        super(
            String.format(
                "Unknown strategy %s specified in %s file",
                strategy,
                AppConfig.CONFIG_FILE
            )
        );
    }
}
