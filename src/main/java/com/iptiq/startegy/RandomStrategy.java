package com.iptiq.startegy;

import com.iptiq.provider.ProviderImpl;
import com.iptiq.provider.ProviderRegistry;

import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

/**
 * RandomStrategy class providing next available providers in random fashion
 */
public class RandomStrategy implements LoadBalancerStrategy {
    
    private final ProviderRegistry providerRegistry;
    
    public RandomStrategy(ProviderRegistry providerRegistry) {
        this.providerRegistry = providerRegistry;
    }
    
    @Override
    public ProviderImpl nextProvider() {
        
        List<String> ids = providerRegistry.getActiveIds();
        int index = ThreadLocalRandom.current().nextInt(ids.size());
        String id = ids.get(index);
        return providerRegistry.get(id);
    }
}
