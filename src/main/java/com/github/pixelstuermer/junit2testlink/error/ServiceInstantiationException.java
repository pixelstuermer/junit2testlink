package com.github.pixelstuermer.junit2testlink.error;

/**
 * Exception that can be thrown if an instance of a service cannot be created.
 *
 * @since 1.0.0
 */
public class ServiceInstantiationException extends RuntimeException {

    public ServiceInstantiationException(String message) {
        super(message);
    }

}
