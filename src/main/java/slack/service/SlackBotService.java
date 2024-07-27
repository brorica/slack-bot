package slack.service;

import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Slf4j
@Service
public class SlackBotService {

    @Resource(name = "slackWebclient")
    private WebClient webClient;

    private String AUTH_TOKEN;
    @Value("${slack.channel}")
    private String CHANNEL;     // 게시할 채널
    @Value("${slack.username}")
    private String USERNAME;    // 슬랙 봇 이름

    public void send() {
        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.set("text", "test");
        params.set("channel", CHANNEL);
        params.set("username", USERNAME);

        Mono<String> response = webClient
                .post()
                .bodyValue(params)
                .retrieve()
                .bodyToMono(String.class);
        /*
         * response 결과를 처리하는 부분
         */
        response.subscribe(
                result -> log.info("Response = {}", result),
                error -> log.error("error = {}", error)
        );
    }
}
