package org.fanlychie.security.jwt.util;

import java.util.HashMap;
import java.util.Map;

/**
 * 模拟缓存
 *
 * Created by fanlychie on 2019/7/8.
 */
public final class CacheImitationUtils {

    private static final Map<String, Object> CACHE_MAP = new HashMap<>();

    public static void put(String key, Object value) {
        CACHE_MAP.put(key, value);
    }

    public static <T> T get(String key) {
        return (T) CACHE_MAP.get(key);
    }

    public static void remove(String key) {
        CACHE_MAP.remove(key);
    }

}