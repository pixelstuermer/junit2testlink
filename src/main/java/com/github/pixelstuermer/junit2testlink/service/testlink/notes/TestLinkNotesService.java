package com.github.pixelstuermer.junit2testlink.service.testlink.notes;

import com.github.pixelstuermer.junit2testlink.data.model.TestProperties;
import com.github.pixelstuermer.junit2testlink.testsupport.annotation.Report2TestLink;
import org.junit.jupiter.api.extension.ExtensionContext;

/**
 * Interface for generating the notes which are sent to TestLink to be displayed for each a test execution.
 * It can be implemented and passed to the {@link Report2TestLink} annotation to customize the automated reporting.
 *
 * @since 1.0.0
 */
public interface TestLinkNotesService {

    /**
     * Returns the TestLink notes text which will be shown when viewing a test execution.
     *
     * @param context        The context of the test
     * @param testProperties The already extracted properties of the test
     * @return The TestLink notes text
     */
    String getNotes(ExtensionContext context, TestProperties testProperties);

}
