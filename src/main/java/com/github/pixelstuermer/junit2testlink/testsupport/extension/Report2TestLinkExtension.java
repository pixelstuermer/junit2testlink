package com.github.pixelstuermer.junit2testlink.testsupport.extension;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.pixelstuermer.junit2testlink.data.definition.ExecutionStatus;
import com.github.pixelstuermer.junit2testlink.data.definition.ExecutionType;
import com.github.pixelstuermer.junit2testlink.data.model.Execution;
import com.github.pixelstuermer.junit2testlink.data.model.TestProperties;
import com.github.pixelstuermer.junit2testlink.error.ServiceInstantiationException;
import com.github.pixelstuermer.junit2testlink.service.test.TestPropertiesService;
import com.github.pixelstuermer.junit2testlink.service.test.TestPropertiesServiceImpl;
import com.github.pixelstuermer.junit2testlink.service.testlink.api.TestLinkApiService;
import com.github.pixelstuermer.junit2testlink.service.testlink.api.TestLinkApiServiceRestImpl;
import com.github.pixelstuermer.junit2testlink.service.testlink.config.TestLinkConfigService;
import com.github.pixelstuermer.junit2testlink.service.testlink.notes.TestLinkNotesService;
import com.github.pixelstuermer.junit2testlink.service.testlink.status.TestLinkStatusService;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.api.extension.TestWatcher;
import org.springframework.web.client.RestTemplate;

import java.time.Instant;
import java.util.Optional;

/**
 * Implementation of the JUnit {@link TestWatcher} interface to collect and report a test result to TestLink.
 * The implemented methods are called after a test method was executed.
 *
 * @since 1.0.0
 */
@Slf4j
public class Report2TestLinkExtension implements TestWatcher {

    // TODO Refactor

    private TestPropertiesService testPropertiesService;
    private TestLinkApiService testLinkApiService;

    public Report2TestLinkExtension() {
        this.testPropertiesService = new TestPropertiesServiceImpl();
        this.testLinkApiService = new TestLinkApiServiceRestImpl(new ObjectMapper(), new RestTemplate());
    }

    @Override
    public void testSuccessful(ExtensionContext context) {
        final TestProperties testProperties = testPropertiesService.getTestProperties(context);
        final TestLinkStatusService statusService = getServiceInstance(testProperties, TestLinkStatusService.class);

        evaluateReporting(context, testProperties, statusService.getTestSuccessfulExecutionStatus());
    }

    @Override
    public void testFailed(ExtensionContext context, Throwable cause) {
        final TestProperties testProperties = testPropertiesService.getTestProperties(context);
        final TestLinkStatusService statusService = getServiceInstance(testProperties, TestLinkStatusService.class);

        evaluateReporting(context, testProperties, statusService.getTestFailedExecutionStatus());
    }

    /*
     * E.g. when a test does not meet required assumptions.
     */
    @Override
    public void testAborted(ExtensionContext context, Throwable cause) {
        final TestProperties testProperties = testPropertiesService.getTestProperties(context);
        final TestLinkStatusService statusService = getServiceInstance(testProperties, TestLinkStatusService.class);

        evaluateReporting(context, testProperties, statusService.getTestAbortedExecutionStatus());
    }

    /*
     * E.g. when a test is annotated with "@Disabled".
     */
    @Override
    public void testDisabled(ExtensionContext context, Optional<String> reason) {
        final TestProperties testProperties = testPropertiesService.getTestProperties(context);
        final TestLinkStatusService statusService = getServiceInstance(testProperties, TestLinkStatusService.class);

        evaluateReporting(context, testProperties, statusService.getTestDisabledExecutionStatus());
    }

    private void evaluateReporting(ExtensionContext context, TestProperties testProperties, ExecutionStatus status) {
        // First check if reporting is enabled on test method-level
        if (testProperties.isTestLinkReportingEnabled()) {
            final TestLinkConfigService configService = getServiceInstance(testProperties, TestLinkConfigService.class);

            // Then check if reporting is enabled on globally
            if (configService.isTestLinkReportingEnabled()) {
                reportTestResult(context, testProperties, status);
            }
        }

        LOG.trace("No reporting of test {} of class {} to TestLink", testProperties.getTestMethodName(),
                testProperties.getTestClassName());
    }

    private void reportTestResult(ExtensionContext context, TestProperties testProperties, ExecutionStatus status) {
        final Execution execution = getExecution(context, testProperties, status);
        final TestLinkConfigService configService = getServiceInstance(testProperties, TestLinkConfigService.class);

        testLinkApiService.reportTestResult(configService, execution);
        LOG.trace("Reported test {} of class {} with result {} to TestLink", testProperties.getTestMethodName(),
                testProperties.getTestClassName(), status);
    }

    private Execution getExecution(ExtensionContext context, TestProperties testProperties, ExecutionStatus status) {
        final TestLinkConfigService configService = getServiceInstance(testProperties, TestLinkConfigService.class);
        final TestLinkNotesService notesService = getServiceInstance(testProperties, TestLinkNotesService.class);

        final Execution execution = Execution.builder()
                                             .testPlan(configService.getTestLinkPlanId())
                                             .build(configService.getTestLinkBuildId())
                                             .testCase(testProperties.getTestCaseId())
                                             .platform(configService.getTestLinkPlatformId())
                                             .timestamp(getCurrentTimestamp())
                                             .notes(notesService.getNotes(context, testProperties))
                                             .type(ExecutionType.AUTOMATED)
                                             .result(status)
                                             .build();

        LOG.trace("Built Execution model {}", execution.toString());
        return execution;
    }

    private Instant getCurrentTimestamp() {
        return Instant.now();
    }

    @SuppressWarnings("unchecked")
    private <T> T getServiceInstance(TestProperties testProperties, Class<T> serviceClass) {
        final Class<T> evaluatedServiceClass;

        if (serviceClass.equals(TestLinkNotesService.class)) {
            evaluatedServiceClass = (Class<T>) testProperties.getTestLinkNotesService();
        } else if (serviceClass.equals(TestLinkStatusService.class)) {
            evaluatedServiceClass = (Class<T>) testProperties.getTestLinkStatusService();
        } else if (serviceClass.equals(TestLinkConfigService.class)) {
            evaluatedServiceClass = (Class<T>) testProperties.getTestLinkConfigService();
        } else {
            LOG.warn("Cannot resolve service class {}", serviceClass.getName());
            throw new ServiceInstantiationException("Cannot resolve service class " + serviceClass.getName());
        }

        try {
            return evaluatedServiceClass.getConstructor()
                                        .newInstance();
        } catch (Exception e) {
            LOG.warn("Cannot instantiate service of type {}", serviceClass.getName());
            throw new ServiceInstantiationException("Cannot instantiate service of type " + serviceClass.getName());
        }
    }

}
