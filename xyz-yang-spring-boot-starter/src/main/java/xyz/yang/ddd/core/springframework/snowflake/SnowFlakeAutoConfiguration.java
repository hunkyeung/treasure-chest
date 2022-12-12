package xyz.yang.ddd.core.springframework.snowflake;

import lombok.Data;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import xyz.yang.toodles.UidGenerator;

/**
 * @author YangXuehong
 * @date 2022/2/10
 */
@Configuration
@Data
@ConfigurationProperties(prefix = "xyz.yang.uid.snow-flake")
@SuppressWarnings("unused")
public class SnowFlakeAutoConfiguration {
    private long machineId;
    private long dataCenterId;

    @ConditionalOnMissingBean(UidGenerator.class)
    @Bean
    public SnowFlakeUidGenerator uidGenerator() {
        return new SnowFlakeUidGenerator(dataCenterId, machineId);
    }
}
