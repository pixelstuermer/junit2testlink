package com.github.pixelstuermer.junit2testlink.error;

/**
 * Exception that can be thrown if required test properties are not present.
 *
 * @since 1.0.0
 */
public class NoTestPropertiesException extends RuntimeException {

    public NoTestPropertiesException(String message) {
        super(message);
    }

}
