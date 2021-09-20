package bistrot.infra.kafka.topic;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class Topic {

    private String name;
    private int numPartitions;
    private short replicartionFactor;

}
