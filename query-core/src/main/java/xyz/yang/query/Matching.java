package xyz.yang.query;

import java.util.Objects;

/**
 * @author YangXuehong
 * @date 2022/3/4
 */
@SuppressWarnings("unused")
public record Matching(Type type, Field field, Object value) {
    public Matching {
        if (Objects.isNull(type)) {
            throw new IllegalArgumentException("The type could not be null. ");
        }
        if (Objects.isNull(field)) {
            throw new IllegalArgumentException("The field could not be null. ");
        }
        if (Objects.isNull(value)) {
            throw new IllegalArgumentException("The value could not be null. ");
        }
    }

    public static Matching of(Type type, Field field, Object value) {
        return new Matching(type, field, value);
    }
}
