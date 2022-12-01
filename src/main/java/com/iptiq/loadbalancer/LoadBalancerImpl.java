package com.iptiq.loadbalancer;


import com.iptiq.startegy.LoadBalancerStrategy;
import com.iptiq.heartbeat.HeartBeatMonitor;
import com.iptiq.provider.ProviderImpl;
import com.iptiq.provider.ProviderRegistry;
import com.iptiq.startegy.StrategyFactory;
import com.iptiq.startegy.StrategyType;

import java.util.concurrent.atomic.AtomicInteger;

import static com.iptiq.config.Config.MAX_PARALLEL_REQUESTS_PER_PROVIDER;

/**
 * LoadBalancer implementation class
 */
public class LoadBalancerImpl implements LoadBalancer{
    
    private AtomicInteger requestCount;
    private final ProviderRegistry providerRegistry;
    private final LoadBalancerStrategy strategy;
    private final StrategyFactory strategyFactory;
    
    public LoadBalancerImpl() {
        this(StrategyType.ROUND_ROBIN);
    }

    public LoadBalancerImpl(StrategyType strategyType) {
        strategyFactory = new StrategyFactory();
        providerRegistry = new ProviderRegistry();

        // NOTE: Used Factory Design pattern to create strategyObject based on type
        strategy = strategyFactory.getStrategy(strategyType, providerRegistry);

        // initialize the request counter to zero (since no requests yet on startup)
        requestCount = new AtomicInteger(0);

        // start the heartbeat monitor
        new HeartBeatMonitor(providerRegistry).start();
    }

    @Override
    public void include(ProviderImpl provider) {
        providerRegistry.include(provider);
    }

    @Override
    public void exclude(ProviderImpl provider) {
        providerRegistry.exclude(provider);
    }

    @Override
    public String get() {
        String value = null;
        int activeProviders = providerRegistry.getActiveIds().size();

        if (requestCount.incrementAndGet() <= activeProviders * MAX_PARALLEL_REQUESTS_PER_PROVIDER) {
            value = doGet();
        }
        requestCount.decrementAndGet();
        return value;
    }
    
    private String doGet() {
        ProviderImpl provider = strategy.nextProvider();
        if (provider == null) {
            return null;
        }
        return provider.get();
    }
}
