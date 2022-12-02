package xyz.yang.ddd.core;

import java.time.Instant;

/**
 * @author yangxuehong
 * @version 1.0
 * @date 2020/5/18 20:07
 */
@SuppressWarnings("unused")
public interface Event extends Entity<Long> {
    Instant getOccurredOn();
}
