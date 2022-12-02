package xyz.yang.persistence.mongo.core;

import xyz.yang.query.Matching;
import xyz.yang.query.Type;

import java.util.List;
import java.util.Map;

/**
 * @author YangXuehong
 * @date 2022/3/4
 */
@SuppressWarnings("unchecked")
public class CriteriaType {
    private CriteriaType() {
    }

    public static MongoCriteria of(Matching matching, Map<String, String> fieldMap) {
        Type type = matching.type();
        MongoCriteria mongoCriteria = null;
        switch (type) {
            case EQ -> mongoCriteria = MongoCriteria.ofEq(matching.field().value(fieldMap), matching.value());
            case GT -> mongoCriteria = MongoCriteria.ofGt(matching.field().value(fieldMap), matching.value());
            case GTE -> mongoCriteria = MongoCriteria.ofGte(matching.field().value(fieldMap), matching.value());
            case LT -> mongoCriteria = MongoCriteria.ofLt(matching.field().value(fieldMap), matching.value());
            case LTE -> mongoCriteria = MongoCriteria.ofLte(matching.field().value(fieldMap), matching.value());
            case IN ->
                    mongoCriteria = MongoCriteria.ofIn(matching.field().value(fieldMap), (List<Object>) matching.value());
            case NIN ->
                    mongoCriteria = MongoCriteria.ofNin(matching.field().value(fieldMap), (List<Object>) matching.value());
            case LIKE -> mongoCriteria = MongoCriteria.ofLike(matching.field().value(fieldMap), matching.value());
            case BETWEEN ->
                    mongoCriteria = MongoCriteria.ofBetween(matching.field().value(fieldMap), (List<Object>) matching.value());
            default -> {
                //do nothing
            }
        }
        return mongoCriteria;
    }
}
