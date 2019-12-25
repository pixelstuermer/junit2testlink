package com.github.pixelstuermer.junit2testlink.testsupport.extension;

import com.github.pixelstuermer.junit2testlink.error.NoTestPropertiesException;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.api.extension.TestWatcher;

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

    @Override
    public void testSuccessful(ExtensionContext context) {
        // TODO Implement
        LOG.trace("Test {} of class {} passed", getTestMethodName(context), getTestClassName(context));
    }

    @Override
    public void testFailed(ExtensionContext context, Throwable cause) {
        // TODO Implement
        LOG.trace("Test {} of class {} failed", getTestMethodName(context), getTestClassName(context));
    }

    /*
     * E.g. when a test does not meet required assumptions.
     */
    @Override
    public void testAborted(ExtensionContext context, Throwable cause) {
        // TODO Implement
        LOG.trace("Test {} of class {} aborted", getTestMethodName(context), getTestClassName(context));
    }

    /*
     * E.g. when a test is annotated with "@Disabled".
     */
    @Override
    public void testDisabled(ExtensionContext context, Optional<String> reason) {
        // TODO Implement
        LOG.trace("Test {} of class {} disabled", getTestMethodName(context), getTestClassName(context));
    }

    private String getTestClassName(ExtensionContext context) {
        final Optional<Class<?>> testClassOptional = context.getTestClass();

        if (testClassOptional.isPresent()) {
            return testClassOptional.get()
                                    .getSimpleName();
        }

        LOG.warn("Test class name not present");
        throw new NoTestPropertiesException("Test class name not present");
    }

    private String getTestMethodName(ExtensionContext context) {
        Optional<Method> testMethodOptional = context.getTestMethod();

        if (testMethodOptional.isPresent()) {
            return testMethodOptional.get()
                                     .getName();
        }

        LOG.warn("Test method name not present");
        throw new NoTestPropertiesException("Test method name not present");
    }

}
