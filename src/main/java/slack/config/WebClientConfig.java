package slack.config;

import io.netty.channel.ChannelOption;
import io.netty.handler.timeout.ReadTimeoutHandler;
import io.netty.handler.timeout.WriteTimeoutHandler;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.netty.http.client.HttpClient;

import java.time.Duration;
import java.util.concurrent.TimeUnit;

@Configuration
public class WebClientConfig {

    @Value("${webclient.max-connect}")
    private int maxConnect;
    @Value("${webclient.connection-timeout}")
    private int connectionTimeout;
    @Value("${webclient.response-timeout}")
    private int responseTimeout;
    @Value("${webclient.read-timeout}")
    private int readTimeout;
    @Value("${webclient.write-timeout}")
    private int writeTimeout;

    /**
     * WebClient 생성에 필요한 초기 세팅
     * @return
     */
    @Bean
    public WebClientFactory getWebClientFactory() {
        HttpClient httpClient = getHttpClient();
        ReactorClientHttpConnector connector = new ReactorClientHttpConnector(httpClient);
        return new WebClientFactory(connector);
    }

    private HttpClient getHttpClient() {
        return HttpClient.create()
                .option(ChannelOption.CONNECT_TIMEOUT_MILLIS, connectionTimeout)
                .responseTimeout(Duration.ofMillis(responseTimeout))
                .doOnConnected(conn -> conn
                        .addHandlerLast(new ReadTimeoutHandler(readTimeout, TimeUnit.MILLISECONDS))
                        .addHandlerLast(new WriteTimeoutHandler(writeTimeout, TimeUnit.MILLISECONDS))
                );
    }
}
