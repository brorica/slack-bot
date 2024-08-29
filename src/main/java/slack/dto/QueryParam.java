package slack.dto;

import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

/**
 * GET 요청에 사용될 쿼리 파라미터를 관리하는 곳
 * @param <K>
 * @param <V>
 */
public class QueryParam<K, V> {

    private final MultiValueMap<K, V> queryParamMap;

    public QueryParam() {
        this(16);
    }

    public QueryParam(final int size) {
        queryParamMap = new LinkedMultiValueMap<>(size);
    }

    /**
     * 쿼리 파라미터 추가
     */
    public void add(final K key, final V value) {
        queryParamMap.add(key, value);
    }

    public void set(final K key, final V value) {
        queryParamMap.set(key, value);
    }

    public MultiValueMap<K, V> getQueryParamMap() {
        return this.queryParamMap;
    }
}
