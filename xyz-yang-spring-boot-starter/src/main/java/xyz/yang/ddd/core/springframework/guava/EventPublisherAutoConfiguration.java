package xyz.yang.ddd.core.springframework.guava;

import com.google.common.eventbus.Subscribe;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import xyz.yang.toodles.EventPublisher;

@Configuration
@SuppressWarnings({"unused", "UnstableApiUsage"})
public class EventPublisherAutoConfiguration {

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

    @Bean
    @ConditionalOnBean(EventPublisher.class)
    @ConditionalOnClass(Subscribe.class)
    public BeanPostProcessor beanPostProcessor() {
        return new GuavaEventBusSubscribeBeanProcessor();
    }
}
