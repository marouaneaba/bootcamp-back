package features;

import com.intuit.karate.Results;
import com.intuit.karate.Runner;
import com.intuit.karate.junit5.Karate;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import net.masterthought.cucumber.Configuration;
import net.masterthought.cucumber.ReportBuilder;

import static org.junit.jupiter.api.Assertions.assertEquals;

class SuitTests {

    @Karate.Test
    Karate testParallel() {
        Karate karateRun = Karate.run("classpath:features/");
        Results results = Runner.path("classpath:features/").outputCucumberJson(true).tags("~@ignore").parallel(5);
        System.out.println("report files:"+results.getReportDir());
        generateReport(results.getReportDir());
        assertEquals(0 , results.getFailCount(), results.getErrorMessages());
        return karateRun;
    }

    public static void generateReport(String karateOutputPath) {
        System.out.println("karateOutputPath : " + karateOutputPath);
        Collection<File> jsonFiles = FileUtils.listFiles(new File(karateOutputPath), new String[] {"json"}, true);
        System.out.println("jsonFiles.size():" + jsonFiles.size());
        List<String> jsonPaths = new ArrayList<>(jsonFiles.size());

        jsonFiles.forEach(file -> jsonPaths.add(file.getAbsolutePath()));
        Configuration config = new Configuration(new File("target"), "hub");
        ReportBuilder reportBuilder = new ReportBuilder(jsonPaths, config);
        reportBuilder.generateReports();
    }

}
