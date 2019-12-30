package com.github.pixelstuermer.junit2testlink.service.test;

import com.github.pixelstuermer.junit2testlink.data.model.TestProperties;
import com.github.pixelstuermer.junit2testlink.error.NoTestPropertiesException;
import com.github.pixelstuermer.junit2testlink.service.testlink.notes.TestLinkNotesService;
import com.github.pixelstuermer.junit2testlink.service.testlink.status.TestLinkStatusService;
import com.github.pixelstuermer.junit2testlink.testsupport.annotation.Report2TestLink;
import com.github.pixelstuermer.junit2testlink.testsupport.annotation.TestLink;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.extension.ExtensionContext;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.Optional;

/**
 * Implementation of the {@link TestPropertiesService} interface to get the test properties.
 *
 * @since 1.0.0
 */
@Slf4j
public class TestPropertiesServiceImpl implements TestPropertiesService {

    private static final Class<TestLink> TEST_LINK_ANNOTATION = TestLink.class;
    private static final Class<Report2TestLink> REPORT_TO_TEST_LINK_ANNOTATION = Report2TestLink.class;

    @Override
    public TestProperties getTestProperties(ExtensionContext context) {
        final TestProperties properties = TestProperties.builder()
                                                        .testLinkReportingEnabled(isTestLinkReportingEnabled(context))
                                                        .testCaseId(getTestCaseId(context))
                                                        .testClassName(getTestClassName(context))
                                                        .testMethodName(getTestMethodName(context))
                                                        .testLinkNotesService(getTestLinkNotesService(context))
                                                        .testLinkStatusService(getTestLinkStatusService(context))
                                                        .build();

        LOG.trace("Evaluated test properties {}", properties.toString());
        return properties;
    }

    private boolean isTestLinkReportingEnabled(ExtensionContext context) {
        return getTestMethod(context).isAnnotationPresent(TEST_LINK_ANNOTATION);
    }

    private String getTestCaseId(ExtensionContext context) {
        if (isTestLinkReportingEnabled(context)) {
            final TestLink testLinkAnnotation = getAnnotation(context, TEST_LINK_ANNOTATION);
            return testLinkAnnotation.testCaseId();
        }

        LOG.warn("Test case ID not present");
        throw new NoTestPropertiesException("Test case ID not present");
    }

    private String getTestClassName(ExtensionContext context) {
        return getTestClass(context).getSimpleName();
    }

    private String getTestMethodName(ExtensionContext context) {
        return getTestMethod(context).getName();
    }

    private Class<? extends TestLinkNotesService> getTestLinkNotesService(ExtensionContext context) {
        final Report2TestLink report2TestLinkAnnotation = getAnnotation(context, REPORT_TO_TEST_LINK_ANNOTATION);
        return report2TestLinkAnnotation.notesService();
    }

    private Class<? extends TestLinkStatusService> getTestLinkStatusService(ExtensionContext context) {
        final Report2TestLink report2TestLinkAnnotation = getAnnotation(context, REPORT_TO_TEST_LINK_ANNOTATION);
        return report2TestLinkAnnotation.statusService();
    }

    private Class<?> getTestClass(ExtensionContext context) {
        final Optional<Class<?>> testClassOptional = context.getTestClass();

        if (testClassOptional.isPresent()) {
            return testClassOptional.get();
        }

        LOG.warn("Test class not present");
        throw new NoTestPropertiesException("Test class not present");
    }

    private Method getTestMethod(ExtensionContext context) {
        final Optional<Method> testMethodOptional = context.getTestMethod();

        if (testMethodOptional.isPresent()) {
            return testMethodOptional.get();
        }

        LOG.warn("Test method not present");
        throw new NoTestPropertiesException("Test method not present");
    }

    @SuppressWarnings("SameParameterValue")
    private <T extends Annotation> T getAnnotation(ExtensionContext context, Class<T> annotation) {
        try {
            return getTestClass(context).getAnnotation(annotation);
        } catch (NullPointerException e) {
            LOG.warn("Annotation {} not present", annotation.getSimpleName());
            throw new NoTestPropertiesException("Annotation " + annotation.getSimpleName() + " not present");
        }
    }

}
