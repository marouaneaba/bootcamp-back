package bistrot;

import com.intuit.karate.Results;
import com.intuit.karate.Runner;
import com.intuit.karate.junit5.Karate;

import static org.junit.jupiter.api.Assertions.assertEquals;

class FeatureRunner {

    @Karate.Test
    void testParallel() {
        Results results = Runner.path("classpath:bistrot/compositeitem")
                .parallel(5);
        assertEquals(0, results.getFailCount(), results.getErrorMessages());
    }

}
