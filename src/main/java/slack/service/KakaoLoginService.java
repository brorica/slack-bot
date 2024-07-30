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
import slack.controller.dto.response.KakaoUserInfo;

import java.nio.charset.StandardCharsets;

@Slf4j
@Service
public class KakaoLoginService {

    @Resource(name = "kakaoLoginWebClient")
    private WebClient loginWebClient;

    @Value("${kakaoLogin.client-id}")
    private String CLIENT_ID;

    @Value("${kakaoLogin.redirect-uri}")
    private String REDIRECT_URI;

    @Resource(name = "kakaoUserWebClient")
    private WebClient userWebClient;

    private final String grantType = "authorization_code";

    public void send(final String code) {
        MultiValueMap<String, String> formData = createFormData(code);

        Mono<KakaoAuthToken> response = loginWebClient.post()
                .bodyValue(formData)
                .acceptCharset(StandardCharsets.UTF_8)
                .retrieve()
                .bodyToMono(KakaoAuthToken.class);

        response.doOnNext(this::logAuthToken)
                .doOnError(error -> log.error("Error occurred while retrieving Kakao user info: {}", error.getMessage()));

        response.subscribe(result -> getKakaoUserInfo(result.getAccessToken()));
    }

    private MultiValueMap<String, String> createFormData(String code) {
        MultiValueMap<String, String> formData = new LinkedMultiValueMap<>();
        formData.set("client_id", CLIENT_ID);
        formData.set("redirect_uri", REDIRECT_URI);
        formData.set("grant_type", grantType);
        formData.set("code", code);
        return formData;
    }

    private void logAuthToken(KakaoAuthToken token) {
        log.info("token_type = {}\naccess_token = {}\nid_token = {}\nexpires_in = {}\nrefresh_token = {}\nrefresh_token_expires_in = {}",
                token.getTokenType(),
                token.getAccessToken(),
                token.getIdToken(),
                token.getExpiresIn(),
                token.getRefreshToken(),
                token.getRefreshTokenExpiresIn());
    }

    public Mono<KakaoUserInfo> getKakaoUserInfo(final String accessToken) {
        Mono<KakaoUserInfo> response = userWebClient.post()
                .header("Authorization", "Bearer " + accessToken)
                .header("Content-type", "application/x-www-form-urlencoded")
                .acceptCharset(StandardCharsets.UTF_8)
                .retrieve()
                .bodyToMono(KakaoUserInfo.class);

        response.subscribe(
                result -> log.info("nickname = {} thumbnailUrl = {}", result.getNickName(), result.getThumbnailImageUrl()),
                error -> log.error("error = {}", error)
        );

        return response;
    }
}