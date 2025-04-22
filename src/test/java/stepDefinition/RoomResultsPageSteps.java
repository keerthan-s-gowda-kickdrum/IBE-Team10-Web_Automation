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
import pageFactoryClasses.LandingPage;
import pageFactoryClasses.RoomResultsPage;
import utilities.ConfigReader;
import utilities.DriverManager;

public class RoomResultsPageSteps {

     private static final Logger logger = LogManager.getLogger(LandingPageSteps.class);
    private WebDriver driver;
    private LandingPage landingPage;
    private RoomResultsPage roomResultsPage;
    private WebDriverWait wait;

    public RoomResultsPageSteps() {
        this.driver = DriverManager.getDriver(ConfigReader.getProperty("browser"));
        this.landingPage = new LandingPage(driver);
        this.roomResultsPage = new RoomResultsPage(driver);
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }
    @Given("the user launches the browser and navigates to the Room Results page")
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
        Hooks.getTest().log(Status.INFO, "Navigated to the Room Results page");
    }

    @Then("the stepper should be displayed")
    public void verifyStepperDisplayed() {
       boolean isDisplayed = roomResultsPage.isStepperDisplayed();
       assertTrue("Stepper is not displayed", isDisplayed);
       logger.info("Stepper is visible");
       Hooks.getTest().log(Status.PASS, "Stepper is displayed");
    }

    @Then("the \"Choose Room\" step should be highlighted as current")
    public void verifyChooseRoomStepHighlighted() {
       boolean isHighlighted = roomResultsPage.isChooseRoomStepHighlighted();
       assertTrue("\"Choose Room\" step is not highlighted as current", isHighlighted);
       logger.info("\"Choose Room\" step is correctly highlighted");
       Hooks.getTest().log(Status.PASS, "\"Choose Room\" step is highlighted as current");
    }



    @And("the user clicks on the \"Amenities\" filter dropdown")
public void theUserClicksOnTheAmenitiesFilterDropdown() {
    try {
        // Call the method to click the Amenities filter dropdown
        roomResultsPage.clickAmenitiesFilterDropdown();
        logger.info("User clicked on the \"Amenities\" filter dropdown");
        Hooks.getTest().log(Status.INFO, "User clicked on the \"Amenities\" filter dropdown");
    } catch (Exception e) {
        logger.error("Failed to click on the \"Amenities\" filter dropdown. Exception: " + e.getMessage());
        Hooks.getTest().log(Status.FAIL, "Failed to click on the \"Amenities\" filter dropdown. Exception: " + e.getMessage());
        throw e;
    }
}

@And("the user selects the second filter option")
public void theUserSelectsTheFirstFilterOption() {
    try {
        roomResultsPage.clickAmenitiesFilter();
        String selectedAmenity = roomResultsPage.getFilterText();
        logger.info("Retrieved selected amenity text: " + selectedAmenity);
        logger.info("User selected the second filter option in the \"Amenities\" filter");
        Hooks.getTest().log(Status.INFO, "User selected the first filter option in the \"Amenities\" filter");
    } catch (Exception e) {
        logger.error("Failed to select the second filter option. Exception: " + e.getMessage());
        Hooks.getTest().log(Status.FAIL, "Failed to select the first filter option. Exception: " + e.getMessage());
        throw e;
    }
}

@And("the user clicks on the \"Select Room\" button of the displayed room")
public void theUserClicksOnTheSelectRoomButtonOfTheDisplayedRoom() {
    try {
        roomResultsPage.clickSelectRoomButton();
        logger.info("User clicked on the 'Select Room' button.");
        Hooks.getTest().log(Status.INFO, "User clicked on the 'Select Room' button");
    } catch (Exception e) {
        logger.error("Failed to click on 'Select Room' button. Exception: " + e.getMessage());
        Hooks.getTest().log(Status.FAIL, "Failed to click on 'Select Room' button: " + e.getMessage());
    }
}

