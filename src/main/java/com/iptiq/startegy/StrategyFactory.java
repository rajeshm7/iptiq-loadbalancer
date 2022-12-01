package com.iptiq.startegy;

import com.iptiq.provider.ProviderRegistry;

/**
 * Factory class gives strategy object based on StrategyType
 */
public class StrategyFactory {

    public LoadBalancerStrategy getStrategy(StrategyType strategyType, ProviderRegistry providerRegistry) {

        switch (strategyType) {
            case ROUND_ROBIN:
                return new RoundRobinStrategy(providerRegistry);
            case RANDOM:
                return new RandomStrategy(providerRegistry);
            default:
                return new RoundRobinStrategy(providerRegistry);
        }

    }
}
