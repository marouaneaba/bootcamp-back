package bistrot.common;

public interface EndPointBistrot {

    // Composition item Endpoint
    String COMPOSITION_ITEM = "/composition-items";
    String VERSION_1 = "v1";
    String VERSION_2 = "v2";

    // Kafka controller
    String KAFKA_PUBLISH = "/publish/{topic-name}";

}
