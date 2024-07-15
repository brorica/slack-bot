package slack.service;

import jakarta.annotation.Resource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Service
public class TestService {

    @Resource(name = "testApiClient")
    private WebClient webClient;

    @Value("${slack.channel}")
    private String CHANNEL;     // 게시할 채널
    @Value("${slack.username}")
    private String USERNAME;    // 슬랙 봇 이름

    public TestService(final WebClient webClient) {
        this.webClient = webClient;
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
