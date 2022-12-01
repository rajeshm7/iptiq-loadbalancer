package com.iptiq.startegy;

import com.iptiq.provider.ProviderImpl;

/**
 * Strategy that will return the next available provider based on a particular algorithm
 */
public interface LoadBalancerStrategy {
    
    ProviderImpl nextProvider();
}
