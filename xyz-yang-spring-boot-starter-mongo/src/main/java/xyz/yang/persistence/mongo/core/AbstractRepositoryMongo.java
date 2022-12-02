package xyz.yang.persistence.mongo.core;

import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import xyz.yang.ddd.core.Entity;
import xyz.yang.ddd.core.Repository;
import xyz.yang.query.*;

import java.io.Serializable;
import java.util.*;

/**
 * @author YangXuehong
 * @date 2021/8/20
 */
@SuppressWarnings("rawtypes")
public abstract class AbstractRepositoryMongo<E extends Entity, I extends Serializable> implements Repository<E, I>, QueryRepository<E> {
    protected MongoTemplate mongoTemplate;
    protected MongoPageHelper mongoPageHelper;

    protected AbstractRepositoryMongo(MongoTemplate mongoTemplate, MongoPageHelper mongoPageHelper) {
        this.mongoTemplate = mongoTemplate;
        this.mongoPageHelper = mongoPageHelper;
    }

    protected String collectionName() {
        return this.getClass().getSimpleName();
    }

    public E save(E e) {
        mongoTemplate.save(e, collectionName());
        return e;
    }

    public void delete(E e) {
        mongoTemplate.remove(e, collectionName());
    }

    public void deleteById(I id) {
        Query query = Query.query(Criteria.where("_id").is(id));
        mongoTemplate.remove(query, collectionName());
    }

    @SuppressWarnings("unchecked")
    public List<E> findAll() {
        return mongoTemplate.findAll(Entity.class, collectionName()).stream().map(entity -> (E) entity).toList();
    }

    @SuppressWarnings("unchecked")
    public Optional<E> findById(I id) {
        return Optional.ofNullable((E) mongoTemplate.findById(id, Entity.class, collectionName()));
    }

    @Override
    public long countByCriteria(List<Matching> matching, KeyWord keyWord) {
        Query query = Query.query(buildCriteria(matching, keyWord));
        return mongoTemplate.count(query, collectionName());
    }

    @Override
    @SuppressWarnings("unchecked")
    public Optional<E> findOneByCriteria(List<Matching> matching, List<xyz.yang.query.Sort> sorts) {
        Query query = Query.query(buildCriteria(matching, null));
        query.with(Sort.by(buildOrder(sorts)));
        @SuppressWarnings("rawtypes") Entity entity = mongoTemplate.findOne(query, Entity.class, collectionName());
        return Optional.ofNullable((E) entity);
    }

    @Override
    public List<E> findByCriteria(xyz.yang.query.Query query) {
        return findByCriteria(query.getMatching(), query.getKeyWord(), query.getSorts());
    }

    @Override
    public PageResult<E> findByCriteria(xyz.yang.query.Query query, Page page) {
        return findByCriteria(query.getMatching(), query.getKeyWord(), query.getSorts(), page);
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<E> findByCriteria(List<Matching> matching, KeyWord keyWord, List<xyz.yang.query.Sort> sorts) {
        Query query = Query.query(buildCriteria(matching, keyWord));
        query.with(Sort.by(buildOrder(sorts)));
        @SuppressWarnings("rawtypes") List<Entity> entities = mongoTemplate.find(query, Entity.class, collectionName());
        return entities.stream().map(entity -> (E) entity).toList();
    }

    @Override
    @SuppressWarnings("unchecked")
    public PageResult<E> findByCriteria(List<Matching> matching, KeyWord keyWord, List<xyz.yang.query.Sort> sorts, Page page) {
        Query query = Query.query(buildCriteria(matching, keyWord));
        query.with(Sort.by(buildOrder(sorts)));
        @SuppressWarnings("rawtypes") PageResult<Entity> result = mongoPageHelper.pageQuery(query, Entity.class, page.size(), page.num(), collectionName());
        return new PageResult<>(result.currentPage(), result.pageSize(), result.totalCount(), result.totalPage(), result.rows().stream().map(entity -> (E) entity).toList());
    }

    protected Criteria buildCriteria(List<Matching> matching, KeyWord keyWord) {
        Criteria criteria = new Criteria();
        List<Matching> actualMatching = process(matching);
        actualMatching.stream().map(tmpMatching -> CriteriaType.of(tmpMatching, fieldMap())).toList().forEach(mongoCriteria -> mongoCriteria.toCriteria(criteria));
        if (keyWord != null) {
            String regexValue = ".*?" + keyWord.value() + ".*";
            List<Criteria> criteriaList = keyWord.fields().stream().map(field -> Criteria.where(field.value(fieldMap())).regex(regexValue)).toList();
            Criteria[] criteriaArray = new Criteria[criteriaList.size()];
            criteria.orOperator(criteriaList.toArray(criteriaArray));
        }
        return criteria;
    }

    protected List<Matching> process(List<Matching> matching) {
        return Optional.ofNullable(matching).orElse(new ArrayList<>(0));
    }

    private List<Sort.Order> buildOrder(List<xyz.yang.query.Sort> sorts) {
        return Optional.ofNullable(sorts).orElse(new ArrayList<>(0)).stream().sorted(Comparator.comparing(xyz.yang.query.Sort::order)).map(sort -> new Sort.Order(Sort.Direction.valueOf(sort.direction().name()), sort.field().value(fieldMap()))).toList();
    }

    //如传递的字段与数据库字段不一致，则在这里映射转换
    protected Map<String, String> fieldMap() {
        return new HashMap<>(0);
    }


}
