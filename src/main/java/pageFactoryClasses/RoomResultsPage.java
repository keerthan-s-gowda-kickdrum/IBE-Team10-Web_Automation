package pageFactoryClasses;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class RoomResultsPage {

    private static final org.apache.logging.log4j.Logger logger = org.apache.logging.log4j.LogManager.getLogger(LandingPage.class);

    @FindBy(xpath = "//div[contains(@class, '_container')]")
    private WebElement roomResultsPageContainer;

    @FindBy(xpath = "//div[contains(@class, '_steps__content')]")
    private WebElement stepperContainer;

    @FindBy(xpath = "//div[contains(@class, '_steps__circle') and contains(@class, '_steps__circle--completed')]")
    private WebElement chooseRoomStepper;

    @FindBy(xpath = "//span[normalize-space()='Amenities']")
    private WebElement amenitiesFilterDropdown;

    @FindBy(xpath = "(//label[contains(@class, '_checkbox__label')])[2]")
    private WebElement amenitiesFilter;

    @FindBy(xpath = "//div[contains(@class, '_room-modal__content')]")
    private WebElement roomModal;

    @FindBy(xpath = "//button[contains(@class, '_dropdown__select_')]")
    private WebElement guestDropDown;

    @FindBy(xpath = "//button[@aria-label='Increase adults count']")
    private WebElement adultCountIncrement;

    @FindBy(xpath = "//div[contains(@class, '_room-modal__amenity-grid')]//span[contains(@class, '_amenity-item__text')]")
    private List<WebElement> amenitiesList;

    @FindBy(xpath = "//span[normalize-space()='Sort By: No Sort']")
    private WebElement sortDropDown;

    @FindBy(xpath = "//button[normalize-space()='Price (Low to High)']")
    private WebElement sortByPriceLowToHighOption;


    @FindBy(xpath = "//div[@class='_content_pxpru_62']")
    private List<WebElement> roomCards;

    @FindBy(xpath = "//h3[contains(@class, '_title')]")
    private List<WebElement> roomCardTitle;

    @FindBy(xpath = "//span[contains(@class, '_reviews')]")
    private List<WebElement> roomCardPriceReviews;

    @FindBy(xpath = "//div[@class='stars__section']")
    private List<WebElement> roomCardPriceRatings;

    @FindBy(xpath = "//div[contains(@class, '_location')]")
    private List<WebElement> roomCardPriceLocation;

    @FindBy(xpath = "//div[contains(@class, '_details')]")
    private List<WebElement> roomCardDetails;

    @FindBy(xpath = "//div[contains(@class, '_deal__heading')]")
    private List<WebElement> roomCardPriceSpecialDealLabel;

    @FindBy(xpath = "//div[contains(@class, '_room-card__price')]//span[contains(@class, '_price_')]/span[7]")
    private List<WebElement> roomCardPriceSpecialDealTitle;

    @FindBy(xpath = "//span[contains(@class, '_price_')]")
    private List<WebElement> pricesInRoomCard;

    @FindBy(xpath = "(//button[normalize-space()='SELECT ROOM'])[1]")
    private WebElement selectRoomBtn;

    @FindBy(xpath = "//button[normalize-space()='SELECT ROOM']")
    private List<WebElement> selectRoomButtons;

    @FindBy(xpath = "//input[@type='text']")
    private WebElement promocodeTextField;

    @FindBy(xpath = "//button[normalize-space()='APPLY']")
    private WebElement applyPromoCodeBtn;

    @FindBy(xpath = "//div[contains(@class, 'MuiAlert-message')]")
    private WebElement successMessageContainer;

    @FindBy(xpath = "//p[contains(@class, '_package__title')]")
    private List<WebElement> promoDealCardTitles;

    @FindBy(xpath = "//button[normalize-space()='×']")
    private List<WebElement> roomModalCloseButton;



    private WebDriver driver;
    private JavascriptExecutor js;
    private WebDriverWait wait;

    public RoomResultsPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(20));
        this.js = (JavascriptExecutor) driver;
        PageFactory.initElements(driver, this);
    }

    public boolean isRoomResultsPageDisplayed() {
        try {
            // Wait for the container to be visible
            wait.until(ExpectedConditions.visibilityOf(roomResultsPageContainer));
            return roomResultsPageContainer.isDisplayed();
        } catch (Exception e) {
            logger.error("Room results page container is not displayed. Exception: " + e.getMessage());
            return false;
        }
    }

    public boolean isStepperDisplayed() {
        try {
            wait.until(ExpectedConditions.visibilityOf(stepperContainer));
            return stepperContainer.isDisplayed();
        } catch (Exception e) {
            logger.error("Stepper container is not displayed. Exception: " + e.getMessage());
            return false;
        }
    }
    
    public boolean isChooseRoomStepHighlighted() {
        try {
            wait.until(ExpectedConditions.visibilityOf(chooseRoomStepper));
            String stepClass = chooseRoomStepper.getAttribute("class");
            // Optional: you can check for presence of specific class names if needed
            return stepClass.contains("current") || stepClass.contains("_steps__circle--current");
        } catch (Exception e) {
            logger.error("Choose Room step is not highlighted. Exception: " + e.getMessage());
            return false;
        }
    }
    public void clickAmenitiesFilterDropdown() {
        try {
            wait.until(ExpectedConditions.elementToBeClickable(amenitiesFilterDropdown)).click();
        } catch (Exception e) {
            logger.error("Failed to click on Amenities filter dropdown. Exception: " + e.getMessage());
        }
    }

    public void clickAmenitiesFilter() {
        try {
            wait.until(ExpectedConditions.elementToBeClickable(amenitiesFilter)).click();
        } catch (Exception e) {
            logger.error("Failed to click on Amenities filter. Exception: " + e.getMessage());
        }
    
    }

    public void clickSelectRoomButton() {
        try {
            wait.until(ExpectedConditions.elementToBeClickable(selectRoomBtn)).click();
        } catch (Exception e) {
            logger.error("Failed to click on Select Room button. Exception: " + e.getMessage());
        }
    }

    public boolean isRoomModalDisplayed() {
        try {
            wait.until(ExpectedConditions.visibilityOf(roomModal));
            return roomModal.isDisplayed();
        } catch (Exception e) {
            logger.error("Room modal is not displayed. Exception: " + e.getMessage());
            return false;
        }
    }
    public void clickGuestDropDown() {
        try {
            wait.until(ExpectedConditions.elementToBeClickable(guestDropDown)).click();
        } catch (Exception e) {
            logger.error("Failed to click on Guest dropdown. Exception: " + e.getMessage());
        }
    }
    public void clickAdultCountIncrement() {
        try {
            wait.until(ExpectedConditions.elementToBeClickable(adultCountIncrement)).click();
        } catch (Exception e) {
            logger.error("Failed to click on Adult count increment button. Exception: " + e.getMessage());
        }
    }

    public String getFilterText() {
        try {
            String label = wait.until(ExpectedConditions.visibilityOf(amenitiesFilter)).getText();
            logger.info("Amenities label text retrieved: " + label);
            return label;
        } catch (Exception e) {
            logger.error("Failed to retrieve text for Cable & Pay TV Channels. Exception: " + e.getMessage());
            return null;
        }
    }

    public boolean isSelectedAmenityFilterDisplayed() {
        try {
            // Get the text of the selected filter
            String selectedFilter = getFilterText();
    
            // Iterate through the list of amenities in the modal to check if the selected filter is displayed
            for (WebElement amenity : amenitiesList) {
                if (amenity.getText().equalsIgnoreCase(selectedFilter)) {
                    logger.info("Selected amenities filter: '" + selectedFilter + "' is displayed in the modal.");
                    return true;
                }
            }
            logger.error("Selected amenities filter: '" + selectedFilter + "' is not displayed in the modal.");
            return false;
        } catch (Exception e) {
            logger.error("Failed to verify selected amenities filter in modal. Exception: " + e.getMessage());
            return false;
        }
    }
    public void clickSortDropDown() {
        try {
            wait.until(ExpectedConditions.elementToBeClickable(sortDropDown)).click();
        } catch (Exception e) {
            logger.error("Failed to click on Sort dropdown. Exception: " + e.getMessage());
        }
    }
    public void clickSortByPriceLowToHighOption() {
        try {
            wait.until(ExpectedConditions.elementToBeClickable(sortByPriceLowToHighOption)).click();
        } catch (Exception e) {
            logger.error("Failed to click on Sort by Price (Low to High) option. Exception: " + e.getMessage());
        }
    
    }

    public boolean arePricesSortedInAscendingOrder() {
    try {
        // Wait for prices to load
        wait.until(ExpectedConditions.visibilityOfAllElements(pricesInRoomCard));
        
        // Extract the text of each price and convert to a list of doubles for comparison
        List<Double> prices = new ArrayList<>();
        for (WebElement priceElement : pricesInRoomCard) {
            String priceText = priceElement.getText().replace("₹", "").trim(); // Remove currency symbol and any extra spaces
            prices.add(Double.parseDouble(priceText));
        }

        // Check if the list is sorted in ascending order
        for (int i = 1; i < prices.size(); i++) {
            if (prices.get(i) < prices.get(i - 1)) {
                logger.error("Prices are not sorted in ascending order.");
                return false;
            }
        }

        logger.info("Prices are sorted in ascending order.");
        return true;
    } catch (Exception e) {
        logger.error("Failed to verify if prices are sorted. Exception: " + e.getMessage());
        return false;
    }
}

