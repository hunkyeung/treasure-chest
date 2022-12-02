package xyz.yang.toodles;

@SuppressWarnings("unused")
public interface EventPublisher {
    void register(Object obj);

    void unregister(Object obj);

    void publish(Object obj);
}
