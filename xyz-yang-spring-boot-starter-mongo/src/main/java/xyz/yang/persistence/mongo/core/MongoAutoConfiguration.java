package xyz.yang.persistence.mongo.core;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.core.MongoTemplate;

@Configuration
@SuppressWarnings("unused")
public class MongoAutoConfiguration {
    @Bean
    public MongoPageHelper mongoPageHelper(MongoTemplate mongoTemplate) {
        return new MongoPageHelper(mongoTemplate);
    }
}
