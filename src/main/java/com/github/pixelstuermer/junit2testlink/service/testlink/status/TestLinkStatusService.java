package com.github.pixelstuermer.junit2testlink.service.testlink.status;

import com.github.pixelstuermer.junit2testlink.data.definition.ExecutionStatus;
import com.github.pixelstuermer.junit2testlink.testsupport.annotation.Report2TestLink;

/**
 * Interface for resolving the {@link ExecutionStatus} of a test.
 * This is the status representing if a test passed, failed or if it was aborted or disabled.
 * It can be implemented and passed to the {@link Report2TestLink} annotation to customize the automated reporting.
 * This is mainly interesting for the handling of aborted and skipped tests.
 *
 * @since 1.0.0
 */
public interface TestLinkStatusService {

    /**
     * Returns the {@link ExecutionStatus} to send to TestLink when a test was successful.
     *
     * @return The execution status of a successful test
     */
    ExecutionStatus getTestSuccessfulExecutionStatus();

    /**
     * Returns the {@link ExecutionStatus} to send to TestLink when a test failed.
     *
     * @return The execution status of a failed test
     */
    ExecutionStatus getTestFailedExecutionStatus();

    /**
     * Returns the {@link ExecutionStatus} to send to TestLink when a test was aborted.
     *
     * @return The execution status of an aborted test
     */
    ExecutionStatus getTestAbortedExecutionStatus();

    /**
     * Returns the {@link ExecutionStatus} to send to TestLink when a test was disabled.
     *
     * @return The execution status of a disabled test
     */
    ExecutionStatus getTestDisabledExecutionStatus();

}
