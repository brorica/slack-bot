package slack.dto;

/**
 * 슬랙 봇에 전송할 정보
 */
public class SlackQueryParam extends QueryParam<String, String> {

    private static final String CHANNEL_PARAM = "channel";  // 게시할 채널
    private static final String USERNAME_PARAM = "username";// 슬랙 봇 이름

    public SlackQueryParam() {
        super();
        add(CHANNEL_PARAM, "bot-error");
        add(USERNAME_PARAM, "ErrorBot");
    }
}
