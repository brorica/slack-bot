package slack.bot;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.StandardCharsets;

@RestController
public class TestController {

    @GetMapping("/")
    public ResponseEntity<Void> test() throws Exception {
        String accessToken = "";
        String apiUrl = "https://slack.com/api/chat.postMessage";
        String parameters = "channel=bot-error&text=testtest&pretty=1";

        try {
            URL url = new URL(apiUrl + "?" + parameters);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("POST");

            // Authorization: Bearer 헤더 추가
            connection.setRequestProperty("Authorization", "Bearer " + accessToken);
            connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");

            // 요청 본문 설정
            connection.setDoOutput(true);
            byte[] paramBytes = parameters.getBytes(StandardCharsets.UTF_8);
            connection.getOutputStream().write(paramBytes);

            int responseCode = connection.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK) {
                // 성공적인 응답 처리
                System.out.println("Response: " + connection.getResponseMessage());
            } else {
                // 오류 처리
                System.out.println("Error: " + connection.getResponseMessage());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return ResponseEntity.ok().build();
    }

}
