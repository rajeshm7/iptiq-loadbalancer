package com.iptiq.provider;

import static com.iptiq.config.Config.MIN_UP_COUNT;

/**
 * ProviderNode is a Node representation of provider along with upCount
 */
public class ProviderNode {

    private ProviderImpl provider;
    private int upCount;

    public ProviderNode(ProviderImpl provider) {
        this.provider = provider;
        upCount = MIN_UP_COUNT;
    }

    public ProviderImpl getProvider() {
        return provider;
    }

    public void markUp() {

        if (upCount < MIN_UP_COUNT) {
            upCount++;
        }
    }

    public void markDown() {
        upCount = 0;
    }

    public boolean enabled() {
        return upCount >= MIN_UP_COUNT;
    }
}
