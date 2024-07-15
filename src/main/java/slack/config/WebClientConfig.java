package slack.config;

import io.netty.channel.ChannelOption;
import io.netty.handler.timeout.ReadTimeoutHandler;
import io.netty.handler.timeout.WriteTimeoutHandler;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.netty.http.client.HttpClient;

import java.time.Duration;
import java.util.concurrent.TimeUnit;

@Configuration
public class WebClientConfig {

    @Primary
    @Bean(name = "slackWebClient")
    public WebClient slackWebClient(@Value("${slack.base-url}") String baseUrl, @Value("${slack.bearer-token}") String token) {
        return WebClient.builder()
                .clientConnector(new ReactorClientHttpConnector(httpClient()))
                .baseUrl(baseUrl)
                .defaultHeader(HttpHeaders.AUTHORIZATION, token)
                .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_FORM_URLENCODED_VALUE)
                .build();
    }

    @Bean(name = "testApiClient")
    public WebClient TestWebClient(@Value("${test.base-url}") String baseUrl) {
        return WebClient.builder()
                .clientConnector(new ReactorClientHttpConnector(httpClient()))
                .baseUrl(baseUrl)
                .defaultHeader(HttpHeaders.AUTHORIZATION, "토큰토큰")
                .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_FORM_URLENCODED_VALUE)
                .build();
    }

    private HttpClient httpClient() {
        // 커넥션 맺기, response 대기, read, write 대기 시간을 모두 5초로
        return HttpClient.create()
                .option(ChannelOption.CONNECT_TIMEOUT_MILLIS, 5000)
                .responseTimeout(Duration.ofMillis(5000))
                .doOnConnected(conn -> conn
                        .addHandlerLast(new ReadTimeoutHandler(5000, TimeUnit.MILLISECONDS))
                        .addHandlerLast(new WriteTimeoutHandler(5000, TimeUnit.MILLISECONDS))
                );
    }
}
