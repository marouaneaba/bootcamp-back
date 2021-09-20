package bistrot.web.api.kafka;

import bistrot.common.EndPointBistrot;
import bistrot.compositionitem.dto.CompositionItemDto;
import bistrot.compositionitem.service.CompositionItemService;
import bistrot.infra.kafka.topic.Topic;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

@RestController

@RequiredArgsConstructor
public class KafkaController {


    private final CompositionItemService compositionItemService;

    @PostMapping(value = EndPointBistrot.KAFKA_PUBLISH, consumes = MediaType.APPLICATION_JSON_VALUE)
    public void produceStream(@PathVariable("topic-name") String topicName, @RequestBody CompositionItemDto compositionItemDto) {
        Topic topic = new Topic(topicName,1,Short.valueOf("1"));
        this.compositionItemService.sendStream(topic, compositionItemDto);
    }

}

