package slack.service;

import jakarta.annotation.PostConstruct;
import jakarta.annotation.Resource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Service
public class SlackBotService {

    @Resource(name = "slackWebClient")
    private WebClient webClient;

    @Value("${slack.channel}")
    private String CHANNEL;     // 게시할 채널
    @Value("${slack.username}")
    private String USERNAME;    // 슬랙 봇 이름

    private MultiValueMap<String, String> params;

    public SlackBotService(final WebClient webClient) {
        this.webClient = webClient;
        this.params = new LinkedMultiValueMap<>();
    }

    /**
     * 객체 생성 이후에 공통 정보 값 삽입
     */
    @PostConstruct
    private void init() {;
        this.params.add("channel", CHANNEL);
        this.params.add("username", USERNAME);
    }

    public void send() {
        params.set("text", "test");

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
