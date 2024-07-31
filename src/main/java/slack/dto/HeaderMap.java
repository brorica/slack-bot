package slack.dto;

import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import java.util.Arrays;
import java.util.List;

/**
 * 헤더에 담을 정보들을 관리하는 곳
 */
public class HeaderMap {

    private final MultiValueMap<String, String> headerMap;

    public HeaderMap() {
        headerMap = new LinkedMultiValueMap<>(8);
    }

    public HeaderMap(final int size) {
        headerMap = new LinkedMultiValueMap<>(size);
    }

    /**
     * 인증 헤더 값 추가
     * @param value
     */
    public void addAuthHeader(final String value) {
        headerMap.add(HttpHeaders.AUTHORIZATION, value);
    }

    /**
     * 단일 헤더 값 추가
     */
    public void add(final String key, final String value) {
        headerMap.add(key, value);
    }

    /**
     * 단일 헤더(콜렉션) 값 추가
     */
    public void add(final String key, final List<String> values) {
        headerMap.put(key, values);
    }

    /**
     * 복수 헤더(배열) 값 추가
     */
    public void add(final String key, final String... values) {
        headerMap.put(key, Arrays.asList(values));
    }

    /**
     * WebClient headers 빌더에 사용될 객체로 반환
     * 빈 객체로 반환될시, application/json 이 기본으로 붙는다.
     */
    public HttpHeaders getHttpHeaders() {
        if(headerMap.isEmpty()) {
            headerMap.add(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE);
        }
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.addAll(headerMap);
        return httpHeaders;
    }
}
