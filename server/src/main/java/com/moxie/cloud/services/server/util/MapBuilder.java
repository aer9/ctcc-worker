package com.moxie.cloud.services.server.util;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by zhangwt n 2017/12/28.
 * Builder模式构建map
 */
public class MapBuilder<K, V> {

    private Builder<K, V> b;

    public MapBuilder(Builder<K, V> b) {
        this.b = b;
    }

    public Map<K, V> map() {
        return b.map;
    }

    public V get(K key) {
        return b.map.get(key);
    }

    public static class Builder<K, V> {

        public Map<K, V> map;

        public Builder() {
            map = new HashMap<>();
        }

        public Builder<K, V> put(K key, V value) {
            map.put(key, value);
            return this;
        }


        public Map<K, V> build() {
            return new MapBuilder<K,V>(this).map();
        }

    }

}
