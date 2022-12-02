package xyz.yang.query;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

/**
 * @author YangXuehong
 * @date 2022/3/4
 */
@SuppressWarnings("unused")
public record KeyWord(String value, List<Field> fields) {
    public KeyWord {
        if (Objects.isNull(value)) {
            throw new IllegalArgumentException("The value could not be null. ");
        }
        if (Objects.isNull(fields) || fields.isEmpty()) {
            throw new IllegalArgumentException("The fields could not be null or empty. ");
        }
    }

    public static KeyWord of(String value, String[] fields) {
        return new KeyWord(value, Arrays.stream(fields).map(Field::of).toList());
    }
}
