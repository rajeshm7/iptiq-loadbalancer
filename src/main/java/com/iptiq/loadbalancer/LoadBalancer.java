package com.iptiq.loadbalancer;

import com.iptiq.provider.ProviderImpl;

/**
 * Interface representing of LoadBalancer
 */
public interface LoadBalancer {

    void include(ProviderImpl provider);

    void exclude(ProviderImpl provider);

    String get();

}
