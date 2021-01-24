package stepdefs;

import test.framework.Initialization;
import test.framework.utility.WebElementActions;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.testng.Assert;

import javax.inject.Inject;
import java.util.Objects;

public class AdditionalTaskSteps {

    @Inject
    Initialization initialization;

    @Step
    @Given("I open page {string}")
    public void iOpenPage(String url) {
        initialization.driver.get(System.getProperty("baseURL") + url);
    }


    @Step
    @When("I click button with text - {string}")
    public void iClickButtonWithText(String buttonText) {
        WebElementActions.clickOnButtonByText(initialization.driver, buttonText, "button");
    }

    @Step
    @Then("I wait for result with text - {string}")
    public void iWaitForResultWithText(String text) {
        Assert.assertEquals(Objects.requireNonNull(WebElementActions.isElementDisplayed(initialization.driver, By.id("finish"))).getText(), text);
    }
}
