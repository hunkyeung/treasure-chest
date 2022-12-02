package xyz.yang.toodles;

import java.util.Objects;
import java.util.Optional;

/**
 * @author YangXuehong
 * @date 2022/4/25
 */
@SuppressWarnings("unused")
public class ServiceLocator {
    private static ServiceRegistry registry;

    public static <T> T service(Class<T> requiredType) {
        if (Objects.isNull(registry)) {
            throw new IllegalStateException("Please initiate registry before using service method.");
        }
        return Optional.ofNullable(registry.getService(requiredType)).orElseThrow(() -> new IllegalArgumentException(String.format("%s could not be found in registry.", requiredType.getName())));
    }

    public static void setRegistry(ServiceRegistry registry) {
        ServiceLocator.registry = registry;
    }

    public interface ServiceRegistry {
        <T> T getService(Class<T> requiredType);

        <T> T getService(String serviceName, Class<T> clazzType);
    }
}
