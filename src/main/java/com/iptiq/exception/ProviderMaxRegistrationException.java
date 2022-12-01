package com.iptiq.exception;

/**
 * Custom exception to handle when Maximum provider registration is reached
 */
public class ProviderMaxRegistrationException extends RuntimeException {

    public ProviderMaxRegistrationException(String message) {
        super(message);
    }

}
