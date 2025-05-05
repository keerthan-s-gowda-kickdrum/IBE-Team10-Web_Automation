package pageFactoryClasses;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import utilities.ConfigReader;

public class CheckoutPage {

    private static final org.apache.logging.log4j.Logger logger = org.apache.logging.log4j.LogManager.getLogger(CheckoutPage.class);


    //traveler info section
    @FindBy(xpath = "//h2[normalize-space()='Payment Info']")
    private WebElement chekoutPageTitle;

    @FindBy(xpath = "(//div[contains(@class, '_steps__circle')])[3]")
    private WebElement checkoutStepper;

    @FindBy(xpath = "(//div[contains(@class, '_steps__circle')])[1]")
    private WebElement chooseRoomStepper;

    @FindBy(xpath = "(//div[contains(@class, '_steps__circle')])[2]")
    private WebElement chooseAddOnStepper;

    @FindBy(xpath = "//div[contains(@class,'traveler-info__row')]//input[@id='firstName']")
    private WebElement travelerInfoFirstName;

    @FindBy(xpath = "//div[contains(@class,'traveler-info__row')]//input[@id='lastName']")
    private WebElement travelerInfoLastName;

    @FindBy(xpath = "//div[@class='traveler-info__content']//input[@id='phone']")
    private WebElement travelerInfoPhoneNumber;

    @FindBy(xpath = "//div[@class='traveler-info__content']//input[@id='email']")
    private WebElement travelerInfoEmail;

    @FindBy(xpath = "//button[normalize-space()='NEXT: BILLING INFO']")
    private WebElement nextBillingInfoBtn;


   //billing info section
   @FindBy(xpath = "(//div[contains(@class,'checkout__section-content active')])[2]")
   private WebElement billingInfoContainer;

   @FindBy(xpath = "//div[contains(@class,'billing-info__row')]//input[@id='firstName']")
   private WebElement billingInfoFirstName;

   @FindBy(xpath = "//div[contains(@class,'billing-info__row')]//input[@id='lastName']")
   private WebElement billingInfoLastName;

   @FindBy(xpath = "//input[@id='address1']")
   private WebElement billingInfoAddress;

   @FindBy(xpath = "//input[@id='address2']")
   private WebElement billingInfoAddress2;

   @FindBy(xpath = "//div[@class='billing-info']//input[@id='phone']")
   private WebElement billingInfoPhoneNumber;

   @FindBy(xpath = "//div[@class='billing-info']//input[@id='email']")
   private WebElement billingInfoEmail;

   @FindBy(xpath = "(//div[contains(@class,'billing-info__dropdown-header')])[1]")
   private WebElement billingInfoCountry;

   @FindBy(xpath = "//input[contains(@placeholder,'Search countries...')]")
   private WebElement billingInfoCountryInput;

   @FindBy(xpath = "//div[ text()='Select State']")
   private WebElement billingInfoState;

   @FindBy(xpath = "//input[contains(@placeholder,'Search states...')]")
   private WebElement billingInfoStateInput;

   @FindBy(xpath = "//div[@class='billing-info__dropdown-option']")
   private WebElement billingInfoStateName;

   @FindBy(xpath = "//div[@class='billing-info']//input[@id='city']")
   private WebElement billingInfoCity;

   @FindBy(xpath = "//div[@class='billing-info']//input[@id='zip']")
   private WebElement billingInfoZipCode;

   @FindBy(xpath = "//button[normalize-space()='NEXT: PAYMENT INFO']")
   private WebElement NextPaymentInfo;

    @FindBy(xpath = "//div[@class='otp-modal__content']")
    private WebElement optVerificationModal;

    @FindBy(xpath = "//input[@placeholder='Enter OTP']")
    private WebElement optVerificationInputField;

    @FindBy(xpath = "//button[normalize-space()='Verify OTP']")
    private WebElement optVerificationSubmitBtn;

    @FindBy(xpath = "//div[@class='payment-info__card-section']")
    private WebElement paymentInfoSection;

    @FindBy(xpath = "//input[@id='cardNumber']")
    private WebElement cardNumberField;

    @FindBy(xpath = "//input[@id='expiryMonth']")
    private WebElement cardExpiryMonthField;

    @FindBy(xpath = "//input[@id='expiryYear']")
    private WebElement cardExpiryYearField;

    @FindBy(xpath = "//input[@id='cvv']")
    private WebElement cardCvvField;

    @FindBy(xpath = "//label[@class='payment-info__checkbox' and contains(., 'Terms')]//input[@type='checkbox']")
    private WebElement cardTermsCheckbox;

