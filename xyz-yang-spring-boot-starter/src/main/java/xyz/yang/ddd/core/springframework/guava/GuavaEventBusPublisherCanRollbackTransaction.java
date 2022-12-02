package xyz.yang.ddd.core.springframework.guava;

import com.google.common.eventbus.EventBus;
import lombok.extern.slf4j.Slf4j;
import org.springframework.transaction.interceptor.TransactionAspectSupport;
import xyz.yang.toodles.EventPublisher;
import xyz.yang.toodles.ThreadLocalUtil;

/**
 * @author YangXuehong
 * @date 2021/10/9
 */
@Slf4j
@SuppressWarnings("UnstableApiUsage")
public class GuavaEventBusPublisherCanRollbackTransaction implements EventPublisher {
    private static final String SUBSCRIBER_EXCEPTION = "SUBSCRIBER_EXCEPTION";
    public static final String EXCEPTION_MESSAGE = "EXCEPTION_MESSAGE";
    private static final EventBus EVENT_BUS = new EventBus((throwable, subscriberExceptionContext) -> {
        TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
        setSubscriberThreadLocal(covertExceptionMessage(throwable));
        throw new EventBusSubscriberException(throwable);
    });

    private static String covertExceptionMessage(Throwable throwable) {
        return String.format("original {exception:%s message:%s}", throwable.getClass().getSimpleName(), throwable.getMessage());
    }

    private static void setSubscriberThreadLocal(String message) {
        ThreadLocalUtil.set(SUBSCRIBER_EXCEPTION, true);
        ThreadLocalUtil.set(EXCEPTION_MESSAGE, message);
    }

    private static void clearSubscriberExceptionThreadLocal() {
        ThreadLocalUtil.remove(SUBSCRIBER_EXCEPTION);
        ThreadLocalUtil.remove(EXCEPTION_MESSAGE);
    }


    @Override
    public void register(Object obj) {
        EVENT_BUS.register(obj);
    }

    @Override
    public void unregister(Object obj) {
        EVENT_BUS.unregister(obj);
    }

    @Override
    public void publish(Object obj) {
        EVENT_BUS.post(obj);
        boolean abnormal = ThreadLocalUtil.get(SUBSCRIBER_EXCEPTION) != null;
        if (abnormal) {
            RuntimeException runtimeException = new RuntimeException(ThreadLocalUtil.get(EXCEPTION_MESSAGE).toString());
            clearSubscriberExceptionThreadLocal();
            throw runtimeException;
        }
    }

    private static class EventBusSubscriberException extends RuntimeException {
        public EventBusSubscriberException(Throwable throwable) {
            super(throwable);
        }
    }
}
