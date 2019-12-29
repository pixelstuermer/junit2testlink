package com.github.pixelstuermer.junit2testlink.service.test;

import com.github.pixelstuermer.junit2testlink.data.model.TestProperties;
import org.junit.jupiter.api.extension.ExtensionContext;

/**
 * Interface to get the properties of a test (based on the given {@link ExtensionContext}).
 *
 * @since 1.0.0
 */
public interface TestPropertiesService {

    /**
     * Gets the properties of the test.
     *
     * @param context The context of the test
     * @return The test properties
     */
    TestProperties getTestProperties(ExtensionContext context);

}
