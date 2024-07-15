package slack.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import slack.service.SlackBotService;
import slack.service.TestService;

@RestController
public class TestController {

    private final SlackBotService slackBotService;

    private final TestService testService;

    public TestController(final SlackBotService slackBotService, final TestService testService) {
        this.slackBotService = slackBotService;
        this.testService = testService;
    }

    @GetMapping("/")
    public ResponseEntity<Void> test() throws Exception {
        slackBotService.send();
        testService.send();
        return ResponseEntity.ok().build();
    }
}
