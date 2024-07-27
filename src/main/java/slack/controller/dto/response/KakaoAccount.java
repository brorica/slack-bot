package slack.controller.dto.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties
public class KakaoAccount {

    // KakaoProfile외 다른 것들은 안 다룸
    private KakaoProfile profile;

    public String getNickName() {
        return profile.getNickname();
    }

    public String getThumbnailImageUrl() {
        return profile.getThumbnailImageUrl();
    }
}
