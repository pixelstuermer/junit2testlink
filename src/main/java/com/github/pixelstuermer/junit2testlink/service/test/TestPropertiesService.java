package com.github.pixelstuermer.junit2testlink.service.test;

import com.github.pixelstuermer.junit2testlink.data.model.TestProperties;
import org.junit.jupiter.api.extension.ExtensionContext;

/**
 * Interface to get the properties of a test.
 *
 * @since 1.0.0
 */
public interface TestPropertiesService {

    /**
     * Returns the properties of a test.
     *
     * @param context The context of the test
     * @return The extracted test properties
     */
    TestProperties getTestProperties(ExtensionContext context);

}
