package test.framework;

import test.framework.configuration.RemoteDriver;
import io.cucumber.guice.ScenarioScoped;
import org.openqa.selenium.Alert;
import org.openqa.selenium.WebDriver;

import java.time.LocalDate;

@ScenarioScoped
public class Initialization {
    public WebDriver driver = new RemoteDriver().getDriver();

    public Alert alert;
}
