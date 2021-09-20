package bistrot.infra.kafka.topic.config;


import org.apache.kafka.clients.admin.AdminClientConfig;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.KafkaAdmin;

import java.util.*;

@Configuration
public class KafkaTopicConfig {


    @Value("${kafka.boostrapAddress.ip}")
    private String bootstrapAddress;

    @Value("${kafka.boostrapAddress.port}")
    private String bootstrapAddressPort;

    @Bean
    public KafkaAdmin kafkaAdmin() {
        Map<String, Object> configs = new HashMap<>();
        configs.put(AdminClientConfig.BOOTSTRAP_SERVERS_CONFIG, String.format("%s:%s",bootstrapAddress,bootstrapAddressPort));
        return new KafkaAdmin(configs);
    }
}
