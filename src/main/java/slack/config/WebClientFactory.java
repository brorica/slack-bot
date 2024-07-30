package slack.config;

import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.web.reactive.function.client.WebClient;

/**
 * WebClientConfig 에서 @Bean 으로 관리된다.
 * @see slack.config.WebClientConfig
 */
public class WebClientFactory {

    private final ReactorClientHttpConnector connector;

    public WebClientFactory(final ReactorClientHttpConnector connector) {
        this.connector = connector;
    }

    public WebClient getJsonClient(final String baseUrl) {
        return WebClient.builder()
                .baseUrl(baseUrl)
                .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .build();
    }

    public WebClient getFormClient(final String baseUrl) {
        return WebClient.builder()
                .baseUrl(baseUrl)
                .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_FORM_URLENCODED_VALUE)
                .build();
    }
}
