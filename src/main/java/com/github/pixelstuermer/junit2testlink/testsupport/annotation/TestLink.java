package com.github.pixelstuermer.junit2testlink.testsupport.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Method-level annotation to report the result a of test method to TestLink.
 *
 * @since 1.0.0
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface TestLink {

    /**
     * Represents the test case ID of TestLink.
     * The result of the test method will be reported to the TestLink test case with the given prefix.
     *
     * @return The TestLink test case ID
     */
    String testCaseId();

}
