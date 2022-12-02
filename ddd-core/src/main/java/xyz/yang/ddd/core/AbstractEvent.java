package xyz.yang.ddd.core;

import java.time.Instant;
import java.util.Objects;

/**
 * @author yangxuehong
 * @version 1.0
 * @date 2020/5/22 12:55
 */

@SuppressWarnings("unused")
public abstract class AbstractEvent extends AbstractEntity<Long> implements Event {
    private final Instant occurredOn;

    protected AbstractEvent(Long id, Instant occurredOn) {
        super(id);
        if (Objects.isNull(occurredOn)) {
            throw new IllegalArgumentException("The occurredOn could not be null.");
        }
        this.occurredOn = occurredOn;
    }

    @Override
    public Instant getOccurredOn() {
        return this.occurredOn;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        AbstractEvent that = (AbstractEvent) o;
        return Objects.equals(occurredOn, that.occurredOn);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), occurredOn);
    }
}
