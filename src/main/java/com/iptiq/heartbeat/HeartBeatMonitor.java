package com.iptiq.heartbeat;

import com.iptiq.provider.ProviderRegistry;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import static com.iptiq.config.Config.HEARTBEAT_CHECK_INTERVAL;

/**
 * HeartBeatMonitor schedules HeartbeatChecker to run every 5 seconds
 */
public class HeartBeatMonitor {

    private final HeartBeatChecker checker;
    
    public HeartBeatMonitor(ProviderRegistry providerRegistry) {
        checker = new HeartBeatChecker(providerRegistry);
    }
    
    public void start() {
        
        ScheduledExecutorService executor = Executors.newSingleThreadScheduledExecutor();
        executor.scheduleAtFixedRate(new Runnable() {
            
            @Override
            public void run() {
                checker.check();
            }
        }, 0, HEARTBEAT_CHECK_INTERVAL, TimeUnit.SECONDS);
    }
}
