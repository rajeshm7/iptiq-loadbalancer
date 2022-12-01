package com.iptiq.startegy;

import com.iptiq.provider.ProviderImpl;
import com.iptiq.provider.ProviderRegistry;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * RoundRobinStrategy class providing next available providers in round-robin fashion
 */
public class RoundRobinStrategy implements LoadBalancerStrategy {
    
    private final ProviderRegistry providerRegistry;
    private AtomicInteger counter;
    
    public RoundRobinStrategy(ProviderRegistry providerRegistry) {
        this.providerRegistry = providerRegistry;
        counter = new AtomicInteger(0);
    }
    
    @Override
    public ProviderImpl nextProvider() {
        
        List<String> ids = providerRegistry.getActiveIds();
        int index = counter.getAndIncrement() % ids.size();
        String id = ids.get(index);
        return providerRegistry.get(id);
    }
}
