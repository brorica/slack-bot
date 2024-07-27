package slack.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import slack.service.IpAddressService;
import slack.service.KakaoLoginService;
import slack.service.SlackBotService;

@Slf4j
@RequiredArgsConstructor
@RestController
public class TestController {

    private final SlackBotService slackBotService;

    private final IpAddressService ipAddressService;

    private final KakaoLoginService kakaoLoginService;

    @GetMapping("/")
    public ResponseEntity<Void> slack() {
        slackBotService.send();
        return ResponseEntity.ok().build();
    }

    @GetMapping("/ip")
    public ResponseEntity<Void> ipAddress() {
        ipAddressService.send();
        return ResponseEntity.ok().build();
    }

    @PostMapping("/login/kakao")
    public ResponseEntity<Void> kakaoLogin(@RequestBody String code) {
        kakaoLoginService.send(code);
        return ResponseEntity.ok().build();
    }
}
