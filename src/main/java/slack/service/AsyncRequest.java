package slack.service;

import org.springframework.http.HttpMethod;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import slack.dto.HeaderMap;
import slack.dto.QueryParam;

/**
 * 외부로 요청을 보내는 곳
 * @param <T>
 */
public abstract class AsyncRequest<T> {

    private final WebClient webClient;

    public AsyncRequest(final WebClient webClient) {
        this.webClient = webClient;
    }

    public Mono<T> asyncSend(final HttpMethod method,
                             final QueryParam queryParam,
                             final HeaderMap headerMap,
                             final Class<T> responseDto) {
        return asyncSend("", method, queryParam, headerMap, responseDto);
    }

    public Mono<T> asyncSend(final String uri,
                        final HttpMethod method,
                        final QueryParam queryParam,
                        final HeaderMap headerMap,
                        final Class<T> responseDto) {
        return webClient.method(method)
                .uri(uriBuilder -> uriBuilder
                        .path(uri)
                        .queryParams(queryParam.getQueryParamMap())
                        .build()
                )
                .headers(httpHeaders -> httpHeaders.addAll(headerMap.getHeaderMap()))
                .retrieve()
                .bodyToMono(responseDto);
    }
}
