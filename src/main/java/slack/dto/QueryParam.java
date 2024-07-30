package slack.dto;

import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

public class QueryParam<K, V> {

    private final MultiValueMap<K, V> queryParamMap;

    public QueryParam() {
        this(16);
    }

    public QueryParam(final int size) {
        queryParamMap = new LinkedMultiValueMap<>(size);
    }

    public void add(K key, V value) {
        queryParamMap.add(key, value);
    }

    public MultiValueMap<K, V> getQueryParamMap() {
        return this.queryParamMap;
    }
}
