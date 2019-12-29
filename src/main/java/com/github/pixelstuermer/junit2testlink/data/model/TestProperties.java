package com.github.pixelstuermer.junit2testlink.data.model;

import lombok.Builder;
import lombok.Getter;

/**
 * POJO for the properties of a test.
 *
 * @since 1.0.0
 */
@Getter
@Builder
public class TestProperties {

    private boolean testLinkReportingEnabled;

    private String testClassName;

    private String testMethodName;

}
