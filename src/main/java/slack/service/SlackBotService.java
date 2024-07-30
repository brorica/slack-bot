package slack.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import slack.dto.SlackQueryParam;

@Slf4j
@Service
public class SlackBotService extends AsyncRequest{

    @Value("${slack.bearer-token}")
    private String AUTH_TOKEN;
    @Value("${slack.channel}")
    private String CHANNEL;     // 게시할 채널
    @Value("${slack.username}")
    private String USERNAME;

    private static final String TEXT_PARAM = "text";

    public SlackBotService(@Qualifier("slackWebclient") WebClient webClient) {
        super(webClient);
    }

    public void send() {
        SlackQueryParam slackQueryParam = new SlackQueryParam();
        slackQueryParam.add(TEXT_PARAM, "test");

        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.set("text", "test");
        params.set("channel", CHANNEL);
        params.set("username", USERNAME);

        Mono mono = asyncSend("", HttpMethod.POST, slackQueryParam, String.class);

        mono.subscribe(
                result -> log.info("success = {}", result),
                error -> log.error("error = {}", error)

        );
    }
}
