package com.iptiq.strategy;

import com.iptiq.provider.ProviderImpl;
import com.iptiq.provider.ProviderRegistry;
import com.iptiq.startegy.RandomStrategy;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;


public class RandomStrategyTest {
    
    private RandomStrategy randomStrategy;
    
    @BeforeEach
    public void beforeEach() {

        ProviderRegistry providerRegistry = new ProviderRegistry();
        for (int i = 0; i < 3; i++) {
            providerRegistry.include(new ProviderImpl());
        }
        randomStrategy = new RandomStrategy(providerRegistry);
    }

    @DisplayName("Fetching provider Id using Random strategy")
    @Test
    public void next() {
        Assertions.assertNotNull(randomStrategy.nextProvider());
    }
}
