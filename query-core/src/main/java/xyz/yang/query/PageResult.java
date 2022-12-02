package xyz.yang.query;

import java.io.Serializable;
import java.util.List;

/**
 * 分页结果.
 */
@SuppressWarnings("unused")
public record PageResult<T extends Serializable>(Integer currentPage, Integer pageSize, Long totalCount,
                                                 Integer totalPage, List<T> rows) implements Serializable {
    public <O extends Serializable> PageResult<O> of(List<O> rows) {
        return new PageResult<>(currentPage, pageSize, totalCount, totalPage, rows);
    }

}