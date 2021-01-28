import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;
import org.testng.annotations.*;

@CucumberOptions(
        features = "src/test/resources/features",
        glue = {"stepdefs"})

public class TestRunner extends AbstractTestNGCucumberTests {

    @Parameters({"baseURL", "browser", "gridURL" })
    @BeforeClass(alwaysRun = true)
    public void setUpClass(String baseURL, @Optional("chrome") String browser, @Optional("localhost:4444") String gridURL) {
        System.setProperty("baseURL", baseURL);
        System.setProperty("browser", browser);
        System.setProperty("gridURL", gridURL);
    }

   // Parallel scenarios execution     
   @Override
   @DataProvider(parallel = true)
   public Object[][] scenarios() {
       return super.scenarios();
   }
}
