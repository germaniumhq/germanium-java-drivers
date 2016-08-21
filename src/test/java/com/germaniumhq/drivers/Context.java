package com.germaniumhq.drivers;

import java.util.HashMap;
import java.util.Map;

/**
 * Singleton class to provide Cucumber contexts.
 */
public class Context {
    private static Map<String, Object> map = new HashMap<>();

    public static <T> T get(String key) {
        return (T) map.get(key);
    }

    public static <T> void set(String key, T value) {
        map.put(key, value);
    }
}
