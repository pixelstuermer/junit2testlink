package com.github.pixelstuermer.junit2testlink.data.model;

import com.github.pixelstuermer.junit2testlink.service.testlink.config.TestLinkConfigService;
import com.github.pixelstuermer.junit2testlink.service.testlink.notes.TestLinkNotesService;
import com.github.pixelstuermer.junit2testlink.service.testlink.status.TestLinkStatusService;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

/**
 * POJO for the properties of a test.
 *
 * @since 1.0.0
 */
@Getter
@Builder
@ToString
public class TestProperties {

    private boolean testLinkReportingEnabled;

    private String testCaseId;

    private String testClassName;

    private String testMethodName;

    private Class<? extends TestLinkNotesService> testLinkNotesService;

    private Class<? extends TestLinkStatusService> testLinkStatusService;

    private Class<? extends TestLinkConfigService> testLinkConfigService;

}
