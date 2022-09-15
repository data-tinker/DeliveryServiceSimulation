package com.deliveryservice.strategies;

import com.deliveryservice.config.StrategyType;

public class StrategyFactory {
    public static Strategy getStrategy(StrategyType type) {
        return switch (type) {
            case MATCHED -> new MatchedStrategy();
            case FIFO -> new FifoStrategy();
        };
    }
}
