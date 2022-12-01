package com.iptiq.strategy;

import com.iptiq.provider.ProviderImpl;
import com.iptiq.provider.ProviderRegistry;
import com.iptiq.startegy.RoundRobinStrategy;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;


public class RoundRobinStrategyTest {

    @DisplayName("Fetching provider Id using Round-robin strategy")
    @Test
    public void next() {

        ProviderImpl[] providers = new ProviderImpl[] {
                new ProviderImpl(),
                new ProviderImpl(),
                new ProviderImpl()
        };
        ProviderRegistry registry = new ProviderRegistry();
        for (ProviderImpl provider : providers) {
            registry.include(provider);
        }
        
        RoundRobinStrategy roundRobin = new RoundRobinStrategy(registry);
        
        Assertions.assertEquals(providers[0], roundRobin.nextProvider());
        Assertions.assertEquals(providers[1], roundRobin.nextProvider());
        Assertions.assertEquals(providers[2], roundRobin.nextProvider());
        Assertions.assertEquals(providers[0], roundRobin.nextProvider());
        Assertions.assertEquals(providers[1], roundRobin.nextProvider());
        Assertions.assertEquals(providers[2], roundRobin.nextProvider());
        
    }
}
