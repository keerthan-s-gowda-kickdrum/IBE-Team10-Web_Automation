package stepDefinition;

import com.aventstack.extentreports.Status;
import io.cucumber.java.en.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;
import pageFactoryClasses.HeaderFooterPage;
import utilities.DriverManager;
import utilities.ConfigReader;
import hooks.Hooks;
import static org.junit.Assert.*;

public class HeaderFooterSteps {
    private static final Logger logger = LogManager.getLogger(HeaderFooterSteps.class);
    private WebDriver driver;
    private HeaderFooterPage headerFooterPage;

    public HeaderFooterSteps() {
        this.driver = DriverManager.getDriver(ConfigReader.getProperty("browser"));
        this.headerFooterPage = new HeaderFooterPage(driver);
    }

    @Given("the user is on the landing page")
    public void theUserIsOnTheLandingPage() {
        driver.get(ConfigReader.getProperty("baseURL") + "/#/");
        logger.info("Navigated to landing page");
        Hooks.getTest().log(Status.INFO, "Navigated to landing page URL");
    }

    @Then("the main header should be visible")
    public void theMainHeaderShouldBeVisible() {
        assertTrue("Main header is not visible", headerFooterPage.isHeaderVisible());
        logger.info("Main header visibility verified");
        Hooks.getTest().log(Status.PASS, "Main header is visible on the page");
    }

    @And("the logo should be displayed")
    public void theLogoShouldBeDisplayed() {
        assertTrue("Logo is not displayed", headerFooterPage.isLogoDisplayed());
        logger.info("Logo display verified");
        Hooks.getTest().log(Status.PASS, "Company logo is displayed in header");
    }

    @And("the booking engine name should be displayed")
    public void theBookingEngineNameShouldBeDisplayed() {
        assertTrue("Booking engine name is not displayed", headerFooterPage.isBookingEngineNameDisplayed());
        logger.info("Booking engine name display verified");
        Hooks.getTest().log(Status.PASS, "Booking engine name is displayed correctly");
    }

    @And("the bookings link should be present")
    public void theBookingsLinkShouldBePresent() {
        assertTrue("Bookings link is not present", headerFooterPage.isBookingsLinkPresent());
        logger.info("Bookings link presence verified");
        Hooks.getTest().log(Status.PASS, "Bookings link is present in navigation");
    }

    @And("the language dropdown should be available")
    public void theLanguageDropdownShouldBeAvailable() {
        assertTrue("Language dropdown is not available", headerFooterPage.isLanguageDropdownAvailable());
        logger.info("Language dropdown availability verified");
        Hooks.getTest().log(Status.PASS, "Language selection dropdown is available");
    }

    @And("the currency dropdown should be available")
    public void theCurrencyDropdownShouldBeAvailable() {
        assertTrue("Currency dropdown is not available", headerFooterPage.isCurrencyDropdownAvailable());
        logger.info("Currency dropdown availability verified");
        Hooks.getTest().log(Status.PASS, "Currency selection dropdown is available");
    }

    @And("the login button should be visible")
    public void theLoginButtonShouldBeVisible() {
        assertTrue("Login button is not visible", headerFooterPage.isLoginButtonVisible());
        logger.info("Login button visibility verified");
        Hooks.getTest().log(Status.PASS, "Login button is visible in header");
    }

    @Then("the footer should be visible")
    public void theFooterShouldBeVisible() {
        assertTrue("Footer is not visible", headerFooterPage.isFooterVisible());
        logger.info("Footer visibility verified");
        Hooks.getTest().log(Status.PASS, "Footer is visible on the page");
    }

    @And("the company logo should be displayed in the footer")
    public void theCompanyLogoShouldBeDisplayedInTheFooter() {
        assertTrue("Footer logo is not displayed", headerFooterPage.isFooterLogoDisplayed());
        logger.info("Footer logo display verified");
        Hooks.getTest().log(Status.PASS, "Company logo is displayed in footer");
    }

    @And("the footer should display the copyright information")
    public void theFooterShouldDisplayCopyrightInformation() {
        assertTrue("Copyright information is not displayed", headerFooterPage.isCopyrightDisplayed());
        String copyrightText = headerFooterPage.getCopyrightText();
        assertFalse("Copyright text should not be empty", copyrightText.isEmpty());
        logger.info("Copyright information verified");
        Hooks.getTest().log(Status.PASS, "Copyright information is displayed in footer");
    }
}