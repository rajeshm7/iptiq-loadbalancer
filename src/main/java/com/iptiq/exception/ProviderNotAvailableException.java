package com.iptiq.exception;

/**
 * Custom exception to handle when No provider is available to exclude
 */
public class ProviderNotAvailableException extends RuntimeException {

    public ProviderNotAvailableException(String message) {
        super(message);
    }

}
