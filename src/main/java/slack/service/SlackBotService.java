package slack.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import reactor.core.publisher.Mono;
import slack.config.WebClientFactory;

import java.time.Duration;

@Service
public class SlackBotService {

    private final WebClientFactory webClientFactory;

    @Value("${slack.base-url}")
    private String BASE_URL = "https://slack.com/api/chat.postMessage";
    @Value("${slack.bearer-token}")
    private String AUTH_TOKEN;
    @Value("${slack.channel}")
    private String CHANNEL;     // 게시할 채널
    @Value("${slack.username}")
    private String USERNAME;    // 슬랙 봇 이름

    public SlackBotService(final WebClientFactory webClientFactory) {
        this.webClientFactory = webClientFactory;
    }

    public void send() {
        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.set("text", "test");
        params.set("channel", CHANNEL);
        params.set("username", USERNAME);

        Mono<String> response = webClientFactory.getClient(BASE_URL, AUTH_TOKEN)
                .post()
                .bodyValue(params)
                .retrieve()
                .bodyToMono(String.class).timeout(Duration.ofMillis(5000));
        /*
         * response 결과를 처리하는 부분
         */
        response.subscribe(
                result -> System.out.println("Response = " + result),
                error -> System.err.println("error = " + error)
        );
    }
}
