package com.github.pixelstuermer.junit2testlink.util.serializer;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializerProvider;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.time.Instant;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;

import static java.time.format.DateTimeFormatter.ISO_LOCAL_DATE;
import static java.time.format.DateTimeFormatter.ISO_LOCAL_TIME;

/**
 * Serializer for Jackson's {@link ObjectMapper} for the TestLink representation of {@link Instant} objects.
 *
 * @since 1.0.0
 */
@Slf4j
public class InstantTestLinkSerializer extends JsonSerializer<Instant> {

    private static final DateTimeFormatter FORMATTER = new DateTimeFormatterBuilder().append(ISO_LOCAL_DATE)
                                                                                     .appendLiteral(' ')
                                                                                     .append(ISO_LOCAL_TIME)
                                                                                     .toFormatter()
                                                                                     .withZone(ZoneId.systemDefault());

    @Override
    public void serialize(Instant value, JsonGenerator gen, SerializerProvider serializers) throws IOException {
        final String serializedInstant = getTestLinkValue(value);
        gen.writeString(serializedInstant);

        LOG.trace("Serialized Instant {} as {}", value, serializedInstant);
    }

    private String getTestLinkValue(Instant instant) {
        return FORMATTER.format(instant);
    }

}
