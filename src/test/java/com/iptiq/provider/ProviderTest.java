package com.iptiq.provider;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;


public class ProviderTest {

    @DisplayName("provider ID should not be null when creating a provider")
    @Test
    public void get_notnull() {
        ProviderImpl provider = new ProviderImpl();
        Assertions.assertNotNull(provider.get(), "provider ID should not be null");
    }

    @DisplayName("Each provider ID must be unique")
    @Test
    public void get_uniqueid() {
        ProviderImpl p1 = new ProviderImpl();
        ProviderImpl p2 = new ProviderImpl();
        Assertions.assertNotEquals(p1, p2, "provider ID must be unique");
    }
}
