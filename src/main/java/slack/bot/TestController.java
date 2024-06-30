package slack.bot;

import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@RestController
public class TestController {

    @GetMapping("/")
    public ResponseEntity<Void> test() throws Exception {
        String accessToken = "";
        MultiValueMap<String, String> parameters = new LinkedMultiValueMap<>();
        parameters.add("channel", "bot-error");
        parameters.add("text", "webflux test");
        parameters.add("pretty", "1");


        WebClient webClient = WebClient.create("https://slack.com/api/chat.postMessage");

        Mono<String> response = webClient.post()
                .header(HttpHeaders.AUTHORIZATION, accessToken)
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_FORM_URLENCODED_VALUE)
                .bodyValue(parameters)
                .retrieve()
                .bodyToMono(String.class);

        response.subscribe(
                result -> System.out.println("Response: " + result),
                error -> System.out.println("Error: " + error.getMessage())
        );
        return ResponseEntity.ok().build();
    }

}
