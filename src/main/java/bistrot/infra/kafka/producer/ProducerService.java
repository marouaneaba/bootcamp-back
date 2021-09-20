package bistrot.infra.kafka.producer;


import bistrot.compositionitem.entity.CompositionItem;
import bistrot.infra.kafka.topic.Topic;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;
import org.springframework.util.concurrent.FailureCallback;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.util.concurrent.SuccessCallback;

@Component
@RequiredArgsConstructor
@Slf4j
public class ProducerService implements Producer{

    private final KafkaTemplate<String, CompositionItem> kafkaTemplate;

    public void sendMessage(Topic topic, CompositionItem compositionItem) {
        log.info("Start send stream in topic: {}",topic.getName());

        ListenableFuture future = kafkaTemplate.send(topic.getName(), compositionItem);
        future.addCallback(
                success -> log.info("Stream is sending with success"),
                failure -> log.info("Failure to send stream in topic : {}", topic.getName()));

        log.info("Sending the stream to the topic {} is finished",topic.getName());
    }

    public void sendMessage(CompositionItem obj, Topic topic, SuccessCallback successCallback, FailureCallback failureCallback) {
        log.info("Start send stream in topic: {}",topic.getName());

        ListenableFuture future = kafkaTemplate.send(topic.getName(), obj);
        future.addCallback(successCallback, failureCallback);

        log.info("Sending the stream to the topic {} is finished",topic.getName());
    }
}
