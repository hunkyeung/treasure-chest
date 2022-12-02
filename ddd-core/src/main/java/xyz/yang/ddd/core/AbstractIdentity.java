package xyz.yang.ddd.core;

import java.io.Serializable;
import java.util.Objects;


@SuppressWarnings("unused")
public abstract class AbstractIdentity<T extends Serializable> implements Identity<T> {
    private T value;

    protected AbstractIdentity(T value) {
        this.setValue(value);
    }

    @Override
    public T getValue() {
        return value;
    }

    private void setValue(T value) {
        if (Objects.isNull(value)) {
            throw new IllegalArgumentException("The value could not be null.");
        }
        this.validateValue(value);
        this.value = value;
    }

    protected void validateValue(T value) {
        // validate value of Identity if need
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        AbstractIdentity<?> that = (AbstractIdentity<?>) o;
        return value.equals(that.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName() + " [value=" + value + "]";
    }

}
