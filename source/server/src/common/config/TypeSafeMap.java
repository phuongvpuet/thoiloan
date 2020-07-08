package common.config;

import common.Key;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Fresher_LOCAL on 7/2/2020.
 */
public class TypeSafeMap {
    private Map<Object, Object> data;

    public TypeSafeMap() {
        data = new HashMap<>();
    }

    public <K> void put(Key<K> kKey, K v) {
        data.put(kKey, v);
    }

    public <K> K get(Key<K> k) {
        return (K) data.get(k);
    }
}
