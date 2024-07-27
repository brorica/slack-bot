package slack.controller.dto.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@JsonIgnoreProperties
public class KakaoAuthToken {

    private String tokenType;

    private String accessToken;

    private String idToken;

    private Integer expiresIn;

    private String refreshToken;

    private Integer refreshTokenExpiresIn;

    public KakaoAuthToken(@JsonProperty("token_type") final String tokenType,
                          @JsonProperty("access_token") String accessToken,
                          @JsonProperty("id_token") String idToken,
                          @JsonProperty("expires_in") Integer expiresIn,
                          @JsonProperty("refresh_token") String refreshToken,
                          @JsonProperty("refresh_token_expires_in") Integer refreshTokenExpiresIn) {
        this.tokenType = tokenType;
        this.accessToken = accessToken;
        this.idToken = idToken;
        this.expiresIn = expiresIn;
        this.refreshToken = refreshToken;
        this.refreshTokenExpiresIn = refreshTokenExpiresIn;
    }
}
