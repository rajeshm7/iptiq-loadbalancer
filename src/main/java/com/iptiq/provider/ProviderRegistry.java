package com.iptiq.provider;

import com.iptiq.exception.ProviderMaxRegistrationException;
import com.iptiq.exception.ProviderNotAvailableException;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static com.iptiq.config.Config.MAX_PROVIDERS;

/**
 * ProviderRegistry class which stores all registered providers
 */
public class ProviderRegistry {

    // registry (database) of all registered providers
    private final Map<String, ProviderNode> providers;
    
    public ProviderRegistry() {
        // choose 'linked' to maintain ordering of keys
        providers = new LinkedHashMap<>();
    }
    
    public synchronized void include(ProviderImpl provider) {
        
        if (provider == null) {
            throw new IllegalArgumentException("Provider must not be null");
        }
        
        if (providers.size() >= MAX_PROVIDERS) {
            throw new ProviderMaxRegistrationException("Maximum Provider capacity reached");
        }
        
        providers.put(provider.get(), new ProviderNode(provider));
    }

    public synchronized void exclude(ProviderImpl provider) {

        if (provider == null) {
            throw new IllegalArgumentException("Provider must not be null");
        }

        if (!providers.containsKey(provider.get())) {
            throw new ProviderNotAvailableException("No Provider available to exclude");
        }

        providers.remove(provider.get());
    }
    
    public synchronized List<String> getIds() {
        return providers.keySet()
                .stream()
                .collect(Collectors.toUnmodifiableList());
    }
    
    public synchronized List<String> getActiveIds() {
        return providers.entrySet()
                .stream()
                .filter(entry -> entry.getValue().enabled())
                .map(entry -> entry.getKey())
                .collect(Collectors.toUnmodifiableList());
    }
    
    public synchronized void markUp(String id) {
        ProviderNode providerNode = providers.get(id);
        if (providerNode != null) {
            providerNode.markUp();
        }
    }
    
    public synchronized void markDown(String id) {
        ProviderNode providerNode = providers.get(id);
        if (providerNode != null) {
            providerNode.markDown();
        }
    }
    
    public synchronized ProviderImpl get(String id) {
        ProviderNode providerNode = providers.get(id);
        if (providerNode == null) {
            return null;
        }
        return providerNode.getProvider();
    }

}
