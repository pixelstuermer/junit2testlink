package com.github.pixelstuermer.junit2testlink.service.testlink.config;

import com.github.pixelstuermer.junit2testlink.data.definition.EnvironmentVariable;
import com.github.pixelstuermer.junit2testlink.error.EnvironmentVariableException;
import lombok.extern.slf4j.Slf4j;

import java.net.URI;

/**
 * Default implementation of the {@link TestLinkConfigService} interface to provide the TestLink properties.
 * The implementation reads the properties from the environment variables.
 * Like this, it is suited for automated test reporting, e.g. while using a build server within a CI pipeline.
 * The tests are therefore only reported if the required environment variables are set.
 * If not, like when executing the tests within an IDE, no reporting is done.
 *
 * @since 1.0.0
 */
@Slf4j
public class TestLinkConfigServiceEnvVarImpl implements TestLinkConfigService {

    @Override
    public boolean isTestLinkReportingEnabled() {
        return Boolean.parseBoolean(getEnvironmentVariable(EnvironmentVariable.TEST_LINK_ENABLED));
    }

    @Override
    public URI getTestLinkBaseUri() {
        return URI.create(getEnvironmentVariable(EnvironmentVariable.TEST_LINK_BASE_URI));
    }

    @Override
    public String getTestLinkPlanId() {
        return getEnvironmentVariable(EnvironmentVariable.TEST_LINK_PLAN_ID);
    }

    @Override
    public String getTestLinkBuildId() {
        return getEnvironmentVariable(EnvironmentVariable.TEST_LINK_BUILD_ID);
    }

    private String getEnvironmentVariable(EnvironmentVariable environmentVariable) {
        final String envVar = System.getenv(environmentVariable.name());

        if (envVar != null && envVar.length() > 0) {
            LOG.trace("Read variable {} with value {}", environmentVariable.name(), envVar);
            return envVar;
        }

        LOG.warn("Cannot read environment variable {}", environmentVariable.name());
        throw new EnvironmentVariableException("Cannot read environment variable " + environmentVariable.name());
    }

}
