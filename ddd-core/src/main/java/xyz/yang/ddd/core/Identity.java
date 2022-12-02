package xyz.yang.ddd.core;

import java.io.Serializable;

@SuppressWarnings("unused")
public interface Identity<T extends Serializable> extends ValueObject {
    T getValue();
}