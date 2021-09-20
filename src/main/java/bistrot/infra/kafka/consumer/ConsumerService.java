package bistrot.infra.kafka.consumer;

import bistrot.compositionitem.entity.CompositionItem;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Slf4j
@Component
public class ConsumerService {

    private final Consumer consumer;

    @KafkaListener(topics = "Hello-Kafka", groupId = "groupId", containerFactory = "compositionItemsKafkaListenerContainerFactory")
    public void listen(@Payload CompositionItem compositionItem,
                               @Header(KafkaHeaders.RECEIVED_TOPIC) String topicName) {

        log.info("Start receive stream from topic: {}", topicName);

        consumer.run(compositionItem, topicName);

        log.info("Receive the stream to the topic {} is finished", topicName);
    }
}
