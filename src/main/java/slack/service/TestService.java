package slack.service;

import jakarta.annotation.Resource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Service
public class TestService {

    private final WebClient webClient;

    @Value("${slack.base-url}")
    private String BASE_URL = "https://slack.com/api/chat.postMessage";
    @Value("${slack.bearer-token}")
    private String AUTH_TOKEN = "토큰토큰";
    @Value("${slack.channel}")
    private String CHANNEL;     // 게시할 채널
    @Value("${slack.username}")
    private String USERNAME;    // 슬랙 봇 이름

    public TestService(final WebClient.Builder webClientBuilder) {
        this.webClient = webClientBuilder
                .baseUrl(BASE_URL)
                .defaultHeader(HttpHeaders.AUTHORIZATION, AUTH_TOKEN)
                .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_FORM_URLENCODED_VALUE)
                .build();
    }

    public void send() {
        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("channel", CHANNEL);
        params.add("username", USERNAME);
        params.set("text", "test2");

        Mono<String> response = webClient
                .post()
                .bodyValue(params)
                .retrieve()
                .bodyToMono(String.class);
        /*
         * response 결과를 처리하는 부분
         */
        response.subscribe(
                result -> System.out.println("Response = " + result),
                error -> System.err.println("error = " + error)
        );
    }
}
