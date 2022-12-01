package com.iptiq.strategy;

import com.iptiq.provider.ProviderRegistry;
import com.iptiq.startegy.RandomStrategy;
import com.iptiq.startegy.RoundRobinStrategy;
import com.iptiq.startegy.StrategyFactory;
import com.iptiq.startegy.StrategyType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class StrategyFactoryTest {

    private StrategyFactory strategyFactory;
    private ProviderRegistry providerRegistry;

    @BeforeEach
    public void beforeEach() {
        providerRegistry = new ProviderRegistry();
        strategyFactory = new StrategyFactory();
    }

    @DisplayName("Fetching RoundRobinStrategy object when strategyType is ROUND_ROBIN")
    @Test
    public void getStrategy_when_type_round_robin() {
        assertTrue(strategyFactory.getStrategy(StrategyType.ROUND_ROBIN, providerRegistry) instanceof RoundRobinStrategy);
    }

    @DisplayName("Fetching RandomStrategy object when strategyType is RANDOM")
    @Test
    public void getStrategy_when_type_random() {
        assertTrue(strategyFactory.getStrategy(StrategyType.RANDOM, providerRegistry) instanceof RandomStrategy);
    }

}