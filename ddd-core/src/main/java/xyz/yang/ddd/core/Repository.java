package xyz.yang.ddd.core;

import java.io.Serializable;
import java.util.List;
import java.util.Optional;

@SuppressWarnings({"rawtypes", "unused"})
public interface Repository<E extends Entity, I extends Serializable> {
    Optional<E> findById(I id);

    E save(E e);

    void delete(E e);

    void deleteById(I id);

    List<E> findAll();

}
