package slack.service;

import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import slack.controller.dto.response.KakaoAuthToken;

import java.nio.charset.StandardCharsets;

@Slf4j
@Service
public class KakaoLoginService {

    @Resource(name = "kakaoLoginWebClient")
    private WebClient webClient;

    @Value("${kakao.client-id}")
    private String CLIENT_ID;

    @Value("${kakao.redirect-uri}")
    private String REDIRECT_URI;

    private final String grantType = "authorization_code";

    public void send(final String code) {
        // bodyValue에 MultiValueMap을 넣으면 자동으로 application/x-www-form-urlencoded 이 붙음
        MultiValueMap<String, String> formData = new LinkedMultiValueMap<>();
        formData.set("client_id", CLIENT_ID);
        formData.set("redirect_uri", REDIRECT_URI);
        formData.set("grant_type", grantType);
        formData.set("code", code);

        Mono<KakaoAuthToken> response = webClient
                .post()
                .bodyValue(formData)
                .acceptCharset(StandardCharsets.UTF_8)
                .retrieve()
                .bodyToMono(KakaoAuthToken.class);

        response.subscribe(
                result -> log.info("token_type = {}\n"
                        + "access_token = {}\n"
                        + "id_token = {}\n"
                        + "expires_in = {}\n"
                        + "refresh_token = {}\n"
                        + "refresh_token_expires_in = {}\n"
                        + "scope = {}\n", result.getToken_type(), result.getAccess_token(), result.getId_token(), result.getExpires_in(),
                        result.getRefresh_token(), result.getRefresh_token_expires_in(), result.getScope()),
                error -> log.error("error = {}", error)
        );
    }
}
