package bistrot.infra.kafka.producer;

import bistrot.compositionitem.entity.CompositionItem;
import bistrot.infra.kafka.topic.Topic;
import org.springframework.util.concurrent.FailureCallback;
import org.springframework.util.concurrent.SuccessCallback;

public interface Producer {

    void sendMessage(Topic topic, CompositionItem message);

    void sendMessage(CompositionItem compositionItem, Topic topic, SuccessCallback successCallback, FailureCallback failureCallback);
}
