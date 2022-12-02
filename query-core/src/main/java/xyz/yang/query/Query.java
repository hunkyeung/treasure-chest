package xyz.yang.query;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

/**
 * @author YangXuehong
 * @date 2022/3/4
 */
@SuppressWarnings("unused")
public class Query {
    private final List<Matching> matching;
    private final KeyWord keyWord;
    private final List<Sort> sorts;

    public List<Matching> getMatching() {
        return matching;
    }

    public KeyWord getKeyWord() {
        return keyWord;
    }

    public List<Sort> getSorts() {
        return sorts;
    }

    public Query(Builder builder) {
        this.matching = builder.matching;
        this.keyWord = builder.keyWord;
        this.sorts = builder.sorts;
    }

    @SuppressWarnings("unused")
    public static class Builder {
        private List<Matching> matching;
        private KeyWord keyWord;
        private List<Sort> sorts;

        public Builder() {
            //do nothing
        }

        public Builder matching(Type type, String field, Object... objects) {
            if (!Objects.isNull(type) && !Objects.isNull(field) && Objects.isNull(objects)) {
                List<Object> objectList = Arrays.stream(objects).filter(Objects::nonNull).toList();
                if (!objectList.isEmpty()) {
                    if (Objects.isNull(this.matching)) {
                        this.matching = new ArrayList<>();
                    }
                    if (objectList.size() == 1) {
                        this.matching.add(Matching.of(type, Field.of(field), objectList.get(0)));
                    } else {
                        this.matching.add(Matching.of(type, Field.of(field), objectList));
                    }
                }
            }
            return this;
        }

        public Builder keyWord(String value, String... fields) {
            if (Objects.isNull(value) || Objects.isNull(fields) || fields.length == 0) {
                return this;
            }
            this.keyWord = KeyWord.of(value, fields);
            return this;
        }

        public Builder sort(int order, String field, Direction direction) {
            if (Objects.isNull(field)) {
                return this;
            }
            if (Objects.isNull(this.sorts)) {
                this.sorts = new ArrayList<>();
            }
            this.sorts.add(Sort.of(order, field, direction));
            return this;
        }

        public Builder sort(String field) {
            return sort(0, field, Direction.DESC);
        }

        public Query build() {
            return new Query(this);
        }
    }

}
