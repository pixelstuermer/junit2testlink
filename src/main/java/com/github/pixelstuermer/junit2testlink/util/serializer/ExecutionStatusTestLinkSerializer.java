package com.github.pixelstuermer.junit2testlink.util.serializer;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.github.pixelstuermer.junit2testlink.data.definition.ExecutionStatus;

import java.io.IOException;

/**
 * Serializer for Jackson's {@link ObjectMapper} for the TestLink representation of {@link ExecutionStatus} enums.
 *
 * @since 1.0.0
 */
public class ExecutionStatusTestLinkSerializer extends JsonSerializer<ExecutionStatus> {

    @Override
    public void serialize(ExecutionStatus value, JsonGenerator gen, SerializerProvider serializers) throws IOException {
        gen.writeString(getTestLinkValue(value));
    }

    private String getTestLinkValue(ExecutionStatus executionStatus) {
        return String.valueOf(executionStatus.getTestLinkValue());
    }
}
