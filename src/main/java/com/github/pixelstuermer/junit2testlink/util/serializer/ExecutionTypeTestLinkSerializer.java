package com.github.pixelstuermer.junit2testlink.util.serializer;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.github.pixelstuermer.junit2testlink.data.definition.ExecutionType;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;

/**
 * Serializer for Jackson's {@link ObjectMapper} for the TestLink representation of {@link ExecutionType} enums.
 *
 * @since 1.0.0
 */
@Slf4j
public class ExecutionTypeTestLinkSerializer extends JsonSerializer<ExecutionType> {

    @Override
    public void serialize(ExecutionType value, JsonGenerator gen, SerializerProvider serializers) throws IOException {
        final String serializedExecutionType = getTestLinkValue(value);
        gen.writeString(serializedExecutionType);

        LOG.trace("Serialized ExecutionType {} as {}", value, serializedExecutionType);
    }

    private String getTestLinkValue(ExecutionType executionType) {
        return String.valueOf(executionType.getTestLinkValue());
    }
}
