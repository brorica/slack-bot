package slack.controller.dto.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@JsonIgnoreProperties
public class KakaoUserInfo {

    private Long id;

    private Boolean hasSignedUp;

    private KakaoAccount kakaoAccount;

    public KakaoUserInfo(@JsonProperty("id") final Long id,
                         @JsonProperty("has_signed_up") final Boolean hasSignedUp,
                         @JsonProperty("kakao_account") final KakaoAccount kakaoAccount) {
        this.id = id;
        this.hasSignedUp = hasSignedUp;
        this.kakaoAccount = kakaoAccount;
    }

    public String getNickName() {
        return kakaoAccount.getNickName();
    }

    public String getThumbnailImageUrl() {
        return kakaoAccount.getThumbnailImageUrl();
    }

}