    @FindBy(xpath = "//div[@class='payment-info__total-amount']")
    private WebElement cardTotalAmount;

    @FindBy(xpath = "//button[normalize-space()='PURCHASE']")
    private WebElement cardPayNowBtn;

    @FindBy(xpath = "//div[@class='confirmation-page__main-title']")
    private WebElement bookingConfirmationTitle;

    @FindBy(xpath = "//button[normalize-space()='CONTINUE SHOPPING']")
    private WebElement continueShoppingBtn;

    @FindBy(xpath = "//div[@class='itinerary itinerary--result']")
    private WebElement tripIteraryPanelInRoomResultsPage;

    @FindBy(xpath = "//button[normalize-space()='Remove']")
    private WebElement tripIteraryPanelRemoveLink;



    private WebDriver driver;
    private JavascriptExecutor js;
    private WebDriverWait wait;

    public CheckoutPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(20));
        this.js = (JavascriptExecutor) driver;
        PageFactory.initElements(driver, this);
    }

    public boolean isCheckoutPageDisplayed() {
        try {
            String currentUrl = driver.getCurrentUrl();
            logger.info("Current URL: " + currentUrl);
    
            // Check URL conditions
           // boolean urlValid = currentUrl.contains("checkout") ;
    
            // Wait for the checkout title to be visible
            boolean titleVisible = wait.until(ExpectedConditions.visibilityOf(chekoutPageTitle)).isDisplayed();
    
            return titleVisible;
        } catch (Exception e) {
            logger.error("Checkout page verification failed. Exception: " + e.getMessage());
            return false;
        }
    }

    public boolean isCheckoutStepHighlighted() {
        try {
            wait.until(ExpectedConditions.visibilityOf(checkoutStepper));
            String stepClass = checkoutStepper.getAttribute("class");
            // Optional: you can check for presence of specific class names if needed
            return stepClass.contains("current") || stepClass.contains("_steps__circle--current");
        } catch (Exception e) {
            logger.error("Choose Room step is not highlighted. Exception: " + e.getMessage());
            return false;
        }
    }

    public boolean isChooseAddOnStepHighlighted() {
        try {
            wait.until(ExpectedConditions.visibilityOf(chooseAddOnStepper));
            String stepClass = chooseAddOnStepper.getAttribute("class");
            // Optional: you can check for presence of specific class names if needed
            return stepClass.contains("completed") || stepClass.contains("_steps__circle--completed");
        } catch (Exception e) {
            logger.error("Choose Add-On step is not highlighted as completed. Exception: " + e.getMessage());
            return false;
        }
    }

    public boolean isChooseRoomStepHighlighted() {
        try {
            wait.until(ExpectedConditions.visibilityOf(chooseRoomStepper));
            String stepClass = chooseRoomStepper.getAttribute("class");
            // Optional: you can check for presence of specific class names if needed
            return stepClass.contains("completed") || stepClass.contains("_steps__circle--completed");
        } catch (Exception e) {
            logger.error("Choose Room step is not highlighted as completed. Exception: " + e.getMessage());
            return false;
        }
    }

// Inside CheckoutPage.java class