public boolean areRoomCardsDisplayed() {
    try {
        return !roomCards.isEmpty();
    } catch (Exception e) {
        logger.error("Failed to verify room cards. Exception: " + e.getMessage());
        return false;
    }
}

public boolean areRoomTitlesDisplayed() {
    try {
        return roomCardTitle.stream().allMatch(WebElement::isDisplayed);
    } catch (Exception e) {
        logger.error("Failed to verify room titles. Exception: " + e.getMessage());
        return false;
    }
}

public boolean areReviewsDisplayed() {
    try {
        return roomCardPriceReviews.stream().allMatch(WebElement::isDisplayed);
    } catch (Exception e) {
        logger.error("Failed to verify reviews. Exception: " + e.getMessage());
        return false;
    }
}

public boolean areRatingsDisplayed() {
    try {
        return roomCardPriceRatings.stream().allMatch(WebElement::isDisplayed);
    } catch (Exception e) {
        logger.error("Failed to verify ratings. Exception: " + e.getMessage());
        return false;
    }
}

public boolean areLocationsDisplayed() {
    try {
        return roomCardPriceLocation.stream().allMatch(WebElement::isDisplayed);
    } catch (Exception e) {
        logger.error("Failed to verify locations. Exception: " + e.getMessage());
        return false;
    }
}

