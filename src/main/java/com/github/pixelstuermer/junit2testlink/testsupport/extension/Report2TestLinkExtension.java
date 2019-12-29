package com.github.pixelstuermer.junit2testlink.testsupport.extension;

import com.github.pixelstuermer.junit2testlink.data.model.TestProperties;
import com.github.pixelstuermer.junit2testlink.service.test.TestPropertiesService;
import com.github.pixelstuermer.junit2testlink.service.test.TestPropertiesServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.api.extension.TestWatcher;

import java.util.Optional;

/**
 * Implementation of the JUnit {@link TestWatcher} interface to collect and report a test result to TestLink.
 * The implemented methods are called after a test method was executed, skipped, etc.
 *
 * @since 1.0.0
 */
@Slf4j
public class Report2TestLinkExtension implements TestWatcher {

    private final TestPropertiesService testPropertiesService;

    public Report2TestLinkExtension() {
        this.testPropertiesService = new TestPropertiesServiceImpl();
    }

    @Override
    public void testSuccessful(ExtensionContext context) {
        final TestProperties testProperties = testPropertiesService.getTestProperties(context);

        LOG.trace("Test {} of class {} passed and enabled for TestLink reporting {}",
                testProperties.getTestMethodName(), testProperties.getTestClassName(),
                testProperties.isTestLinkReportingEnabled());

        // TODO Implement
    }

    @Override
    public void testFailed(ExtensionContext context, Throwable cause) {
        final TestProperties testProperties = testPropertiesService.getTestProperties(context);

        LOG.trace("Test {} of class {} failed and enabled for TestLink reporting {}",
                testProperties.getTestMethodName(), testProperties.getTestClassName(),
                testProperties.isTestLinkReportingEnabled());

        // TODO Implement
    }

    /*
     * E.g. when a test does not meet required assumptions.
     */
    @Override
    public void testAborted(ExtensionContext context, Throwable cause) {
        final TestProperties testProperties = testPropertiesService.getTestProperties(context);

        LOG.trace("Test {} of class {} aborted and enabled for TestLink reporting {}",
                testProperties.getTestMethodName(), testProperties.getTestClassName(),
                testProperties.isTestLinkReportingEnabled());

        // TODO Implement
    }

    /*
     * E.g. when a test is annotated with "@Disabled".
     */
    @Override
    public void testDisabled(ExtensionContext context, Optional<String> reason) {
        final TestProperties testProperties = testPropertiesService.getTestProperties(context);

        LOG.trace("Test {} of class {} disabled and enabled for TestLink reporting {}",
                testProperties.getTestMethodName(), testProperties.getTestClassName(),
                testProperties.isTestLinkReportingEnabled());

        // TODO Implement
    }

}
