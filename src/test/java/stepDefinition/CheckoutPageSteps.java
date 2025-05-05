package stepDefinition;

import java.time.Duration;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import static org.junit.Assert.assertTrue;

import com.aventstack.extentreports.Status;

import hooks.Hooks;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import pageFactoryClasses.CheckoutPage;
import pageFactoryClasses.LandingPage;
import pageFactoryClasses.RoomResultsPage;
import utilities.ConfigReader;
import utilities.DriverManager;

public class CheckoutPageSteps {

    private static final Logger logger = LogManager.getLogger(CheckoutPageSteps.class);
    private WebDriver driver;
    private LandingPage landingPage;
    private RoomResultsPage roomResultsPage;
    private CheckoutPage checkoutPage;
    private WebDriverWait wait;

    public CheckoutPageSteps() {
        this.driver = DriverManager.getDriver(ConfigReader.getProperty("browser"));
        this.landingPage = new LandingPage(driver);
        this.roomResultsPage = new RoomResultsPage(driver);
        this.checkoutPage = new CheckoutPage(driver);
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }
    @Given("the user launches the browser and navigates to the Checkout page")
    public void theUserLaunchesTheBrowserAndNavigatesToTheRoomResultsPageURL() {
        driver.get(ConfigReader.getProperty("baseURL"));
        wait.until(ExpectedConditions.visibilityOf(landingPage.getBannerImage()));
        logger.info("Landing page loaded successfully");
        landingPage.selectProperty(ConfigReader.getProperty("propertyName"));
        logger.info("Selected property");
        landingPage.clickCheckInCheckOutField();
        logger.info("Clicked on check-in/check-out field");
        landingPage.selectCheckInDateFromConfig();
        logger.info("Selected check-in date from config file");
        landingPage.selectCheckOutDateAfterCheckIn();
        String range = ConfigReader.getProperty("rangeOfStay");
        logger.info("Selected check-out date " + range + " days after check-in");
        landingPage.clickApplyDatesButton();
        logger.info("Clicked the Apply Dates button");
        landingPage.clickSearchButton(); 
        logger.info("User clicked the Search button");
        boolean isRoomResultsPageDisplayed = roomResultsPage.isRoomResultsPageDisplayed();
        assertTrue("User is not redirected to the Room Results Page", isRoomResultsPageDisplayed);
        logger.info("User is redirected to the Room Results Page");
        //
        roomResultsPage.clickGuestDropDown();
        roomResultsPage.clickAdultCountIncrement();
        logger.info("Successfully incremented adult count." );
        roomResultsPage.clickSelectRoomButton();
        logger.info("User clicked on the 'Select Room' button.");
        boolean isModalDisplayed = roomResultsPage.isRoomModalDisplayed();
        assertTrue("Room modal is not displayed", isModalDisplayed);
        logger.info("Room modal is displayed successfully.");
        roomResultsPage.clickSelectPackageWithLeastPrice();
        logger.info("User clicked on the Select Package button for the deal with the least price");
        boolean isCheckoutPageDisplayed = roomResultsPage.isCheckoutPageDisplayed();
        assertTrue("User is not redirected to the Checkout Page", isCheckoutPageDisplayed);
        logger.info("User is redirected to the Checkout Page");
        Hooks.getTest().log(Status.INFO, "User is on the Checkout Page");
    
    }

    @Then("the Checkout step label should be highlighted as current")
    public void verifyChooseRoomStepHighlighted() {
       boolean isHighlighted = checkoutPage.isCheckoutStepHighlighted();
       assertTrue("the Checkout step label is highlighted as current", isHighlighted);
       logger.info("the Checkout step label is highlighted as current");
       Hooks.getTest().log(Status.PASS, "the Checkout step label is highlighted as current");
    }

