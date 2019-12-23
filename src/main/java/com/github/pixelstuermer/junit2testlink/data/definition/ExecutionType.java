package com.github.pixelstuermer.junit2testlink.data.definition;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * Enum for the execution types.
 *
 * @since 1.0.0
 */
@Getter
@AllArgsConstructor
public enum ExecutionType {

    MANUAL(1),
    AUTOMATED(2);

    private int testLinkValue;

}
