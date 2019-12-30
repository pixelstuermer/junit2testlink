package com.github.pixelstuermer.junit2testlink.service.testlink.config;

import com.github.pixelstuermer.junit2testlink.data.definition.EnvironmentVariable;
import lombok.extern.slf4j.Slf4j;

import java.net.URI;

/**
 * Default implementation of the {@link TestLinkConfigService} interface to provide the TestLink properties.
 * This implementation reads the properties from the environment variables.
 * Like this, it is suited for automated test reporting, e.g. while using a build server within a CI pipeline.
 * The tests are therefore only reported if the required environment variables are set.
 * If they are not set, e.g. when only executing the tests within an IDE, the reporting is disabled.
 *
 * @since 1.0.0
 */
@Slf4j
public class TestLinkConfigServiceEnvVarImpl implements TestLinkConfigService {

    @Override
    public boolean isTestLinkReportingEnabled() {
        return Boolean.parseBoolean(getEnvironmentVariable(EnvironmentVariable.TEST_LINK_ENABLED, true));
    }

    @Override
    public URI getTestLinkBaseUri() {
        return URI.create(getEnvironmentVariable(EnvironmentVariable.TEST_LINK_BASE_URI, true));
    }

    @Override
    public String getTestLinkApiKey() {
        return getEnvironmentVariable(EnvironmentVariable.TEST_LINK_API_KEY, false);
    }

    @Override
    public int getTestLinkPlatformId() {
        return Integer.parseInt(getEnvironmentVariable(EnvironmentVariable.TEST_LINK_PLATFORM_ID, true));
    }

    @Override
    public int getTestLinkPlanId() {
        return Integer.parseInt(getEnvironmentVariable(EnvironmentVariable.TEST_LINK_PLAN_ID, true));
    }

    @Override
    public int getTestLinkBuildId() {
        return Integer.parseInt(getEnvironmentVariable(EnvironmentVariable.TEST_LINK_BUILD_ID, true));
    }

    private String getEnvironmentVariable(EnvironmentVariable environmentVariable, boolean loggingEnabled) {
        final String envVarFromSystem = System.getProperty(environmentVariable.name());

        if (loggingEnabled) {
            LOG.trace("Read environment variable {} with value {}", environmentVariable.name(), envVarFromSystem);
        }

        return envVarFromSystem;
    }

}