    @Then("the previous step label should be highlighted as completed")
public void thePreviousStepLabelShouldBeHighlightedAsCompleted() {
    boolean isChooseAddOnStepHighlighted = checkoutPage.isChooseAddOnStepHighlighted();
    if (isChooseAddOnStepHighlighted) {
        boolean isChooseRoomStepCompleted = checkoutPage.isChooseRoomStepHighlighted();
        assertTrue("The 'Choose Room' step label is not highlighted as completed", isChooseRoomStepCompleted);
        logger.info("The 'Choose Room' step label is highlighted as completed");
        Hooks.getTest().log(Status.PASS, "The 'Choose Room' step label is highlighted as completed");
    } else {
        logger.error("The 'Choose Add-On' step is not highlighted as current. Cannot verify the previous step completion.");
        Hooks.getTest().log(Status.FAIL, "The 'Choose Add-On' step is not highlighted as current. Cannot verify the previous step completion.");
        throw new AssertionError("The 'Choose Add-On' step is not highlighted as current. Previous step completion cannot be verified.");
    }
}

@When("the user enters {string}, {string}, {string}, and {string} in the payment info section and clicks on the NEXT: BILLING INFO button")
public void enterTravelerInfoDetails(String firstName, String lastName, String phoneNumber, String email) {
    boolean result = checkoutPage.enterTravelerInfoAndClickNextBilling(firstName, lastName, phoneNumber, email);
    assertTrue("'NEXT: BILLING INFO' button is disabled due to the presense of invalid data", result);
    logger.info("Successfully entered traveler information and clicked 'NEXT: BILLING INFO' button for user: " + firstName + " " + lastName);
    Hooks.getTest().log(Status.INFO, "Successfully entered traveler information and clicked 'NEXT: BILLING INFO' button for user: " + firstName + " " + lastName);
}

@Then("the billing info section should expand")
public void theBillingInfoSectionShouldExpand() {
    boolean isExpanded = checkoutPage.isBillingInfoSectionExpanded();
    assertTrue("The Billing Info section is not expanded", isExpanded);
    logger.info("The Billing Info section is expanded");
    Hooks.getTest().log(Status.PASS, "The Billing Info section is expanded");
}

@When("the user enters {string}, {string}, {string}, {string}, {string}, {string}, {string}, {string}, {string}, and {string} in the billing info section")
public void theUserEntersBillingInfo(
    String billingFirstName,
    String billingLastName,
    String mailingAddress1,
    String mailingAddress2,
    String country,
    String state,
    String zipcode,
    String city,
    String billingPhoneNumber,
    String billingEmail) {

    // Log the billing info data
    logger.info("Entering billing information for: ");
    logger.info("First Name: " + billingFirstName);
    logger.info("Last Name: " + billingLastName);
    logger.info("Address 1: " + mailingAddress1);
    logger.info("Address 2: " + mailingAddress2);
    logger.info("Country: " + country);
    logger.info("State: " + state);
    logger.info("Zipcode: " + zipcode);
    logger.info("City: " + city);
    logger.info("Phone Number: " + billingPhoneNumber);
    logger.info("Email: " + billingEmail);

    // Log the action being performed
    Hooks.getTest().log(Status.INFO, "Entering billing info: " +
            billingFirstName + " " + billingLastName + " (" + country + ", " + state + ")");
    
    checkoutPage.enterBillingInfo(billingFirstName, billingLastName, mailingAddress1, mailingAddress2, country, state, zipcode, city, billingPhoneNumber, billingEmail);
}

@And("clicks on the Next: Payment Info button")
public void clicksOnTheNextPaymentInfoButton() {
    checkoutPage.clickNextPaymentInfo();
    logger.info("Successfully clicked on the 'Next: Payment Info' button.");
    Hooks.getTest().log(Status.INFO, "Successfully clicked on the 'Next: Payment Info' button.");
}

@Then("the OTP Verification section should expand")
public void theOtpVerificationSectionShouldExpand() {
    boolean isModalDisplayed = checkoutPage.isOptVerificationModalDisplayed();
    if (isModalDisplayed) {
        logger.info("OTP Verification section is expanded.");
        Hooks.getTest().log(Status.PASS, "OTP Verification section is expanded.");
    } else {
        logger.error("OTP Verification section is not expanded.");
        Hooks.getTest().log(Status.FAIL, "OTP Verification section is not expanded.");
    }
}

@When("the user enters OTP in the OTP field and clicks on the VERIFY OTP button")
public void theUserEntersOtpAndClicksOnVerifyOtpButton() throws InterruptedException {
    // Log the action
    logger.info("User is entering OTP manually in the OTP field.");

    // Log the test step
    Hooks.getTest().log(Status.INFO, "User is entering OTP manually.");

    // Call the method to enter OTP manually
    checkoutPage.enterOtp();

    // Log the action
    logger.info("User has entered OTP manually.");

    // Log the test step
    Hooks.getTest().log(Status.INFO, "User has entered OTP manually.");

    // Call the method to click the Verify OTP button
    checkoutPage.clickVerifyOtp();

    // Log the action
    logger.info("User clicked the VERIFY OTP button.");

    // Log the test step
    Hooks.getTest().log(Status.INFO, "User clicked the VERIFY OTP button.");
}

@Then("the payment info section should expand")
public void thePaymentInfoSectionShouldExpand() {


    // Assertion to verify Payment Info section is displayed
    assertTrue("Payment Info section is not expanded!", checkoutPage.isPaymentInfoSectionDisplayed());

    // Log success
    logger.info("Payment Info section expanded successfully.");
    Hooks.getTest().log(Status.PASS, "Payment Info section expanded successfully.");
}

@When("the user enters valid payment details like card number {string}, expiry month {string}, expiry year {string}, and CVV {string}")
public void theUserEntersValidPaymentDetails(String cardNumber, String expiryMonth, String expiryYear, String cvv) {
    try {
        checkoutPage.enterPaymentDetails(cardNumber, expiryMonth, expiryYear, cvv);
        logger.info("User has entered payment details successfully.");
        Hooks.getTest().log(Status.PASS, "User has entered payment details successfully.");
    } catch (Exception e) {
        logger.error("Failed to enter payment details. Exception: " + e.getMessage());
        Hooks.getTest().log(Status.FAIL, "Failed to enter payment details. Exception: " + e.getMessage());
        throw e;
    }
}

@And("selects the Terms and Conditions checkbox")
public void selectsTheTermsAndConditionsCheckbox() {
    try {
        checkoutPage.selectTermsAndConditionsCheckbox();
        logger.info("User has selected the Terms and Conditions checkbox.");
        Hooks.getTest().log(Status.PASS, "User has selected the Terms and Conditions checkbox.");
    } catch (Exception e) {
        logger.error("Failed to select Terms and Conditions checkbox. Exception: " + e.getMessage());
        Hooks.getTest().log(Status.FAIL, "Failed to select Terms and Conditions checkbox. Exception: " + e.getMessage());
        throw e;
    }
}

@And("clicks on the Purchase button")
public void clicksOnThePurchaseButton() {
    try {
        checkoutPage.clickPurchaseButton();
        logger.info("User clicked on the Purchase button.");
        Hooks.getTest().log(Status.PASS, "User clicked on the Purchase button.");
    } catch (Exception e) {
        logger.error("Failed to click on the Purchase button. Exception: " + e.getMessage());
        Hooks.getTest().log(Status.FAIL, "Failed to click on the Purchase button. Exception: " + e.getMessage());
        throw e;
    }
}

@Then("the user should be redirected to the Booking Confirmation page")
public void theUserShouldBeRedirectedToTheBookingConfirmationPage() {
    try {
        boolean isConfirmationDisplayed = checkoutPage.isBookingConfirmationDisplayed();
        assertTrue("Booking Confirmation page was not displayed.",isConfirmationDisplayed);
        logger.info("User successfully redirected to the Booking Confirmation page.");
        Hooks.getTest().log(Status.PASS, "User successfully redirected to the Booking Confirmation page.");
    } catch (AssertionError e) {
        logger.error("Booking Confirmation page validation failed. AssertionError: " + e.getMessage());
        Hooks.getTest().log(Status.FAIL, "Booking Confirmation page validation failed. " + e.getMessage());
        throw e;
    } catch (Exception e) {
        logger.error("An unexpected error occurred during Booking Confirmation page validation. Exception: " + e.getMessage());
        Hooks.getTest().log(Status.FAIL, "Unexpected error during Booking Confirmation page validation. " + e.getMessage());
        throw e;
    }
}

@When("the user clicks on the Continue Shopping button")
public void theUserClicksOnTheContinueShoppingButton() {
    logger.info("User is clicking on the Continue Shopping button.");
    Hooks.getTest().log(Status.INFO, "User clicks on the Continue Shopping button.");
    
    checkoutPage.clickContinueShoppingButton();
}

@Then("the user should be redirected to the Room Results page")
public void theUserShouldBeRedirectedToTheRoomResultsPage() {
    try {
        String currentUrl = driver.getCurrentUrl();
        logger.info("Current URL: " + currentUrl);
        Hooks.getTest().log(Status.INFO, "Current URL after clicking Continue Shopping: " + currentUrl);

        assertTrue( "The URL does not contain 'results-page'. User is not on Room Results page.",currentUrl.contains("results-page"));
        logger.info("User successfully redirected to the Room Results page.");
        Hooks.getTest().log(Status.PASS, "User successfully redirected to the Room Results page.");
    } catch (AssertionError e) {
        logger.error("User is not redirected to the Room Results page. Assertion failed: " + e.getMessage());
        Hooks.getTest().log(Status.FAIL, "User is not redirected to the Room Results page. " + e.getMessage());
        throw e;
    }
}

@Then("the Trip Itinerary panel should be displayed on the Room Results page")
public void theTripItineraryPanelShouldBeDisplayedOnTheRoomResultsPage() {
    try {
        assertTrue("Trip Itinerary panel is not displayed on the Room Results page.",checkoutPage.isTripItineraryPanelDisplayed());
        logger.info("Trip Itinerary panel is displayed on the Room Results page.");
        Hooks.getTest().log(Status.PASS, "Trip Itinerary panel is displayed on the Room Results page.");
    } catch (AssertionError e) {
        logger.error("Trip Itinerary panel is NOT displayed. Assertion failed: " + e.getMessage());
        Hooks.getTest().log(Status.FAIL, "Trip Itinerary panel is NOT displayed. " + e.getMessage());
        throw e;
    }
}

@When("the user clicks on the Remove link")
public void the_user_clicks_on_the_remove_link() {
    try {
        checkoutPage.clickRemoveLink();
        logger.info("User successfully clicked the Remove link.");
        Hooks.getTest().log(Status.INFO, "User successfully clicked the Remove link.");
    } catch (Exception e) {
        logger.error("Failed to click on the Remove link. Exception: " + e.getMessage());
        Hooks.getTest().log(Status.FAIL, "Failed to click on the Remove link. Exception: " + e.getMessage());
        throw e;
    }
}

@Then("the user should be redirected to the Landing page")
public void the_user_should_be_redirected_to_the_landing_page() {

    
        boolean isSearchFormVisible = landingPage.isSearchFormDisplayed();
        if (isSearchFormVisible) {
            logger.info("User is successfully redirected to the Landing page.");
            Hooks.getTest().log(Status.PASS, "User is successfully redirected to the Landing page.");
        } else {
            logger.error("User is not redirected to the Landing page.");
            Hooks.getTest().log(Status.FAIL, "User is not redirected to the Landing page.");
            throw new AssertionError("Landing page is not displayed.");
        }
    }
}












