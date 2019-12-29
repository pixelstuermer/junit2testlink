package com.github.pixelstuermer.junit2testlink.data.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.github.pixelstuermer.junit2testlink.data.definition.ExecutionStatus;
import com.github.pixelstuermer.junit2testlink.data.definition.ExecutionType;
import com.github.pixelstuermer.junit2testlink.util.serializer.ExecutionStatusTestLinkSerializer;
import com.github.pixelstuermer.junit2testlink.util.serializer.ExecutionTypeTestLinkSerializer;
import com.github.pixelstuermer.junit2testlink.util.serializer.InstantTestLinkSerializer;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

import java.time.Instant;

/**
 * POJO for a single TestLink execution.
 * It therefore represents the test result report of one unit test which is sent to TestLink.
 *
 * @since 1.0.0
 */
@Getter
@Builder
@ToString
public class Execution {

    @JsonProperty("testPlanID")
    private int testPlan;

    @JsonProperty("buildID")
    private int build;

    @JsonProperty("testCaseExternalID")
    private String testCase;

    @JsonProperty("platformID")
    private int platform;

    @JsonProperty("executionTimeStampISO")
    @JsonSerialize(using = InstantTestLinkSerializer.class)
    private Instant timestamp;

    private String notes;

    @JsonProperty("executionType")
    @JsonSerialize(using = ExecutionTypeTestLinkSerializer.class)
    private ExecutionType type;

    @JsonProperty("statusCode")
    @JsonSerialize(using = ExecutionStatusTestLinkSerializer.class)
    private ExecutionStatus result;

}
