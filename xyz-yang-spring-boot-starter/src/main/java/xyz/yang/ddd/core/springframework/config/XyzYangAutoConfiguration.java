package xyz.yang.ddd.core.springframework.config;

import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import xyz.yang.ddd.core.springframework.guava.GuavaEventBusPublisher;
import xyz.yang.ddd.core.springframework.guava.GuavaEventBusPublisherCanRollbackTransaction;
import xyz.yang.toodles.EventPublisher;

@Configuration
@SuppressWarnings("unused")
public class XyzYangAutoConfiguration {

    @ConditionalOnMissingBean(EventPublisher.class)
    @ConditionalOnProperty(prefix = "xyz.yang.transaction", name = "support-transaction")
    @Bean
    public EventPublisher guavaEventBusPublisherCanRollbackTransaction() {
        return new GuavaEventBusPublisherCanRollbackTransaction();
    }

    @ConditionalOnMissingBean(EventPublisher.class)
    @Bean
    public EventPublisher guavaEventBusPublisher() {
        return new GuavaEventBusPublisher();
    }

}
