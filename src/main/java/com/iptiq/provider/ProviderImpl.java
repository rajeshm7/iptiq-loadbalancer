package com.iptiq.provider;

import java.util.UUID;

/**
 * Provider implementation class
 */
public class ProviderImpl implements Provider{
    
    private final String identifier;
    
    public ProviderImpl() {
        identifier = UUID.randomUUID().toString();
    }

    @Override
    public String get() {
        return identifier;
    }
    
    /**
     * @return True if alive, False otherwise
     */
    @Override
    public boolean check() {
        return true;
    }
}
