package com.iptiq.heartbeat;

import com.iptiq.provider.ProviderImpl;
import com.iptiq.provider.ProviderRegistry;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;


public class HeartBeatCheckerTest {
    
    private HeartBeatChecker checker;
    
    @BeforeEach
    public void beforeEach() {

        ProviderRegistry providerRegistry = new ProviderRegistry();
        for (int i = 0; i < 3; i++) {
            providerRegistry.include(new ProviderImpl());
        }
        checker = new HeartBeatChecker(providerRegistry);
    }

    @Test
    public void check_provider_is_alive(){
        HeartBeatChecker heartBeatChecker = mock(HeartBeatChecker.class);
        doNothing().when(heartBeatChecker).check();
    }
}
