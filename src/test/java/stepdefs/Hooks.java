package stepdefs;

import test.framework.Initialization;
import com.google.inject.Inject;
import io.cucumber.java.After;
import io.cucumber.java.Scenario;
import io.qameta.allure.Attachment;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;


public class Hooks {

    @Inject
    Initialization initialization;

    @After
    public void tearDown(Scenario scenario) {
        if (scenario.isFailed()) {
            scenario.attach(attachFailed(initialization.driver), "image/png", "Page screenshot");
        }
        initialization.driver.quit();
    }

    @Attachment(type = "image/png")
    private byte[] attachFailed(WebDriver driver) {
        return ((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES);
    }
}
