package com.iptiq.provider;

import com.iptiq.exception.ProviderMaxRegistrationException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;


public class ProviderRegistryTest {
    
    private ProviderRegistry providerRegistry;
    
    @BeforeEach
    public void beforeEach() {
        providerRegistry = new ProviderRegistry();
    }
    
    @DisplayName("Provider is required, if null throw an exception")
    @Test
    public void register_null() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> providerRegistry.include(null));
    }
    
    @DisplayName("Can register up to MAX providers")
    @Test
    public void register() {
        
        for (int i = 0; i < 10; i++) {
            providerRegistry.include(new ProviderImpl());
        }
    }
    
    @DisplayName("If registration would exceed the maximum number of providers, then throw an exception")
    @Test
    public void register_maximum_exceeded() {
        
        // register up to the max
        for (int i = 0; i < 10; i++) {
            providerRegistry.include(new ProviderImpl());
        }
        // register an additional to go over the max
        Assertions.assertThrows(ProviderMaxRegistrationException.class, () -> providerRegistry.include(new ProviderImpl()));
    }
    
    @DisplayName("If a provider is registered more than once, it should be equivalent to a single registration")
    @Test
    public void register_same_provider_more_than_once() {
        
        // NOTE: this is a bit hacky given the 'void' return type of 'register'...
        // if switching over to use java.util.Map semantics one could have more insight into whether the provider had previously been registered
        final ProviderImpl provider = new ProviderImpl();
        
        // register up to the max + 1
        for (int i = 0; i < (10 + 1); i++) {
            providerRegistry.include(provider);
        }
    }
    
    @Test
    public void get_bad_id() {
        Assertions.assertNull(providerRegistry.get("bad-id"), "Invalid provider id should return null");
    }
    
    @Test
    public void get_good_id() {
        ProviderImpl provider = new ProviderImpl();
        providerRegistry.include(provider);
        Assertions.assertEquals(provider, providerRegistry.get(provider.get()), "Valid provider id should return that provider");
    }
    
    @DisplayName("Ensure id list is complete and maintains order registered")
    @Test
    public void get_ids() {
        ProviderImpl[] providers = new ProviderImpl[] {
                new ProviderImpl(),
                new ProviderImpl(),
                new ProviderImpl(),
                new ProviderImpl()
        };
        // register a few providers
        for (ProviderImpl provider : providers) {
            providerRegistry.include(provider);
        }
        List<String> ids = providerRegistry.getIds();
        Assertions.assertEquals(providers.length, ids.size());
        for (int i = 0; i < providers.length; i++) {
            Assertions.assertEquals(providers[i].get(), ids.get(i));
        }
    }
    
    @DisplayName("Ensure behavior when no providers are registered")
    @Test
    public void get_ids_zero() {
        Assertions.assertEquals(0, providerRegistry.getIds().size());
    }
    
    @DisplayName("Ensure enabled/active id list is complete and maintains order registered")
    @Test
    public void get_active_ids() {
        ProviderImpl[] providers = new ProviderImpl[] {
                new ProviderImpl(),
                new ProviderImpl(),
                new ProviderImpl(),
                new ProviderImpl()
        };
        // register a few providers
        for (ProviderImpl provider : providers) {
            providerRegistry.include(provider);
        }
        // mark a provider as down
        providerRegistry.markDown(providers[1].get());
        
        List<String> ids = providerRegistry.getActiveIds();
        Assertions.assertEquals(providers.length - 1, ids.size());
    }
    
    @DisplayName("Ensure behavior when no providers are registered")
    @Test
    public void get_active_ids_zero() {
        Assertions.assertEquals(0, providerRegistry.getActiveIds().size());
    }


    @DisplayName("providernode is removed when it is marked as down enabled by default ")
    @Test
    public void providernode_disabled_when_marked_as_down_enabled_by_default() {
        
        ProviderNode providerNode = new ProviderNode(new ProviderImpl());
        Assertions.assertTrue(providerNode.enabled());
    }

    @DisplayName("providernode is removed when it is marked as down")
    @Test
    public void providernode_disabled_when_marked_as_down() {
        
        ProviderNode providerNode = new ProviderNode(new ProviderImpl());
        providerNode.markDown();
        Assertions.assertFalse(providerNode.enabled());
    }
    
    @DisplayName("If a node has been previously excluded from the balancing it should be re-included if it has successfully been 'heartbeat checked' for 2 consecutive times")
    @Test
    public void node_enabled_after_two_successful_up_checks() {
        
        ProviderNode providerNode = new ProviderNode(new ProviderImpl());
        // providerNode marked down, is down
        providerNode.markDown();
        Assertions.assertFalse(providerNode.enabled());
        // providerNode marked up, is still down
        providerNode.markUp();
        Assertions.assertFalse(providerNode.enabled());
        // providerNode marked up a 2nd time, is now up/enabled
        providerNode.markUp();
        Assertions.assertTrue(providerNode.enabled());
    }

}
