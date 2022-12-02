package xyz.yang.query;

import java.util.Map;
import java.util.Objects;

/**
 * @author YangXuehong
 * @date 2022/3/4
 */
@SuppressWarnings("unused")
public record Field(String value) {
    public Field {
        if (Objects.isNull(value)) {
            throw new IllegalArgumentException("The field could not be null. ");
        }
    }

    public static Field of(String value) {
        return new Field(value);
    }

    public String value(Map<String, String> fieldMap) {
        if (Objects.isNull(fieldMap) || Objects.isNull(fieldMap.get(value))) {
            return value;
        } else {
            return fieldMap.get(value);
        }
    }
}
