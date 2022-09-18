import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(
        features = "src/test/features",
        glue = "ru.savkk.test",
        tags = "@all",
        dryRun = false,
        snippets = CucumberOptions.SnippetType.UNDERSCORE
)
public class RunnerTest {
}
