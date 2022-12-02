package xyz.yang.toodles;

import com.alibaba.ttl.TransmittableThreadLocal;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

/**
 * @author YangXuehong
 * @date 2021/7/13
 */

@SuppressWarnings({"unchecked", "unused", "UnusedReturnValue"})
public class ThreadLocalUtil {
    private static final ThreadLocal<Map<String, Object>> threadLocal = new TransmittableThreadLocal<>() {
        @Override
        protected Map<String, Object> initialValue() {
            return new HashMap<>();
        }
    };

    public static Map<String, Object> getThreadLocal() {
        return threadLocal.get();
    }

    private ThreadLocalUtil() {
    }

    public static <T> T get(String key) {
        return get(key, null);
    }

    public static <T> T get(String key, T defaultValue) {
        Map<String, Object> map = threadLocal.get();
        return (T) Optional.ofNullable(map.get(key)).orElse(defaultValue);
    }

    public static void set(String key, Object value) {
        Map<String, Object> map = threadLocal.get();
        map.put(key, value);
    }

    public static void set(Map<String, Object> keyValueMap) {
        Map<String, Object> map = threadLocal.get();
        map.putAll(keyValueMap);
    }

    public static void remove() {
        threadLocal.remove();
    }

    public static <T> T remove(String key) {
        Map<String, Object> map = threadLocal.get();
        return (T) map.remove(key);
    }

}