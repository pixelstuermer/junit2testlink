package com.github.pixelstuermer.junit2testlink.service.testlink.notes;

import com.github.pixelstuermer.junit2testlink.data.model.TestProperties;
import com.github.pixelstuermer.junit2testlink.error.NoTestPropertiesException;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.extension.ExtensionContext;

import java.util.Optional;

/**
 * Implementation of the {@link TestLinkNotesService} interface to define the TestLink notes info text.
 * It extracts the {@link DisplayName} of a test method and uses it as the TestLink notes text.
 *
 * @since 1.0.0
 */
@Slf4j
public class TestLinkNotesServiceDisplayNameImpl implements TestLinkNotesService {

    @Override
    public String getNotes(ExtensionContext context, TestProperties testProperties) {
        final Optional<String> displayNameOptional = getDisplayName(context);

        if (displayNameOptional.isPresent()) {
            final String displayName = displayNameOptional.get();

            LOG.trace("Set TestLink notes as {}", displayName);
            return displayName;
        }

        LOG.warn("DisplayName not present");
        throw new NoTestPropertiesException("DisplayName not present");
    }

    private Optional<String> getDisplayName(ExtensionContext context) {
        final String displayName = context.getDisplayName();

        if (displayName != null && displayName.length() > 0) {
            return Optional.of(displayName);
        }

        return Optional.empty();
    }

}
