package com.github.pixelstuermer.junit2testlink.service.testlink.api;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.pixelstuermer.junit2testlink.data.model.Execution;
import com.github.pixelstuermer.junit2testlink.error.TestLinkApiException;
import com.github.pixelstuermer.junit2testlink.service.testlink.config.TestLinkConfigService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.util.Base64Utils;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

/**
 * Implementation of the {@link TestLinkApiService} interface to report test results to TestLink.
 * The implementation uses the REST API of TestLink and sends the execution via HTTP POST.
 * The {@link Execution} model is sent as JSON in the HTTP body.
 *
 * @since 1.0.0
 */
@Slf4j
public class TestLinkApiServiceRestImpl implements TestLinkApiService {

    private static final String BASIC_AUTH_PREFIX = "Basic ";
    private static final char BASIC_AUTH_USERNAME_PASSWORD_SEPARATOR = ':';

    private static final String TEST_LINK_REST_API_PATH = "/lib/api/rest/v1/executions";

    private ObjectMapper objectMapper;
    private RestTemplate restTemplate;

    public TestLinkApiServiceRestImpl(ObjectMapper objectMapper, RestTemplate restTemplate) {
        this.objectMapper = objectMapper;
        this.restTemplate = restTemplate;
    }

    @Override
    public void reportTestResult(TestLinkConfigService testLinkConfigService, Execution execution) {
        final URI testLinkExecutionsUri = getTestLinkExecutionsUri(testLinkConfigService);
        final MultiValueMap<String, String> httpHeaders = getHttpHeaders(testLinkConfigService);
        final HttpEntity<String> httpEntity = getHttpEntity(httpHeaders, execution);

        postTestResult(httpEntity, testLinkExecutionsUri);
    }

    // TODO Test leading/trailing slashes at host and path
    private URI getTestLinkExecutionsUri(TestLinkConfigService testLinkConfigService) {
        final URI testLinkExecutionsUri = UriComponentsBuilder.fromUri(testLinkConfigService.getTestLinkBaseUri())
                                                              .path(TEST_LINK_REST_API_PATH)
                                                              .build()
                                                              .toUri();

        LOG.trace("Constructed TestLink executions URI {}", testLinkExecutionsUri.toString());
        return testLinkExecutionsUri;
    }

    private MultiValueMap<String, String> getHttpHeaders(TestLinkConfigService testLinkConfigService) {
        final String apiKey = testLinkConfigService.getTestLinkApiKey() + BASIC_AUTH_USERNAME_PASSWORD_SEPARATOR;
        final String apiKeyBase64Encoded = encodeToBase64(apiKey);

        final LinkedMultiValueMap<String, String> httpHeaders = new LinkedMultiValueMap<>();
        httpHeaders.add(HttpHeaders.AUTHORIZATION, BASIC_AUTH_PREFIX + apiKeyBase64Encoded);

        LOG.trace("Created HTTP headers with {} entries", httpHeaders.size());
        return httpHeaders;
    }

    private String encodeToBase64(String plaintext) {
        final String encodedBase64 = Base64Utils.encodeToString(plaintext.getBytes());

        LOG.trace("Encoded plaintext to Base64");
        return encodedBase64;
    }

    private HttpEntity<String> getHttpEntity(MultiValueMap<String, String> httpHeaders, Execution execution) {
        try {
            final String serializedExecutionAsJson = objectMapper.writeValueAsString(execution);

            LOG.trace("Prepared HttpEntity with body {}", serializedExecutionAsJson);
            return new HttpEntity<>(serializedExecutionAsJson, httpHeaders);
        } catch (JsonProcessingException e) {
            LOG.warn("Cannot serialize Execution object {}", execution.toString());
            throw new TestLinkApiException("Cannot serialize Execution object " + execution.toString());
        }
    }

    private void postTestResult(HttpEntity<String> httpEntity, URI testLinkExecutionsUri) {
        try {
            final ResponseEntity<String> response = restTemplate.exchange(testLinkExecutionsUri, HttpMethod.POST, httpEntity, String.class);
            LOG.trace("POSTed test result to {} with response code {}", testLinkExecutionsUri.toString(), response.getStatusCodeValue());
        } catch (RestClientException e) {
            LOG.warn("Cannot POST to {}", testLinkExecutionsUri.toString());
            throw new TestLinkApiException("Cannot POST to " + testLinkExecutionsUri.toString());
        }
    }

}
