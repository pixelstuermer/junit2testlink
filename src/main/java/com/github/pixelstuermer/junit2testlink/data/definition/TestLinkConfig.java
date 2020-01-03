package com.github.pixelstuermer.junit2testlink.data.definition;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * Enum for the configuration properties of TestLink.
 *
 * @since 1.0.0
 */
@Getter
@AllArgsConstructor
public enum TestLinkConfig {

    TEST_LINK_ENABLED("report2testlink.enabled"),
    TEST_LINK_BASE_URI("report2testlink.base-uri"),
    TEST_LINK_API_KEY("report2testlink.api-key"),
    TEST_LINK_PLATFORM_ID("report2testlink.platform-id"),
    TEST_LINK_PLAN_ID("report2testlink.plan-id"),
    TEST_LINK_BUILD_ID("report2testlink.build-id");

    private String propertiesFileKey;

}