public boolean areRoomDetailsDisplayed() {
    try {
        return roomCardDetails.stream().allMatch(WebElement::isDisplayed);
    } catch (Exception e) {
        logger.error("Failed to verify room details (sq ft, guest count, bed count). Exception: " + e.getMessage());
        return false;
    }
}

public boolean areSpecialDealLabelsDisplayed() {
    try {
        return roomCardPriceSpecialDealLabel.stream().allMatch(WebElement::isDisplayed);
    } catch (Exception e) {
        logger.error("Failed to verify special deal labels. Exception: " + e.getMessage());
        return false;
    }
}

public boolean areSpecialDealTitlesDisplayed() {
    try {
        return roomCardPriceSpecialDealTitle.stream().allMatch(WebElement::isDisplayed);
    } catch (Exception e) {
        logger.error("Failed to verify special deal titles. Exception: " + e.getMessage());
        return false;
    }
}

public boolean arePricesDisplayed() {
    try {
        return pricesInRoomCard.stream().allMatch(WebElement::isDisplayed);
    } catch (Exception e) {
        logger.error("Failed to verify room prices. Exception: " + e.getMessage());
        return false;
    }
}

public boolean isSelectRoomButtonDisplayed() {
    try {
        return selectRoomButtons.stream().allMatch(WebElement::isDisplayed);
    } catch (Exception e) {
        logger.error("Failed to verify Select Room button. Exception: " + e.getMessage());
        return false;
    }
}

public void enterPromoCode(String promoCode) {
    try {
        wait.until(ExpectedConditions.visibilityOf(promocodeTextField)).clear();
        promocodeTextField.sendKeys(promoCode);
        logger.info("Entered promo code: " + promoCode);
    } catch (Exception e) {
        logger.error("Failed to enter promo code. Exception: " + e.getMessage());
    }
}

public void clickApplyPromoCodeButton() {
    try {
        wait.until(ExpectedConditions.elementToBeClickable(applyPromoCodeBtn)).click();
        logger.info("Clicked on APPLY button for promo code");
    } catch (Exception e) {
        logger.error("Failed to click APPLY button. Exception: " + e.getMessage());
    }
}

public boolean isSuccessMessageDisplayed() {
    try {
        wait.until(ExpectedConditions.visibilityOf(successMessageContainer));
        return successMessageContainer.isDisplayed();
    } catch (Exception e) {
        logger.error("Success message is not displayed. Exception: " + e.getMessage());
        return false;
    }
}
public String getMessageText() {
    try {
        wait.until(ExpectedConditions.visibilityOf(successMessageContainer));
        return successMessageContainer.getText();
    } catch (Exception e) {
        logger.error("Failed to retrieve success message text. Exception: " + e.getMessage());
        return null;
    }

}

public List<WebElement> getPromoDealCardTitles() {
    try {
        wait.until(ExpectedConditions.visibilityOfAllElements(promoDealCardTitles));
        logger.info("Promo deal card titles are visible on the modal");
        return promoDealCardTitles;
    } catch (Exception e) {
        logger.error("Failed to retrieve promo deal card titles. Exception: " + e.getMessage());
        return new ArrayList<>();
    }
}

public void clickRoomModalCloseButton() {
    try {
        wait.until(ExpectedConditions.elementToBeClickable(roomModalCloseButton.get(0))).click();
        logger.info("Clicked on room modal close (X) button");
    } catch (Exception e) {
        logger.error("Failed to click on the room modal close button. Exception: " + e.getMessage());
    }
}


}
