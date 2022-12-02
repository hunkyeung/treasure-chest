package xyz.yang.query;

import java.io.Serializable;
import java.util.List;
import java.util.Optional;

@SuppressWarnings("unused")
public interface QueryRepository<Q extends Serializable> {

    long countByCriteria(List<Matching> matching, KeyWord keyWord);

    Optional<Q> findOneByCriteria(List<Matching> matching, List<Sort> sorts);

    List<Q> findByCriteria(Query query);

    PageResult<Q> findByCriteria(Query query, Page page);

    List<Q> findByCriteria(List<Matching> matching, KeyWord keyWord, List<Sort> sorts);

    PageResult<Q> findByCriteria(List<Matching> matching, KeyWord keyWord, List<Sort> sorts, Page page);
}
