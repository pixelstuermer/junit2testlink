package com.github.pixelstuermer.junit2testlink.service.testlink.config;

import com.github.pixelstuermer.junit2testlink.data.definition.TestLinkConfig;
import lombok.extern.slf4j.Slf4j;

import java.net.URI;

/**
 * Default implementation of the {@link TestLinkConfigService} interface to provide the TestLink properties.
 * This implementation reads the properties from the environment variables.
 * Like this, it is suited for automated test reporting, e.g. while using a build server within a CI pipeline.
 * The tests are therefore only reported if the required environment variables are set.
 * If they are not set, e.g. when only executing the tests within an IDE, the reporting is disabled.
 * See here for the naming convention of the properties: {@link TestLinkConfig}.
 *
 * @since 1.0.0
 */
@Slf4j
public class TestLinkConfigServiceEnvVarImpl implements TestLinkConfigService {

    @Override
    public boolean isTestLinkReportingEnabled() {
        return Boolean.parseBoolean(getEnvironmentVariable(TestLinkConfig.TEST_LINK_ENABLED, true));
    }

    @Override
    public URI getTestLinkBaseUri() {
        return URI.create(getEnvironmentVariable(TestLinkConfig.TEST_LINK_BASE_URI, true));
    }

    @Override
    public String getTestLinkApiKey() {
        return getEnvironmentVariable(TestLinkConfig.TEST_LINK_API_KEY, false);
    }

    @Override
    public int getTestLinkPlatformId() {
        return Integer.parseInt(getEnvironmentVariable(TestLinkConfig.TEST_LINK_PLATFORM_ID, true));
    }

    @Override
    public int getTestLinkPlanId() {
        return Integer.parseInt(getEnvironmentVariable(TestLinkConfig.TEST_LINK_PLAN_ID, true));
    }

    @Override
    public int getTestLinkBuildId() {
        return Integer.parseInt(getEnvironmentVariable(TestLinkConfig.TEST_LINK_BUILD_ID, true));
    }

    private String getEnvironmentVariable(TestLinkConfig testLinkConfig, boolean loggingEnabled) {
        final String envVarFromSystem = System.getProperty(testLinkConfig.name());

        if (loggingEnabled) {
            LOG.trace("Read environment variable {} with value {}", testLinkConfig.name(), envVarFromSystem);
        } else {
            LOG.trace("Read environment variable {}", testLinkConfig.name());
        }

        return envVarFromSystem;
    }

}
