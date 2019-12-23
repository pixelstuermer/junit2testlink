package com.github.pixelstuermer.junit2testlink.util.serializer;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializerProvider;

import java.io.IOException;
import java.time.Instant;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

/**
 * Serializer for Jackson's {@link ObjectMapper} for the TestLink representation of {@link Instant} objects.
 *
 * @since 1.0.0
 */
public class InstantTestLinkSerializer extends JsonSerializer<Instant> {

    private static final String TEST_LINK_PATTERN = "uuuu-MM-dd HH:mm:ss";

    private static final DateTimeFormatter TEST_LINK_FORMATTER = DateTimeFormatter.ofPattern(TEST_LINK_PATTERN)
                                                                                  .withZone(ZoneId.systemDefault());

    @Override
    public void serialize(Instant value, JsonGenerator gen, SerializerProvider serializers) throws IOException {
        gen.writeString(getTestLinkValue(value));
    }

    private String getTestLinkValue(Instant instant) {
        return TEST_LINK_FORMATTER.format(instant);
    }
}
