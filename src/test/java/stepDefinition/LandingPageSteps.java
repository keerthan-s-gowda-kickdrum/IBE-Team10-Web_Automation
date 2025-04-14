package stepDefinition;

import com.aventstack.extentreports.Status;
import hooks.Hooks;
import io.cucumber.java.en.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import static org.junit.Assert.*;
import pageFactoryClasses.LandingPage;
import utilities.ConfigReader;
import utilities.DriverManager;

import java.time.Duration;

public class LandingPageSteps {

    private static final Logger logger = LogManager.getLogger(LandingPageSteps.class);
    private WebDriver driver;
    private LandingPage landingPage;
    private WebDriverWait wait;

    public LandingPageSteps() {
        this.driver = DriverManager.getDriver(ConfigReader.getProperty("browser"));
        this.landingPage = new LandingPage(driver);
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    @Given("the user launches the browser")
    public void theUserLaunchesTheBrowser() {
        logger.info("Browser launched successfully");
        Hooks.getTest().log(Status.INFO, "Browser launched successfully");
    }

    @And("navigates to the landing page URL {string}")
    public void navigatesToTheLandingPageURL(String url) {
        driver.get(url);
        logger.info("Navigated to URL: {}", url);
        Hooks.getTest().log(Status.INFO, "Navigated to URL: " + url);

        // Log page source for debugging
        logger.debug("Page source: {}", driver.getPageSource());
    }

    @When("the landing page is loaded")
    public void theLandingPageIsLoaded() {
        try {
            Thread.sleep(2000); // Allow time for page load
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

        logger.info("Current URL: {}", driver.getCurrentUrl());
        logger.info("Page Title: {}", driver.getTitle());
        Hooks.getTest().log(Status.INFO, "Current URL: " + driver.getCurrentUrl());
        Hooks.getTest().log(Status.INFO, "Page Title: " + driver.getTitle());

        wait.until(ExpectedConditions.visibilityOf(landingPage.getBannerImage()));
        logger.info("Landing page loaded successfully");
        Hooks.getTest().log(Status.PASS, "Landing page loaded successfully");
    }

    @Then("the banner image should be displayed")
    public void theBannerImageShouldBeDisplayed() {
        assertTrue("Banner image is not displayed", landingPage.getBannerImage().isDisplayed());
        logger.info("Banner image verified");
        Hooks.getTest().log(Status.PASS, "Banner image is displayed");
    }

    @And("the user selects a property from the {string} dropdown")
    public void userSelectsProperty(String dropdownName) {
        landingPage.selectProperty("Team 19 Hotel");
        logger.info("Selected property: Team 19 Hotel");
        Hooks.getTest().log(Status.PASS, "User selected property: Team 19 Hotel from dropdown");
    }
}
