package com.github.pixelstuermer.junit2testlink.testsupport.annotation;

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
}
