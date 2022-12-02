package xyz.yang.query;

import java.util.Objects;

/**
 * @author YangXuehong
 * @date 2022/3/4
 */
@SuppressWarnings("unused")
public record Sort(int order, Field field, Direction direction) {
    public static Sort of(int order, String field, Direction direction) {
        if (Objects.isNull(direction)) {
            direction = Direction.DESC;
        }
        return new Sort(order, Field.of(field), direction);
    }

    public static Sort of(int order, String field) {
        return of(order, field, Direction.DESC);
    }

    public static Sort of(String field) {
        return of(0, field, Direction.DESC);
    }
}
