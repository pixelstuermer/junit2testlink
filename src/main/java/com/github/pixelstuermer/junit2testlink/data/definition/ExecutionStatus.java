package com.github.pixelstuermer.junit2testlink.data.definition;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * Enum for the execution status of TestLink.
 *
 * @since 1.0.0
 */
@Getter
@AllArgsConstructor
public enum ExecutionStatus {

    PASSED('p'),
    FAILED('f'),
    BLOCKED('b'),
    SKIPPED('n');

    private char testLinkValue;

}
