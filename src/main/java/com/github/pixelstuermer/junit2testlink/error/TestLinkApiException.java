package com.github.pixelstuermer.junit2testlink.error;

/**
 * Exception that can be thrown if an error occurs while requesting the TestLink API.
 *
 * @since 1.0.0
 */
public class TestLinkApiException extends RuntimeException {

    public TestLinkApiException(String message) {
        super(message);
    }

}
