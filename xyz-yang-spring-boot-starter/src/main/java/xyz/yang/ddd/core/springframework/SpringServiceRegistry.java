package xyz.yang.ddd.core.springframework;

import org.springframework.boot.context.event.ApplicationPreparedEvent;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;
import xyz.yang.toodles.ServiceLocator;

@SuppressWarnings("unused")
@Component
public class SpringServiceRegistry implements ServiceLocator.ServiceRegistry, ApplicationListener<ApplicationPreparedEvent> {
    private ApplicationContext context;

    @Override
    public <T> T getService(Class<T> requiredType) {
        return this.context.getBean(requiredType);
    }

    @Override
    public <T> T getService(String serviceName, Class<T> clazzType) {
        return this.context.getBean(serviceName, clazzType);
    }

    @Override
    public void onApplicationEvent(ApplicationPreparedEvent applicationPreparedEvent) {
        this.context = applicationPreparedEvent.getApplicationContext();
        ServiceLocator.setRegistry(this);
    }
}
