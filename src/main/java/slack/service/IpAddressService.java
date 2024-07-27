package slack.service;

import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Slf4j
@Service
public class IpAddressService {

    @Resource(name = "ipAddressWebClient")
    private WebClient webClient;


    public void send() {
        Mono<String> response = webClient
                .get()
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
