package test.framework.utility;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

public class WebElementActions {


    public static void clickOnButtonByText(WebDriver driver, String name, String tag) {
        WebElement element = searchElementByText(driver, name, tag);
        waitForElementAndClick(driver, element);
    }

    public static WebElement searchElementByText(WebDriver driver, String text, String tag){
        return driver.findElement(By.xpath("//" + tag + "[normalize-space(text()[contains(translate(.,'ABCDEFGHIJKLMNOPQRSTUVWXYZ','abcdefghijklmnopqrstuvwxyz'),\"" + text.toLowerCase() + "\")])]"));
    }

    public static WebElement isElementDisplayed(WebDriver driver, By locator) {
        WebDriverWait wait = new WebDriverWait(driver, 10);
        try{
            return wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
        }catch (Exception e){
            Assert.fail("TIMEOUT: No element found");
        }
        return null;
    }

    public static void waitForElementAndClick(WebDriver driver, WebElement element) {
        WebDriverWait wait = new WebDriverWait(driver, 10);
        try {
            WebElement elementToClick = wait.until(ExpectedConditions.elementToBeClickable(element));
            elementToClick.click();
        } catch (Exception e) {
            clickByJavaScript(driver, element);
        }
    }

    public static void clickByJavaScript(WebDriver driver, WebElement element) {
        JavascriptExecutor ex = (JavascriptExecutor) driver;
        ex.executeScript("arguments[0].click()", element);
    }
}