@Then("the room modal should open")
public void theRoomModalShouldOpen() {
    try {
        boolean isModalDisplayed = roomResultsPage.isRoomModalDisplayed();
        assertTrue("Room modal is not displayed", isModalDisplayed);
        logger.info("Room modal is displayed successfully.");
        Hooks.getTest().log(Status.PASS, "Room modal is displayed successfully.");
    } catch (Exception e) {
        logger.error("Room modal failed to open. Exception: " + e.getMessage());
        Hooks.getTest().log(Status.FAIL, "Room modal failed to open: " + e.getMessage());
    }
}

@When("the user increases the number of adults by one")
public void theUserIncreasesTheNumberOfAdultsByOne() {
    roomResultsPage.clickGuestDropDown();
    roomResultsPage.clickAdultCountIncrement();

    logger.info("Successfully incremented adult count." );
    Hooks.getTest().log(Status.PASS, "Adult count incremented successfully. Current value");
}

@Then("the modal should display the selected amenities filter")
public void theModalShouldDisplayTheSelectedAmenitiesFilter() {
    try {
        boolean isFilterDisplayed = roomResultsPage.isSelectedAmenityFilterDisplayed();
        assertTrue("The selected amenities filter is not displayed in the modal", isFilterDisplayed);
        logger.info("The selected amenities filter is displayed in the modal.");
        Hooks.getTest().log(Status.PASS, "The selected amenities filter is displayed in the modal.");
    } catch (Exception e) {
        logger.error("The selected amenities filter is not displayed in the modal. Exception: " + e.getMessage());
        Hooks.getTest().log(Status.FAIL, "The selected amenities filter is not displayed in the modal: " + e.getMessage());
    }
}

@When("the user clicks on the Sort By dropdown")
public void theUserClicksOnTheSortByDropdown() {
    roomResultsPage.clickSortDropDown();
    logger.info("User clicked on the 'Sort By' dropdown.");
    Hooks.getTest().log(Status.INFO, "User clicked on the 'Sort By' dropdown");
}


@And("the user selects Price Low to High from the dropdown")
public void theUserSelectsPriceLowToHighFromDropdown() {
    roomResultsPage.clickSortByPriceLowToHighOption();
    logger.info("User selected 'Price (Low to High)' from the dropdown.");
    Hooks.getTest().log(Status.INFO, "User selected 'Price (Low to High)' from the dropdown");
}

@Then("the cost of the room cards displayed after the sort should be sorted in ascending order")
public void verifyPricesAreSortedInAscendingOrder() {
    boolean arePricesSorted = roomResultsPage.arePricesSortedInAscendingOrder();
    assertTrue("The prices are not sorted in ascending order", arePricesSorted);
    logger.info("Verified that room prices are sorted in ascending order.");
    Hooks.getTest().log(Status.PASS, "Verified room prices are sorted in ascending order.");
}

