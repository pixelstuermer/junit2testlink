package com.github.pixelstuermer.junit2testlink.testsupport.extension;

import com.github.pixelstuermer.junit2testlink.error.NoTestPropertiesException;
import com.github.pixelstuermer.junit2testlink.testsupport.annotation.TestLink;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.api.extension.TestWatcher;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.Optional;

/**
 * Implementation of the JUnit {@link TestWatcher} interface to collect and report a test result to TestLink.
 * The implemented methods are called after a test method was executed, skipped, etc.
 *
 * @since 1.0.0
 */
@Slf4j
public class Report2TestLinkExtension implements TestWatcher {

    private static final Class<? extends Annotation> TEST_LINK_ANNOTATION = TestLink.class;

    @Override
    public void testSuccessful(ExtensionContext context) {
        LOG.trace("Test {} of class {} passed and enabled for TestLink reporting: {}",
                getTestMethodName(context), getTestClassName(context), isTestLinkAnnotationPresent(context));
        // TODO Implement
    }

    @Override
    public void testFailed(ExtensionContext context, Throwable cause) {
        LOG.trace("Test {} of class {} failed and enabled for TestLink reporting: {}",
                getTestMethodName(context), getTestClassName(context), isTestLinkAnnotationPresent(context));
        // TODO Implement
    }

    /*
     * E.g. when a test does not meet required assumptions.
     */
    @Override
    public void testAborted(ExtensionContext context, Throwable cause) {
        LOG.trace("Test {} of class {} aborted and enabled for TestLink reporting: {}",
                getTestMethodName(context), getTestClassName(context), isTestLinkAnnotationPresent(context));
        // TODO Implement
    }

    /*
     * E.g. when a test is annotated with "@Disabled".
     */
    @Override
    public void testDisabled(ExtensionContext context, Optional<String> reason) {
        LOG.trace("Test {} of class {} disabled and enabled for TestLink reporting: {}",
                getTestMethodName(context), getTestClassName(context), isTestLinkAnnotationPresent(context));
        // TODO Implement
    }

    private boolean isTestLinkAnnotationPresent(ExtensionContext context) {
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

        LOG.warn("Test class name not present");
        throw new NoTestPropertiesException("Test class name not present");
    }


    private Method getTestMethod(ExtensionContext context) {
        final Optional<Method> testMethodOptional = context.getTestMethod();

        if (testMethodOptional.isPresent()) {
            return testMethodOptional.get();
        }

        LOG.warn("Test method name not present");
        throw new NoTestPropertiesException("Test method name not present");
    }

}
