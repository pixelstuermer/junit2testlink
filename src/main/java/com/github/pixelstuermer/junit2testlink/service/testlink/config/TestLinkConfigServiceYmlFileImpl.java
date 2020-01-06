package com.github.pixelstuermer.junit2testlink.service.testlink.config;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import com.github.pixelstuermer.junit2testlink.data.definition.TestLinkConfig;
import com.github.pixelstuermer.junit2testlink.error.NoTestPropertiesException;
import lombok.extern.slf4j.Slf4j;

import java.io.*;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.Paths;
import java.util.Arrays;

/**
 * Alternative implementation of the {@link TestLinkConfigService} interface to provide the TestLink properties.
 * This implementation reads the properties from the "report2testlink.yml" file.
 * This file must therefore be present in the classpath.
 * Please pay attention to name it ".yml", not ".yaml".
 * See here for the naming convention of the properties: {@link TestLinkConfig}.
 *
 * @since 1.0.0
 */
@Slf4j
public class TestLinkConfigServiceYmlFileImpl implements TestLinkConfigService {

    private static final String YML_FILE_NAME = "report2testlink.yml";

    private static final String YML_PATH_SEPARATOR_REGEX = "\\.";

    private final JsonNode rootJsonNode;

    public TestLinkConfigServiceYmlFileImpl() {
        this.rootJsonNode = getRootJsonNode();
    }

    @Override
    public boolean isTestLinkReportingEnabled() {
        return Boolean.parseBoolean(readPropertyFromJsonNode(TestLinkConfig.TEST_LINK_ENABLED, true));
    }

    @Override
    public URI getTestLinkBaseUri() {
        return URI.create(readPropertyFromJsonNode(TestLinkConfig.TEST_LINK_BASE_URI, true));
    }

    @Override
    public String getTestLinkApiKey() {
        return readPropertyFromJsonNode(TestLinkConfig.TEST_LINK_API_KEY, false);
    }

    @Override
    public int getTestLinkPlatformId() {
        return Integer.parseInt(readPropertyFromJsonNode(TestLinkConfig.TEST_LINK_PLATFORM_ID, true));
    }

    @Override
    public int getTestLinkPlanId() {
        return Integer.parseInt(readPropertyFromJsonNode(TestLinkConfig.TEST_LINK_PLAN_ID, true));
    }

    @Override
    public int getTestLinkBuildId() {
        return Integer.parseInt(readPropertyFromJsonNode(TestLinkConfig.TEST_LINK_BUILD_ID, true));
    }

    private JsonNode getRootJsonNode() {
        try {
            final ObjectMapper objectMapper = new ObjectMapper(new YAMLFactory());
            final JsonNode rootJsonNode = objectMapper.readTree(getInputStreamOfFile());

            LOG.trace("Successfully loaded yml file {} from classpath", YML_FILE_NAME);
            return rootJsonNode;
        } catch (IOException | URISyntaxException | NullPointerException e) {
            LOG.warn("Cannot read from file {} in classpath", YML_FILE_NAME);
            throw new NoTestPropertiesException("Cannot read from file " + YML_FILE_NAME + " in classpath");
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
                   .getResource(YML_FILE_NAME)
                   .toURI();
    }

    private String readPropertyFromJsonNode(TestLinkConfig testLinkConfig, boolean loggingEnabled) {
        final String[] pathComponents = testLinkConfig.getPropertiesFileKey()
                                                      .split(YML_PATH_SEPARATOR_REGEX);

        final String property = getValueOfLeafNode(rootJsonNode, pathComponents);

        if (loggingEnabled) {
            LOG.trace("Read property {} with value {} from file", testLinkConfig.getPropertiesFileKey(), property);
        } else {
            LOG.trace("Read property {} from file", testLinkConfig.getPropertiesFileKey());
        }

        return property;
    }

    /*
     * Recursive method to parse a YML path to its leaf node.
     * The path to the leaf node is specified with the given array.
     */
    private String getValueOfLeafNode(JsonNode jsonNode, String[] pathComponents) {
        // Get the JsonNode of the first path component
        final JsonNode currentJsonNode = jsonNode.get(pathComponents[0]);

        if (currentJsonNode.isValueNode()) {
            // If the current path component is the leaf node, then return it and therefore stop the recursion
            return currentJsonNode.asText();
        } else {
            // If not, then call the method again without the current path component
            final String[] arrayRightOfFirst = Arrays.copyOfRange(pathComponents, 1, pathComponents.length);
            return getValueOfLeafNode(currentJsonNode, arrayRightOfFirst);
        }
    }
}