@Then("each of the room cards displayed should show the following details:")
public void verifyRoomCardDetails(io.cucumber.datatable.DataTable dataTable) {
    List<String> expectedFields = dataTable.asList();

    if (expectedFields.contains("Room Title")) {
        assertTrue("Room titles are not displayed", roomResultsPage.areRoomTitlesDisplayed());
        logger.info("Room titles are displayed.");
        Hooks.getTest().log(Status.PASS, "Room titles are displayed.");
    }
    if (expectedFields.contains("Reviews")) {
        assertTrue("Room reviews are not displayed", roomResultsPage.areReviewsDisplayed());
        logger.info("Room reviews are displayed.");
        Hooks.getTest().log(Status.PASS, "Room reviews are displayed.");
    }
    if (expectedFields.contains("Ratings")) {
        assertTrue("Room ratings are not displayed", roomResultsPage.areRatingsDisplayed());
        logger.info("Room ratings are displayed.");
        Hooks.getTest().log(Status.PASS, "Room ratings are displayed.");
    }
    if (expectedFields.contains("Location")) {
        assertTrue("Room locations are not displayed", roomResultsPage.areLocationsDisplayed());
        logger.info("Room locations are displayed.");
        Hooks.getTest().log(Status.PASS, "Room locations are displayed.");
    }
    if (expectedFields.contains("Details")) {
        assertTrue("Room details (sq ft, guests, beds) are not displayed", roomResultsPage.areRoomDetailsDisplayed());
        logger.info("Room details (sq ft, guests, beds) are displayed.");
        Hooks.getTest().log(Status.PASS, "Room details (sq ft, guests, beds) are displayed.");
    }
    if (expectedFields.contains("Special Deal Label")) {
        assertTrue("Special deal labels are not displayed", roomResultsPage.areSpecialDealLabelsDisplayed());
        logger.info("Special deal labels are displayed.");
        Hooks.getTest().log(Status.PASS, "Special deal labels are displayed.");
    }
    if (expectedFields.contains("Price")) {
        assertTrue("Room prices are not displayed", roomResultsPage.arePricesDisplayed());
        logger.info("Room prices are displayed.");
        Hooks.getTest().log(Status.PASS, "Room prices are displayed.");
    }
    if (expectedFields.contains("Select Room Button")) {
        assertTrue("Select Room button is not displayed", roomResultsPage.isSelectRoomButtonDisplayed());
        logger.info("Select Room button is displayed.");
        Hooks.getTest().log(Status.PASS, "Select Room button is displayed.");
    }
}


@Given("the user launches the browser and navigates to the Room Modal page")
    public void theUserLaunchesTheBrowserAndNavigatesToTheRoomModalPageURL() {
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
        logger.info("User is redirected to the Room Results Page");
        roomResultsPage.clickGuestDropDown();
        roomResultsPage.clickAdultCountIncrement();
        logger.info("Successfully incremented adult count." );
        roomResultsPage.clickSelectRoomButton();
        logger.info("User clicked on the 'Select Room' button.");
        boolean isModalDisplayed = roomResultsPage.isRoomModalDisplayed();
        assertTrue("Room modal is not displayed", isModalDisplayed);
        logger.info("Room modal is displayed successfully.");
        Hooks.getTest().log(Status.INFO, "Navigated to the Room Modal page");
    }

    @When("the user enters a valid promo code in the promo code text field and clicks on Apply")
public void enterPromoCodeAndClickApply() {
    try {
        String promoCode = ConfigReader.getProperty("validPromoCode"); // Make sure "validPromoCode" is set to NEWUSER in your config
        roomResultsPage.enterPromoCode(promoCode);
        roomResultsPage.clickApplyPromoCodeButton();
        logger.info("Promo code entered and APPLY button clicked successfully.");
        Hooks.getTest().log(Status.PASS, "Promo code entered and APPLY button clicked successfully.");
    } catch (Exception e) {
        logger.error("Failed during promo code entry or apply: " + e.getMessage());
        Hooks.getTest().log(Status.FAIL, "Failed during promo code entry or apply: " + e.getMessage());
    }
}


@Then("a success message should be displayed")
public void verifySuccessMessageDisplayed() {
    try {
        String expectedSuccessText = ConfigReader.getProperty("validPromoSuccessMessage");
        String actualSuccessText = roomResultsPage.getMessageText();

        assertTrue("Success message is not displayed or text does not match",
            actualSuccessText != null && actualSuccessText.equals(expectedSuccessText));

        logger.info("Success message is displayed with text: " + actualSuccessText);
        Hooks.getTest().log(Status.PASS, "Success message displayed: " + actualSuccessText);
    } catch (Exception e) {
        logger.error("Error while verifying success message: " + e.getMessage());
        Hooks.getTest().log(Status.FAIL, "Exception during success message verification: " + e.getMessage());
    }
}

