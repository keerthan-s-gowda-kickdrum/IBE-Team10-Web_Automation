package TestRunner;
import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(
        features = "src/test/java/features",
        glue = {"stepDefinition", "hooks"},
        plugin = {
            "pretty",
            "html:target/cucumber-reports/cucumber-pretty.html",
            "json:target/cucumber-reports/CucumberTestReport.json"
        },
        tags = "@RoomResultsPage"

)
public class TestRunner {
}