package xyz.yang.persistence.mongo.core.multipletenant;

import xyz.yang.ddd.core.AbstractEntity;
import xyz.yang.ddd.core.Entity;
import xyz.yang.ddd.core.ValueObject;

import java.util.Objects;

/**
 * @author YangXuehong
 * @date 2021/8/18
 */
@SuppressWarnings({"rawtypes", "unused"})
public class PersistentObject<T extends Entity> extends AbstractEntity<Long> {
    public static final String TENANT_FIELD = "tenant";
    public static final String ENTITY_FIELD_PREFIX = "entity.";
    public static final String ENTITY_ID_FIELD = ENTITY_FIELD_PREFIX + "id";
    private Tenant tenant;
    private T entity;

    public PersistentObject() {
    }

    public PersistentObject(long id, Tenant tenant, T entity) {
        super(id);
        this.tenant = tenant;
        this.entity = entity;
    }

    public Tenant getTenant() {
        return tenant;
    }

    public T getEntity() {
        return entity;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        PersistentObject<?> that = (PersistentObject<?>) o;
        return Objects.equals(tenant, that.tenant) && Objects.equals(entity, that.entity);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), tenant, entity);
    }

    public record Tenant(String id, String name) implements ValueObject {
        public static Tenant of(String id, String name) {
            return new Tenant(id, name);
        }
    }
}




