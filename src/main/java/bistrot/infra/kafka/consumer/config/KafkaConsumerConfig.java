package bistrot.infra.kafka.consumer.config;

import bistrot.compositionitem.entity.CompositionItem;
import org.apache.kafka.clients.consumer.ConsumerConfig;

import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.support.serializer.JsonDeserializer;

import java.util.HashMap;
import java.util.Map;

@EnableKafka
@Configuration
public class KafkaConsumerConfig {

    private static final String KAFKA_GROUP_ID = "groupId";

    @Value("${kafka.boostrapAddress.ip}")
    private String bootstrapAddress;

    @Value("${kafka.boostrapAddress.port}")
    private String bootstrapAddressPort;

    @Bean
    public ConsumerFactory<String, CompositionItem> consumerFactory() {
        Map<String, Object> props = new HashMap<>();
        props.put(
                ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, String.format("%s:%s",bootstrapAddress,bootstrapAddressPort));
        props.put(ConsumerConfig.GROUP_ID_CONFIG, KAFKA_GROUP_ID);
        props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);

        return new DefaultKafkaConsumerFactory<>(props, new StringDeserializer(),
                new JsonDeserializer<>(CompositionItem.class));
    }

    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, CompositionItem>
    compositionItemsKafkaListenerContainerFactory() {

        ConcurrentKafkaListenerContainerFactory<String, CompositionItem> factory =
                new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(consumerFactory());
        return factory;
    }
}
