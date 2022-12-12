package xyz.yang.ddd.core.springframework.guava;

import com.google.common.eventbus.Subscribe;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.lang.NonNull;
import xyz.yang.toodles.EventPublisher;
import xyz.yang.toodles.ServiceLocator;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;

/**
 * @author YangXuehong
 * @date 2021/10/11
 */
@Slf4j
@SuppressWarnings({"unused", "UnstableApiUsage"})
public class GuavaEventBusSubscribeBeanProcessor implements BeanPostProcessor {
    @Override
    public Object postProcessAfterInitialization(Object bean, @NonNull String beanName) throws BeansException {
        Method[] methods = bean.getClass().getMethods();
        for (Method method : methods) {
            Annotation annotation = AnnotationUtils.findAnnotation(method, Subscribe.class);
            if (annotation != null) {
                log.debug("Register the subscriber[{}] to the EventBus. ", beanName);
                ServiceLocator.service(EventPublisher.class).register(bean);
                break;
            }
        }
        return BeanPostProcessor.super.postProcessAfterInitialization(bean, beanName);
    }
}