@And("a new deal card with the title should be displayed in the modal")
public void verifyPromoDealCardIsDisplayed() {
    String expectedTitle = ConfigReader.getProperty("validPromoCode");
    List<WebElement> dealCards = roomResultsPage.getPromoDealCardTitles();

    boolean isPromoDisplayed = dealCards.stream()
        .anyMatch(card -> card.getText().trim().equalsIgnoreCase(expectedTitle.trim()));

    assertTrue("Promo deal card with the expected title '" + expectedTitle + "' is not displayed", isPromoDisplayed);

    logger.info("Promo deal card with title '" + expectedTitle + "' is displayed in the modal");
    Hooks.getTest().log(Status.PASS, "Promo deal card with title '" + expectedTitle + "' is displayed successfully");
}

@When("the user enters an invalid promo code in the promo code text field and clicks on Apply")
public void enterInvalidPromoCodeAndClickApply() {
    try {
        String promoCode = ConfigReader.getProperty("invalidPromoCode"); 
        roomResultsPage.enterPromoCode(promoCode);
        roomResultsPage.clickApplyPromoCodeButton();
        logger.info("Promo code entered and APPLY button clicked successfully.");
        Hooks.getTest().log(Status.PASS, "Promo code entered and APPLY button clicked successfully.");
    } catch (Exception e) {
        logger.error("Failed during promo code entry or apply: " + e.getMessage());
        Hooks.getTest().log(Status.FAIL, "Failed during promo code entry or apply: " + e.getMessage());
    }
}

@Then("an error message should be displayed")
public void verifyErrorMessageDisplayed() {
    try {
        String expectedErrorText = ConfigReader.getProperty("invalidPromoErrorMessage");
        String actualErrorText = roomResultsPage.getMessageText();

        assertTrue("Error message is not displayed or text does not match",
            actualErrorText != null && actualErrorText.equals(expectedErrorText));

        logger.info("Error message is displayed with text: " + actualErrorText);
        Hooks.getTest().log(Status.PASS, "Error message displayed: " + actualErrorText);
    } catch (Exception e) {
        logger.error("Error while verifying error message: " + e.getMessage());
        Hooks.getTest().log(Status.FAIL, "Exception during error message verification: " + e.getMessage());
    }
}

@When("the user clicks on the close \\(X) button")
public void userClicksOnCloseButton() {
    try {
        roomResultsPage.clickRoomModalCloseButton();
        logger.info("User clicked on the close (X) button on the room modal.");
        Hooks.getTest().log(Status.PASS, "User clicked on the close (X) button.");
    } catch (Exception e) {
        logger.error("Failed to click on the close (X) button. Exception: " + e.getMessage());
        Hooks.getTest().log(Status.FAIL, "Failed to click on the close (X) button. Exception: " + e.getMessage());
    }
}

@When("the user clicks on the Select Package button of the deal with the least price")
public void userClicksSelectPackageOfLeastPriceDeal() {
    try {
        roomResultsPage.clickSelectPackageWithLeastPrice();
        logger.info("User clicked on the Select Package button for the deal with the least price");
        Hooks.getTest().log(Status.PASS, "Clicked on Select Package button of the least price deal");
    } catch (Exception e) {
        logger.error("Failed to click on Select Package button for least price deal: " + e.getMessage());
        Hooks.getTest().log(Status.FAIL, "Error clicking on least price deal: " + e.getMessage());
    }
}
@Then("the user should be redirected to the checkout page")
public void userShouldBeRedirectedToCheckOutPage() {
    try {
        boolean isCheckoutPageDisplayed = roomResultsPage.isCheckoutPageDisplayed();
        assertTrue("User is not redirected to the Checkout Page", isCheckoutPageDisplayed);
        logger.info("User is redirected to the Checkout Page");
        Hooks.getTest().log(Status.PASS, "User is redirected to the Checkout Page");
    } catch (Exception e) {
        logger.error("Error while verifying redirection to checkout page: " + e.getMessage());
        Hooks.getTest().log(Status.FAIL, "Error during redirection verification: " + e.getMessage());
    }
}
}





