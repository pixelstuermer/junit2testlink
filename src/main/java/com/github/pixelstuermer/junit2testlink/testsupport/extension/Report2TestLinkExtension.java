package com.github.pixelstuermer.junit2testlink.testsupport.extension;

import com.github.pixelstuermer.junit2testlink.data.model.TestProperties;
import com.github.pixelstuermer.junit2testlink.error.ServiceInstantiationException;
import com.github.pixelstuermer.junit2testlink.service.test.TestPropertiesService;
import com.github.pixelstuermer.junit2testlink.service.test.TestPropertiesServiceImpl;
import com.github.pixelstuermer.junit2testlink.service.testlink.config.TestLinkConfigService;
import com.github.pixelstuermer.junit2testlink.service.testlink.notes.TestLinkNotesService;
import com.github.pixelstuermer.junit2testlink.service.testlink.status.TestLinkStatusService;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.api.extension.TestWatcher;

import java.util.Optional;

/**
 * Implementation of the JUnit {@link TestWatcher} interface to collect and report a test result to TestLink.
 * The implemented methods are called after a test method was executed.
 *
 * @since 1.0.0
 */
@Slf4j
public class Report2TestLinkExtension implements TestWatcher {

    private TestPropertiesService testPropertiesService;
    //    private TestLinkApiService testLinkApiService;

    public Report2TestLinkExtension() {
        this.testPropertiesService = new TestPropertiesServiceImpl();
        //    this.testLinkApiService = new TestLinkApiServiceRestImpl();
    }

    @Override
    public void testSuccessful(ExtensionContext context) {
        final TestProperties testProperties = testPropertiesService.getTestProperties(context);

        LOG.trace("Test {} of class {} passed and enabled for TestLink reporting {}",
                testProperties.getTestMethodName(), testProperties.getTestClassName(),
                testProperties.isTestLinkReportingEnabled());

        // TODO Implement, extract and rework logging
    }

    @Override
    public void testFailed(ExtensionContext context, Throwable cause) {
        final TestProperties testProperties = testPropertiesService.getTestProperties(context);

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
            LOG.warn("Cannot instantiate TestLinkNotesService of type {}", testLinkNotesService.getSimpleName());
            throw new ServiceInstantiationException("Cannot instantiate TestLinkNotesService of type " +
                    testLinkNotesService.getSimpleName());
        }
    }

    private TestLinkStatusService getTestLinkStatusService(TestProperties testProperties) {
        final Class<? extends TestLinkStatusService> testLinkStatusService = testProperties.getTestLinkStatusService();

        try {
            return testLinkStatusService.getConstructor()
                                        .newInstance();
        } catch (Exception e) {
            LOG.warn("Cannot instantiate TestLinkStatusService of type {}", testLinkStatusService.getSimpleName());
            throw new ServiceInstantiationException("Cannot instantiate TestLinkStatusService of type " +
                    testLinkStatusService.getSimpleName());
        }
    }

    private TestLinkConfigService getTestLinkConfigService(TestProperties testProperties) {
        final Class<? extends TestLinkConfigService> testLinkConfigService = testProperties.getTestLinkConfigService();

        try {
            return testLinkConfigService.getConstructor()
                                        .newInstance();
        } catch (Exception e) {
            LOG.warn("Cannot instantiate TestLinkConfigService of type {}", testLinkConfigService.getSimpleName());
            throw new ServiceInstantiationException("Cannot instantiate TestLinkConfigService of type " +
                    testLinkConfigService.getSimpleName());
        }
    }

}