public boolean enterTravelerInfoAndClickNextBilling(String firstName, String lastName, String phoneNumber, String email) {
    try {
        wait.until(ExpectedConditions.visibilityOf(travelerInfoFirstName)).clear();
        travelerInfoFirstName.sendKeys(firstName);

        wait.until(ExpectedConditions.visibilityOf(travelerInfoLastName)).clear();
        travelerInfoLastName.sendKeys(lastName);

        wait.until(ExpectedConditions.visibilityOf(travelerInfoPhoneNumber)).clear();
        travelerInfoPhoneNumber.sendKeys(phoneNumber);

        wait.until(ExpectedConditions.visibilityOf(travelerInfoEmail)).clear();
        travelerInfoEmail.sendKeys(email);

        wait.until(ExpectedConditions.elementToBeClickable(nextBillingInfoBtn)).click();

        logger.info("Traveler information entered and 'NEXT: BILLING INFO' button clicked successfully.");
        return true;
    } catch (Exception e) {
        logger.error("Failed to enter traveler information or click 'NEXT: BILLING INFO' button. Exception: " + e.getMessage());
        return false;
    }
        
    }

    public boolean isBillingInfoSectionExpanded() {
        try {
            wait.until(ExpectedConditions.visibilityOf(billingInfoContainer));
            return billingInfoContainer.isDisplayed();
        } catch (Exception e) {
            logger.error("Billing Info section is not expanded. Exception: " + e.getMessage());
            return false;
        }
    }

    public void enterBillingInfo(
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
    
        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));
            
            // Wait for billing section to be fully expanded and scroll to it
            wait.until(ExpectedConditions.visibilityOf(billingInfoContainer));
            js.executeScript("arguments[0].scrollIntoView(true);", billingInfoContainer);
            Thread.sleep(1000);
            
            // Fill in basic info
            wait.until(ExpectedConditions.elementToBeClickable(billingInfoFirstName));
            billingInfoFirstName.clear();
            billingInfoFirstName.sendKeys(billingFirstName);
            logger.info("Entered billing first name: " + billingFirstName);
    
            wait.until(ExpectedConditions.elementToBeClickable(billingInfoLastName));
            billingInfoLastName.clear();
            billingInfoLastName.sendKeys(billingLastName);
            logger.info("Entered billing last name: " + billingLastName);
    
            wait.until(ExpectedConditions.elementToBeClickable(billingInfoAddress));
            billingInfoAddress.clear();
            billingInfoAddress.sendKeys(mailingAddress1);
            logger.info("Entered billing address line 1: " + mailingAddress1);
    
            wait.until(ExpectedConditions.elementToBeClickable(billingInfoAddress2));
            billingInfoAddress2.clear();
            billingInfoAddress2.sendKeys(mailingAddress2);
            logger.info("Entered billing address line 2: " + mailingAddress2);
    
            // Wait for the country dropdown to be clickable and click it
            wait.until(ExpectedConditions.elementToBeClickable(billingInfoCountry)).click();
            wait.until(ExpectedConditions.visibilityOf(billingInfoCountryInput)).sendKeys(country);

            // Wait for the country element to be present and clickable, then click
            WebElement countryElement = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//div[normalize-space()='" + country + "']")));
            countryElement.click();

            // Wait for the state dropdown to be clickable and click it
            wait.until(ExpectedConditions.elementToBeClickable(billingInfoState)).click();
            wait.until(ExpectedConditions.visibilityOf(billingInfoStateInput)).sendKeys(state);

            // Wait for the state element to be present and clickable, then click
            WebElement stateElement = wait.until(ExpectedConditions.elementToBeClickable(billingInfoStateName));
            stateElement.click();
            
            // Fill in remaining fields
            wait.until(ExpectedConditions.elementToBeClickable(billingInfoZipCode));
            billingInfoZipCode.clear();
            billingInfoZipCode.sendKeys(zipcode);
            logger.info("Entered billing zip code: " + zipcode);
    
            wait.until(ExpectedConditions.elementToBeClickable(billingInfoCity));
            billingInfoCity.clear();
            billingInfoCity.sendKeys(city);
            logger.info("Entered billing city: " + city);
    
            wait.until(ExpectedConditions.elementToBeClickable(billingInfoPhoneNumber));
            billingInfoPhoneNumber.clear();
            billingInfoPhoneNumber.sendKeys(billingPhoneNumber);
            logger.info("Entered billing phone number: " + billingPhoneNumber);
    
            wait.until(ExpectedConditions.elementToBeClickable(billingInfoEmail));
            billingInfoEmail.clear();
            billingInfoEmail.sendKeys(billingEmail);
            logger.info("Entered billing email: " + billingEmail);
            
        } catch (Exception e) {
            logger.error("Failed to enter billing information: " + e.getMessage());
            throw new RuntimeException("Failed to enter billing information", e);
        }
    }

    public void clickNextPaymentInfo() {
        // Wait for the Next Payment Info button to be clickable and click on it
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait.until(ExpectedConditions.elementToBeClickable(NextPaymentInfo)).click();
        logger.info("Clicked on the 'Next: Payment Info' button.");
    }

    public boolean isOptVerificationModalDisplayed() {
        try {
            wait.until(ExpectedConditions.visibilityOf(optVerificationModal));
            return optVerificationModal.isDisplayed();
        } catch (Exception e) {
            logger.error("OTP Verification Modal is not displayed. Exception: " + e.getMessage());
            return false;
        }
    }

    public void enterOtp() throws InterruptedException {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        Thread.sleep(30000);
        WebElement otpField = wait.until(ExpectedConditions.visibilityOf(optVerificationInputField));
        String otp = ConfigReader.getProperty("otp");
        // otpField.clear();
        // otpField.sendKeys(otp);
        // logger.info("Entered OTP: " + otp);
        Thread.sleep(2000); // Optional: wait for 2 seconds after entering OTP
    }
    
    public void clickVerifyOtp() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        WebElement verifyOtpButton = wait.until(ExpectedConditions.elementToBeClickable(optVerificationSubmitBtn));
        verifyOtpButton.click();
        logger.info("Clicked VERIFY OTP button.");
    }

    public boolean isPaymentInfoSectionDisplayed() {
        try {
            wait.until(ExpectedConditions.visibilityOf(paymentInfoSection));
            return paymentInfoSection.isDisplayed();
        } catch (Exception e) {
            logger.error("Payment Info section is not displayed. Exception: " + e.getMessage());
            return false;
        }
    }

    public void enterPaymentDetails(String cardNumber, String expiryMonth, String expiryYear, String cvv) {
        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

            ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", cardNumberField);
            logger.info("Scrolled to Payment Info Section.");
    
            // Wait and enter Card Number
            WebElement cardNumField = wait.until(ExpectedConditions.visibilityOf(cardNumberField));
            cardNumField.clear();
            cardNumField.sendKeys(cardNumber);
            logger.info("Entered Card Number: " + cardNumber);

    
            // Wait and enter Expiry Month
            WebElement expMonthField = wait.until(ExpectedConditions.visibilityOf(cardExpiryMonthField));
            expMonthField.clear();
            expMonthField.sendKeys(expiryMonth);
            logger.info("Entered Expiry Month: " + expiryMonth);

    
            // Wait and enter Expiry Year
            WebElement expYearField = wait.until(ExpectedConditions.visibilityOf(cardExpiryYearField));
            expYearField.clear();
            expYearField.sendKeys(expiryYear);
            logger.info("Entered Expiry Year: " + expiryYear);
       
    
            // Wait and enter CVV
            WebElement cvvFieldElement = wait.until(ExpectedConditions.visibilityOf(cardCvvField));
            cvvFieldElement.clear();
            cvvFieldElement.sendKeys(cvv);
            logger.info("Entered CVV: " + cvv);
      
    
        } catch (Exception e) {
            logger.error("Failed to enter payment details. Exception: " + e.getMessage());
            throw e;
        }
    }

    public void selectTermsAndConditionsCheckbox() {
        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

    
            // Wait until visible and click if not already selected
            WebElement checkbox = wait.until(ExpectedConditions.visibilityOf(cardTermsCheckbox));
            if (!checkbox.isSelected()) {
                checkbox.click();
                logger.info("Selected Terms and Conditions checkbox.");
            } else {
                logger.info("Terms and Conditions checkbox was already selected.");
            }
    
        } catch (Exception e) {
            logger.error("Failed to select Terms and Conditions checkbox. Exception: " + e.getMessage());
            throw e;
        }
    }
    
    public void clickPurchaseButton() {
        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

            WebElement purchaseButton = wait.until(ExpectedConditions.elementToBeClickable(cardPayNowBtn));
            purchaseButton.click();
            logger.info("Clicked on the Purchase button.");
    
        } catch (Exception e) {
            logger.error("Failed to click on the Purchase button. Exception: " + e.getMessage());
            throw e;
        }
    }
    public boolean isBookingConfirmationDisplayed() {
        try {
            wait.until(ExpectedConditions.visibilityOf(bookingConfirmationTitle));
            return bookingConfirmationTitle.isDisplayed();
        } catch (Exception e) {
            logger.error("Booking Confirmation page is not displayed. Exception: " + e.getMessage());
            return false;
        }
    }

    public void clickContinueShoppingButton() {
        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
            WebElement continueShoppingButton = wait.until(ExpectedConditions.elementToBeClickable(continueShoppingBtn));
            continueShoppingButton.click();
            logger.info("Clicked on the Continue Shopping button.");
        } catch (Exception e) {
            logger.error("Failed to click on the Continue Shopping button. Exception: " + e.getMessage());
            throw e;
        }
    }

    public boolean isTripItineraryPanelDisplayed() {
        try {
            wait.until(ExpectedConditions.visibilityOf(tripIteraryPanelInRoomResultsPage));
            boolean isDisplayed = tripIteraryPanelInRoomResultsPage.isDisplayed();
            logger.info("Trip Itinerary panel displayed: " + isDisplayed);
            return isDisplayed;
        } catch (Exception e) {
            logger.error("Trip Itinerary panel is not displayed. Exception: " + e.getMessage());
            return false;
        }
    }

    public void clickRemoveLink() {
        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
            
            // // Scroll into view before clicking
            // ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", tripIteraryPanelRemoveLink);
            // logger.info("Scrolled to the Remove link.");
    
            WebElement removeLink = wait.until(ExpectedConditions.elementToBeClickable(tripIteraryPanelRemoveLink));
            removeLink.click();
            logger.info("Clicked on the Remove link.");
        } catch (Exception e) {
            logger.error("Failed to click on the Remove link. Exception: " + e.getMessage());
            throw e;
        }
    }








    



}
