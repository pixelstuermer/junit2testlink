package com.github.pixelstuermer.junit2testlink.service.testlink.notes;

import com.github.pixelstuermer.junit2testlink.data.model.TestProperties;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.extension.ExtensionContext;

/**
 * Default implementation of the {@link TestLinkNotesService} interface to define the TestLink notes info text.
 * Is concatenates the {@link TestProperties} of a test method and uses them as the TestLink notes text.
 *
 * @since 1.0.0
 */
@Slf4j
public class TestLinkNotesServiceDefaultImpl implements TestLinkNotesService {

    @Override
    public String getNotes(ExtensionContext context, TestProperties testProperties) {
        final String testNotes = concatenateTestNotes(testProperties);

        LOG.trace("Set TestLink notes as {}", testNotes);
        return testNotes;
    }

    @SuppressWarnings("StringBufferReplaceableByString")
    private String concatenateTestNotes(TestProperties testProperties) {
        final StringBuilder stringBuilder = new StringBuilder();

        return stringBuilder.append("Executed automatically by junit2testlink: Report based on test method ")
                            .append(testProperties.getTestMethodName())
                            .append(" of test class ")
                            .append(testProperties.getTestClassName())
                            .toString();
    }

}
