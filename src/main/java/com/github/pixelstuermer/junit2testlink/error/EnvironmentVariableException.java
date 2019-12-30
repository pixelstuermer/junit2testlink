package com.github.pixelstuermer.junit2testlink.error;

import com.github.pixelstuermer.junit2testlink.service.testlink.config.TestLinkConfigServiceEnvVarImpl;

/**
 * Exception that can be thrown if an error occurs while accessing or casting environment variables.
 * It is mainly used by the {@link TestLinkConfigServiceEnvVarImpl}.
 *
 * @since 1.0.0
 */
public class EnvironmentVariableException extends RuntimeException {

    public EnvironmentVariableException(String message) {
        super(message);
    }

}
