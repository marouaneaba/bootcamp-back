package bistrot.infra.kafka.consumer;

import bistrot.compositionitem.entity.CompositionItem;

public interface Consumer {

    /**
     * Method receive events
     * @param compositionItem Object retrieved
     * @param topicName topic name
     */
    void run(CompositionItem compositionItem, String topicName);
}
