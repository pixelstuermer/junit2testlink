package com.github.pixelstuermer.junit2testlink.service.testlink.config;

import com.github.pixelstuermer.junit2testlink.data.definition.TestLinkConfig;
import com.github.pixelstuermer.junit2testlink.error.NoTestPropertiesException;
import lombok.extern.slf4j.Slf4j;

import java.io.*;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.Paths;
import java.util.Properties;

/**
 * Alternative implementation of the {@link TestLinkConfigService} interface to provide the TestLink properties.
 * This implementation reads the properties from the "report2testlink.properties" file.
 * This file must therefore be present in the classpath.
 * See here for the naming convention of the properties: {@link TestLinkConfig}.
 *
 * @since 1.0.0
 */
@Slf4j
public class TestLinkConfigServicePropertiesFileImpl implements TestLinkConfigService {

    private static final String PROPERTIES_FILE_NAME = "report2testlink.properties";

    private final Properties properties;

    public TestLinkConfigServicePropertiesFileImpl() {
        this.properties = getPropertiesFile();
    }

    @Override
    public boolean isTestLinkReportingEnabled() {
        return readPropertyFromFile(TestLinkConfig.TEST_LINK_ENABLED, Boolean.class, true);
    }

    @Override
    public URI getTestLinkBaseUri() {
        return readPropertyFromFile(TestLinkConfig.TEST_LINK_BASE_URI, URI.class, true);
    }

    @Override
    public String getTestLinkApiKey() {
        return readPropertyFromFile(TestLinkConfig.TEST_LINK_API_KEY, String.class, false);
    }

    @Override
    public int getTestLinkPlatformId() {
        return readPropertyFromFile(TestLinkConfig.TEST_LINK_PLATFORM_ID, Integer.class, true);
    }

    @Override
    public int getTestLinkPlanId() {
        return readPropertyFromFile(TestLinkConfig.TEST_LINK_PLAN_ID, Integer.class, true);
    }

    @Override
    public int getTestLinkBuildId() {
        return readPropertyFromFile(TestLinkConfig.TEST_LINK_BUILD_ID, Integer.class, true);
    }

    private Properties getPropertiesFile() {
        try {
            final Properties properties = new Properties();
            properties.load(getInputStreamOfFile());

            LOG.trace("Successfully loaded properties file {} from classpath", PROPERTIES_FILE_NAME);
            return properties;
        } catch (IOException | URISyntaxException | NullPointerException e) {
            LOG.warn("Cannot read from file {} in classpath", PROPERTIES_FILE_NAME);
            throw new NoTestPropertiesException("Cannot read from file " + PROPERTIES_FILE_NAME + " in classpath");
        }
    }

    private InputStream getInputStreamOfFile() throws URISyntaxException, FileNotFoundException {
        return new FileInputStream(getFile());
    }

    private File getFile() throws URISyntaxException {
        return Paths.get(getFileUri())
                    .toFile();
    }

    @SuppressWarnings("ConstantConditions")
    private URI getFileUri() throws URISyntaxException {
        return this.getClass()
                   .getClassLoader()
                   .getResource(PROPERTIES_FILE_NAME)
                   .toURI();
    }

    private <T> T readPropertyFromFile(TestLinkConfig testLinkConfig, Class<T> type, boolean loggingEnabled) {
        final String property = properties.getProperty(testLinkConfig.getPropertiesFileKey());

        if (loggingEnabled) {
            LOG.trace("Read property {} with value {} from file", testLinkConfig.getPropertiesFileKey(), property);
        } else {
            LOG.trace("Read property {} from file", testLinkConfig.getPropertiesFileKey());
        }

        return type.cast(property);
    }

}
