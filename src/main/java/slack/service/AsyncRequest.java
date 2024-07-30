package slack.service;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import slack.dto.QueryParam;

public abstract class AsyncRequest<T> {

    private final WebClient webClient;

    public AsyncRequest(WebClient webClient) {
        this.webClient = webClient;
    }

    public Mono<T> asyncSend(final String uri,
                        final HttpMethod method,
                        final QueryParam queryParam,
                        final Class<T> responseDto) {
        return webClient.method(method)
                .uri(uriBuilder -> uriBuilder
                        .path(uri)
                        .queryParams(queryParam.getQueryParamMap())
                        .build()
                )
                .header(HttpHeaders.AUTHORIZATION, "")
                .retrieve()
                .bodyToMono(responseDto);
    }
}
