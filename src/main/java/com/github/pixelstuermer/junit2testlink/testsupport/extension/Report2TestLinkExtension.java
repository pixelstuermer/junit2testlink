package com.github.pixelstuermer.junit2testlink.testsupport.extension;

import com.github.pixelstuermer.junit2testlink.data.model.TestProperties;
import com.github.pixelstuermer.junit2testlink.error.ServiceInstantiationException;
import com.github.pixelstuermer.junit2testlink.service.test.TestPropertiesService;
import com.github.pixelstuermer.junit2testlink.service.test.TestPropertiesServiceImpl;
import com.github.pixelstuermer.junit2testlink.service.testlink.notes.TestLinkNotesService;
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

    private TestPropertiesService testPropertiesService;

    public Report2TestLinkExtension() {
        this.testPropertiesService = new TestPropertiesServiceImpl();
    }

    @Override
    public void testSuccessful(ExtensionContext context) {
        final TestProperties testProperties = testPropertiesService.getTestProperties(context);
        final TestLinkNotesService testLinkNotesService = getTestLinkNotesService(testProperties);

        LOG.trace("Test {} of class {} passed and enabled for TestLink reporting {}",
                testProperties.getTestMethodName(), testProperties.getTestClassName(),
                testProperties.isTestLinkReportingEnabled());

        // TODO Implement, extract and rework logging
    }

    @Override
    public void testFailed(ExtensionContext context, Throwable cause) {
        final TestProperties testProperties = testPropertiesService.getTestProperties(context);
        final TestLinkNotesService testLinkNotesService = getTestLinkNotesService(testProperties);

        LOG.trace("Test {} of class {} failed and enabled for TestLink reporting {}",
                testProperties.getTestMethodName(), testProperties.getTestClassName(),
                testProperties.isTestLinkReportingEnabled());

        // TODO Implement, extract and rework logging
    }

    /*
     * E.g. when a test does not meet required assumptions.
     */
    @Override
    public void testAborted(ExtensionContext context, Throwable cause) {
        final TestProperties testProperties = testPropertiesService.getTestProperties(context);
        final TestLinkNotesService testLinkNotesService = getTestLinkNotesService(testProperties);

        LOG.trace("Test {} of class {} aborted and enabled for TestLink reporting {}",
                testProperties.getTestMethodName(), testProperties.getTestClassName(),
                testProperties.isTestLinkReportingEnabled());

        // TODO Implement, extract and rework logging
    }

    /*
     * E.g. when a test is annotated with "@Disabled".
     */
    @Override
    public void testDisabled(ExtensionContext context, Optional<String> reason) {
        final TestProperties testProperties = testPropertiesService.getTestProperties(context);
        final TestLinkNotesService testLinkNotesService = getTestLinkNotesService(testProperties);

        LOG.trace("Test {} of class {} disabled and enabled for TestLink reporting {}",
                testProperties.getTestMethodName(), testProperties.getTestClassName(),
                testProperties.isTestLinkReportingEnabled());

        // TODO Implement, extract and rework logging
    }

    private TestLinkNotesService getTestLinkNotesService(TestProperties testProperties) {
        final Class<? extends TestLinkNotesService> testLinkNotesService = testProperties.getTestLinkNotesService();

        try {
            return testLinkNotesService.getConstructor()
                                       .newInstance();
        } catch (Exception e) {
            LOG.warn("Cannot instantiate TestLinkNotesService object of type {}", testLinkNotesService.getSimpleName());
            throw new ServiceInstantiationException("Cannot instantiate TestLinkNotesService object of type " +
                    testLinkNotesService.getSimpleName());
        }
    }

}
