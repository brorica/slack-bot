package slack.controller.dto.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties
public class KakaoAuthToken {

    private String token_type;

    private String access_token;

    private String id_token;

    private Integer expires_in;

    private String refresh_token;

    private Integer refresh_token_expires_in;

    private String scope;
}
