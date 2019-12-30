package com.github.pixelstuermer.junit2testlink.service.testlink.status;

import com.github.pixelstuermer.junit2testlink.data.definition.ExecutionStatus;
import lombok.extern.slf4j.Slf4j;

/**
 * Default implementation of the {@link TestLinkStatusService} interface to resolve the {@link ExecutionStatus}.
 * It resolves the execution status against an executed test as shown below:
 *
 * <ul>
 *     <li>Successful test: {@link ExecutionStatus}.PASSED</li>
 *     <li>Failed test: {@link ExecutionStatus}.FAILED</li>
 *     <li>Aborted test: {@link ExecutionStatus}.BLOCKED</li>
 *     <li>Disabled test: {@link ExecutionStatus}.SKIPPED</li>
 * </ul>
 *
 * @since 1.0.0
 */
@Slf4j
public class TestLinkStatusServiceDefaultImpl implements TestLinkStatusService {

    @Override
    public ExecutionStatus getTestSuccessfulExecutionStatus() {
        final ExecutionStatus successfulExecutionStatus = ExecutionStatus.PASSED;

        LOG.trace("Resolved a successful test as {}", successfulExecutionStatus);
        return successfulExecutionStatus;
    }

    @Override
    public ExecutionStatus getTestFailedExecutionStatus() {
        final ExecutionStatus failedExecutionStatus = ExecutionStatus.FAILED;

        LOG.trace("Resolved a failed test as {}", failedExecutionStatus);
        return failedExecutionStatus;
    }

    @Override
    public ExecutionStatus getTestAbortedExecutionStatus() {
        final ExecutionStatus abortedExecutionStatus = ExecutionStatus.BLOCKED;

        LOG.trace("Resolved an aborted test as {}", abortedExecutionStatus);
        return abortedExecutionStatus;
    }

    @Override
    public ExecutionStatus getTestDisabledExecutionStatus() {
        final ExecutionStatus disabledExecutionStatus = ExecutionStatus.SKIPPED;

        LOG.trace("Resolved a disabled test as {}", disabledExecutionStatus);
        return disabledExecutionStatus;
    }

}
