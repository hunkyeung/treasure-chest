package xyz.yang.ddd.core;

import java.io.Serializable;

@SuppressWarnings("unused")
public interface Entity<T extends Serializable> extends Serializable {
    T getId();
}
