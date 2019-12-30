package com.github.pixelstuermer.junit2testlink.service.testlink.api;

import com.github.pixelstuermer.junit2testlink.data.model.Execution;
import com.github.pixelstuermer.junit2testlink.service.testlink.config.TestLinkConfigService;

/**
 * Interface for reporting test results to TestLink.
 *
 * @since 1.0.0
 */
public interface TestLinkApiService {

    /**
     * Sends a test execution result to TestLink.
     *
     * @param execution             The execution of the current test to be sent to TestLink
     * @param testLinkConfigService The service which provides the TestLink configuration
     */
    void reportTestResult(TestLinkConfigService testLinkConfigService, Execution execution);

}
