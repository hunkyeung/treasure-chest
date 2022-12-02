package xyz.yang.toodles;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Map;

class ServiceLocatorTest {

    @BeforeEach
    void init() {
        ServiceLocator.setRegistry(new ServiceLocator.ServiceRegistry() {
            private final Map<Class<?>, Object> service = Map.of(String.class, "1", Long.class, 1L);
            private final Map<String, Class<?>> nameService = Map.of("String", String.class, "Long", Long.class);

            @Override
            @SuppressWarnings("unchecked")
            public <T> T getService(Class<T> requiredType) {
                return (T) service.get(requiredType);
            }

            @Override
            @SuppressWarnings("unchecked")
            public <T> T getService(String serviceName, Class<T> clazzType) {
                return (T) service.get(nameService.get(serviceName));
            }
        });
    }

    @Test
    void test() {
        String str = ServiceLocator.service(String.class);
        Assertions.assertEquals("1", str);
        Long l = ServiceLocator.service(Long.class);
        Assertions.assertEquals(1, l);
    }

    @Test
    void test2() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> ServiceLocator.service(Boolean.class));
    }

}