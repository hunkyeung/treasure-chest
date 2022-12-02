package xyz.yang.ddd.core;

import java.io.Serializable;
import java.util.Objects;

@SuppressWarnings({"unused"})
public abstract class AbstractEntity<T extends Serializable> implements Entity<T> {
    private T id;

    protected AbstractEntity() {
    }

    protected AbstractEntity(T id) {
        setId(id);
    }

    private void setId(T id) {
        if (Objects.isNull(id)) {
            throw new IllegalArgumentException("The id could not be null. ");
        }
        this.id = id;
    }

    @Override
    public T getId() {
        return this.id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AbstractEntity<?> that = (AbstractEntity<?>) o;
        return id.equals(that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
