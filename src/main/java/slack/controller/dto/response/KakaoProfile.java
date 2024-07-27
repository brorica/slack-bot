package slack.controller.dto.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@JsonIgnoreProperties
public class KakaoProfile {

    private String nickname;

    private String thumbnailImageUrl;

    private String profileImageUrl;

    private Boolean isDefaultImage;

    private Boolean isDefaultNickname;

    public KakaoProfile(@JsonProperty("nickname") final String nickname,
                        @JsonProperty("thumbnail_image_url") final String thumbnailImageUrl,
                        @JsonProperty("profile_image_url") final String profileImageUrl,
                        @JsonProperty("is_default_image") final Boolean isDefaultImage,
                        @JsonProperty("is_default_nickname") final Boolean isDefaultNickname) {
        this.nickname = nickname;
        this.thumbnailImageUrl = thumbnailImageUrl;
        this.profileImageUrl = profileImageUrl;
        this.isDefaultImage = isDefaultImage;
        this.isDefaultNickname = isDefaultNickname;
    }
}
