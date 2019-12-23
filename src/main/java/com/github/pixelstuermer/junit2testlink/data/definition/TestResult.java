package com.github.pixelstuermer.junit2testlink.data.definition;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * Enum for the test results.
 *
 * @since 1.0.0
 */
@Getter
@AllArgsConstructor
public enum TestResult {

    PASSED('p'),
    FAILED('f'),
    BLOCKED('b'),
    SKIPPED('n');

    private char testLinkValue;

}
