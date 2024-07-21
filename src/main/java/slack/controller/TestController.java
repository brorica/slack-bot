package slack.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import slack.service.IpAddressService;
import slack.service.SlackBotService;

@RestController
public class TestController {

    private final SlackBotService slackBotService;

    private final IpAddressService ipAddressService;

    public TestController(final SlackBotService slackBotService, final IpAddressService ipAddressService) {
        this.slackBotService = slackBotService;
        this.ipAddressService = ipAddressService;
    }

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
}
