package com.iptiq.heartbeat;

import com.iptiq.provider.ProviderImpl;
import com.iptiq.provider.ProviderRegistry;

/**
 * HeartBeatChecker checks all the providers alive or not
 */
public class HeartBeatChecker {
    
    private final ProviderRegistry providerRegistry;
    
    public HeartBeatChecker(ProviderRegistry providerRegistry) {
        this.providerRegistry = providerRegistry;
    }
    
    public void check() {
        providerRegistry.getIds()
                .stream()
                .forEach(id -> {
                    ProviderImpl provider = providerRegistry.get(id);
                    if (provider.check()) {
                        providerRegistry.markUp(id);
                    } else {
                        providerRegistry.markDown(id);
                    }
                });
    }
}
