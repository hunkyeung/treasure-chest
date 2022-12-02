package xyz.yang.ddd.core.springframework.guava;

import com.google.common.eventbus.EventBus;
import lombok.extern.slf4j.Slf4j;
import xyz.yang.toodles.EventPublisher;

@Slf4j
@SuppressWarnings("UnstableApiUsage")
public class GuavaEventBusPublisher implements EventPublisher {
    private final EventBus eventBus = new EventBus();

    @Override
    public void register(Object obj) {
        this.eventBus.register(obj);
    }

    @Override
    public void unregister(Object obj) {
        this.eventBus.unregister(obj);
    }

    @Override
    public void publish(Object obj) {
        this.eventBus.post(obj);
    }

}
