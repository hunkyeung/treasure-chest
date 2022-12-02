package xyz.yang.persistence.mongo.core.multipletenant;

import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import xyz.yang.ddd.core.Entity;
import xyz.yang.persistence.mongo.core.AbstractRepositoryMongo;
import xyz.yang.persistence.mongo.core.MongoPageHelper;
import xyz.yang.query.Field;
import xyz.yang.query.Matching;
import xyz.yang.query.Type;
import xyz.yang.toodles.ServiceLocator;
import xyz.yang.toodles.ThreadLocalUtil;
import xyz.yang.toodles.UidGenerator;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * @author YangXuehong
 * @date 2021/8/20
 */
@SuppressWarnings({"rawtypes", "unused"})
public abstract class AbstractMultipleTenantRepositoryMongo<E extends Entity, I extends Serializable> extends AbstractRepositoryMongo<E, I> {

    protected AbstractMultipleTenantRepositoryMongo(MongoTemplate mongoTemplate, MongoPageHelper mongoPageHelper) {
        super(mongoTemplate, mongoPageHelper);
    }

    @Override
    @SuppressWarnings("unchecked")
    public E save(E e) {
        PersistentObject<E> po = getPersistentObject((I) e.getId()).orElse(new PersistentObject<>(ServiceLocator.service(UidGenerator.class).nextId(), getTenant(), e));
        return super.save((E) po);
    }

    @Override
    @SuppressWarnings("unchecked")
    public void delete(E e) {
        getPersistentObject((I) e.getId()).ifPresent(po -> mongoTemplate.remove(po, collectionName()));
    }

    @Override
    public void deleteById(I id) {
        getPersistentObject(id).ifPresent(po -> mongoTemplate.remove(po, collectionName()));
    }

    @Override
    public List<E> findAll() {
        return mongoTemplate.find(new Query(Criteria.where(PersistentObject.TENANT_FIELD).is(getTenant())), PersistentObject.class, collectionName()).stream().map(PersistentObject<E>::getEntity).toList();
    }

    @Override
    public Optional<E> findById(I id) {
        return Optional.ofNullable(getPersistentObject(id).orElse(new PersistentObject<>()).getEntity());
    }

    @SuppressWarnings("unchecked")
    private Optional<PersistentObject<E>> getPersistentObject(I id) {
        if (id == null) {
            throw new IllegalArgumentException("Entity id could not be null.");
        }
        PersistentObject.Tenant tenant = getTenant();
        return Optional.ofNullable(mongoTemplate.findOne(new Query(Criteria.where(PersistentObject.ENTITY_ID_FIELD).is(id).and(PersistentObject.TENANT_FIELD).is(tenant)), PersistentObject.class, collectionName()));
    }

    protected PersistentObject.Tenant getTenant() {
        return ThreadLocalUtil.get(PersistentObject.TENANT_FIELD);
    }

    @Override
    protected List<Matching> process(List<Matching> matching) {
        List<Matching> multipleTenantMatching = new ArrayList<>(matching);
        multipleTenantMatching.add(Matching.of(Type.EQ, Field.of(PersistentObject.TENANT_FIELD), getTenant()));
        return multipleTenantMatching;
    }
}
