package com.github.pixelstuermer.junit2testlink.testsupport.annotation;

import com.github.pixelstuermer.junit2testlink.service.testlink.config.TestLinkConfigService;
import com.github.pixelstuermer.junit2testlink.service.testlink.config.TestLinkConfigServiceEnvVarImpl;
import com.github.pixelstuermer.junit2testlink.service.testlink.notes.TestLinkNotesService;
import com.github.pixelstuermer.junit2testlink.service.testlink.notes.TestLinkNotesServiceDefaultImpl;
import com.github.pixelstuermer.junit2testlink.service.testlink.status.TestLinkStatusService;
import com.github.pixelstuermer.junit2testlink.service.testlink.status.TestLinkStatusServiceDefaultImpl;
import com.github.pixelstuermer.junit2testlink.testsupport.extension.Report2TestLinkExtension;
import org.junit.jupiter.api.extension.ExtendWith;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Class-level annotation to enable the method-level TestLink reporting with {@link TestLink}.
 * It furthermore registers the {@link Report2TestLinkExtension} extension.
 *
 * @since 1.0.0
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@ExtendWith(Report2TestLinkExtension.class)
public @interface Report2TestLink {

    /**
     * Represents the {@link TestLinkNotesService} implementation to use for generating the info text of an execution.
     * This is the text which is additionally sent to TestLink and displayed when viewing the test execution.
     *
     * @return The service to generate the notes for TestLink ({@link TestLinkNotesServiceDefaultImpl} by default)
     */
    Class<? extends TestLinkNotesService> notesService() default TestLinkNotesServiceDefaultImpl.class;

    /**
     * Represents the {@link TestLinkStatusService} implementation to use for resolving the execution status of a test.
     * This is the status which defines if a test passed or failed or if it was aborted or disabled.
     *
     * @return The service to resolve the execution status with ({@link TestLinkStatusServiceDefaultImpl} by default)
     */
    Class<? extends TestLinkStatusService> statusService() default TestLinkStatusServiceDefaultImpl.class;

    /**
     * Represents the {@link TestLinkConfigService} implementation to use for providing the TestLink properties.
     * This includes configurable options like base URI, test plan ID, build ID, etc.
     *
     * @return The service to provide the TestLink config ({@link TestLinkConfigServiceEnvVarImpl} by default)
     */
    Class<? extends TestLinkConfigService> configService() default TestLinkConfigServiceEnvVarImpl.class;

}
