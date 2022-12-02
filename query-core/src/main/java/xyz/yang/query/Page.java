package xyz.yang.query;

/**
 * @author YangXuehong
 * @date 2022/3/4
 */
@SuppressWarnings("unused")
public record Page(int size, int num) {

    public static Page of(int size, int num) {
        return new Page(size, num);
    }
}
