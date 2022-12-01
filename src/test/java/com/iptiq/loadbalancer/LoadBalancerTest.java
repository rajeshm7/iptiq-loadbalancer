package com.iptiq.loadbalancer;


import com.iptiq.provider.ProviderImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;


public class LoadBalancerTest {
    
    private ProviderImpl provider;

    @BeforeEach
    public void beforeEach() {
        provider = new  ProviderImpl();
    }

    @Test
    public void include_provider(){
        LoadBalancer loadBalancer = mock(LoadBalancerImpl.class);
        doNothing().when(loadBalancer).include(provider);
    }

    @Test
    public void exclude_provider(){
        LoadBalancer loadBalancer = mock(LoadBalancerImpl.class);
        doNothing().when(loadBalancer).exclude(provider);
    }
}
