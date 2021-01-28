package test.framework.configuration;


import org.openqa.selenium.Platform;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.DesiredCapabilities;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Base64;

public class RemoteDriver {
    private WebDriver driver;

    private WebDriver createDriver(String browser) {
        DesiredCapabilities capabilities = new DesiredCapabilities(browser, "any", Platform.LINUX);
        try {
            return driver = new org.openqa.selenium.remote.RemoteWebDriver(new URL("http://" + System.getProperty("gridURL") + "/wd/hub"), capabilities);
        } catch (MalformedURLException e) {
            throw new IllegalArgumentException(e.getMessage());
        }
    }

    public WebDriver getDriver() {

        if (driver == null) {
            String browser = System.getProperty("browser");
            if (browser.equals("chrome") || browser.equals("firefox")) {
                this.createDriver(browser);
            } else {
                throw new IllegalArgumentException("No browser with name" + System.getProperty("browser") + "found");
            }
        }
        return driver;
    }
}
