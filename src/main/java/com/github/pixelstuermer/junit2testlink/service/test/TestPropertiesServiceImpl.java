package com.github.pixelstuermer.junit2testlink.service.test;

import com.github.pixelstuermer.junit2testlink.data.model.TestProperties;
import com.github.pixelstuermer.junit2testlink.error.NoTestPropertiesException;
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

    private static final Class<? extends Annotation> TEST_LINK_ANNOTATION = TestLink.class;

    @Override
    public TestProperties getTestProperties(ExtensionContext context) {
        final TestProperties properties = TestProperties.builder()
                                                        .testLinkReportingEnabled(isTestLinkReportingEnabled(context))
                                                        .testClassName(getTestClassName(context))
                                                        .testMethodName(getTestMethodName(context))
                                                        .build();

        LOG.trace("Evaluated test properties {}", properties.toString());
        return properties;
    }

    private boolean isTestLinkReportingEnabled(ExtensionContext context) {
        return getTestMethod(context).isAnnotationPresent(TEST_LINK_ANNOTATION);
    }

    private String getTestClassName(ExtensionContext context) {
        return getTestClass(context).getSimpleName();
    }

    private String getTestMethodName(ExtensionContext context) {
        return getTestMethod(context).getName();
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

}
