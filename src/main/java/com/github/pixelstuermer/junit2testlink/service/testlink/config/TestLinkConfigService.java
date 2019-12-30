package com.github.pixelstuermer.junit2testlink.service.testlink.config;

import com.github.pixelstuermer.junit2testlink.testsupport.annotation.Report2TestLink;

import java.net.URI;

/**
 * Interface for providing the TestLink properties like base URI, test plan ID, build ID, etc.
 * It can be implemented and passed to the {@link Report2TestLink} annotation to customize the automated reporting.
 * Since the library is focused on automated reporting within a CI toolchain, it also provides a default implementation.
 * The provided default implementation gets the properties from environment variables.
 * These can be set, for example, while executing automated builds with a build server.
 * Like this, the tests can be executed within the IDE without reporting to TestLink.
 * The reporting then only gets triggered when the required environment variables are set during the automated build.
 * See here for details: {@link TestLinkConfigServiceEnvVarImpl}.
 *
 * @since 1.0.0
 */
public interface TestLinkConfigService {

    /**
     * TestLink configuration if tests should be reported at all.
     *
     * @return Whether or not reporting is enabled
     */
    boolean isTestLinkReportingEnabled();

    /**
     * TestLink configuration to get the base URI.
     *
     * @return The base URI
     */
    URI getTestLinkBaseUri();

    /**
     * TestLink configuration to get the test plan ID.
     *
     * @return The test plan ID
     */
    String getTestLinkPlanId();

    /**
     * TestLink configuration to get the test build ID.
     *
     * @return The test build ID
     */
    String getTestLinkBuildId();

}
