package stepDefinition;

import com.aventstack.extentreports.Status;
import hooks.Hooks;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import utilities.ConfigReader;
import utilities.DriverManager;

public class LandingPageSteps {

    private static final Logger logger = LogManager.getLogger(LandingPageSteps.class);
    private WebDriver driver;
    private final String url = ConfigReader.getProperty("baseURL");
    private final String expectedTitle = ConfigReader.getProperty("pageTitle");

    public LandingPageSteps() {
        this.driver = DriverManager.getDriver(ConfigReader.getProperty("browser"));
    }

    @When("the user enters the application URL in the browser")
    public void the_user_enters_the_application_URL_in_the_browser() {
        driver.get(url);
        logger.info("Navigating to URL: {}", url);
        Hooks.getTest().log(Status.INFO, "Navigated to " + url);
    }

    @Then("the landing page should be displayed")
    public void the_landing_page_should_be_displayed() {
        String title = driver.getTitle();
        logger.info("Fetched page title: {}", title);

        Assert.assertEquals(title, expectedTitle, "Landing page title does not match");
        logger.info("Landing page title validated successfully");
        Hooks.getTest().log(Status.PASS, "Landing page title validated successfully");
    }
}
