package slack.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import slack.dto.HeaderMap;
import slack.dto.SlackQueryParam;

@Slf4j
@Service
public class SlackBotService extends AsyncRequest{

    @Value("${slack.bearer-token}")
    private String AUTH_TOKEN;

    private static final String TEXT_PARAM = "text";

    public SlackBotService(@Qualifier("slackWebclient") WebClient webClient) {
        super(webClient);
    }

    public void send() {
        SlackQueryParam slackQueryParam = new SlackQueryParam();
        slackQueryParam.add(TEXT_PARAM, "test");

        HeaderMap headerMap = new HeaderMap();
        headerMap.addAuthHeader(AUTH_TOKEN);

        Mono mono = asyncSend(HttpMethod.POST, slackQueryParam, headerMap, String.class);

        mono.subscribe(
                result -> log.info("success = {}", result),
                error -> log.error("error = {}", error)
        );
    }
}
