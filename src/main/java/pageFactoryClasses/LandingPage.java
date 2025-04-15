package pageFactoryClasses;

import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import utilities.ConfigReader;

import java.time.Duration;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Arrays;

public class LandingPage {

    private static final org.apache.logging.log4j.Logger logger = org.apache.logging.log4j.LogManager.getLogger(LandingPage.class);

    @FindBy(css = "img[alt*='banner'], img[alt*='hero'], .banner-image, .hero-image, .header-image, header img, main img")
    private List<WebElement> bannerImages;

    @FindBy(css = ".spinner-container")
    private WebElement spinner;

    @FindBy(xpath = "//header[@class='header']")
    private WebElement Header;

    @FindBy(css = "[type='button'].search__button")
    private WebElement propertyButton;

    @FindBy(css = ".search__dropdown-menu")
    private WebElement propertyDropdownMenu;

    @FindBy(xpath = "//div[@class='date-input__box']")
    private WebElement dateRangeField;

    @FindBy(xpath = "//div[@class='date-input__calendar']")
    private WebElement rateCalender ;

    @FindBy(xpath = "//button[normalize-space()='SEARCH']")
    private WebElement searchButton;

    @FindBy(xpath = "//button[normalize-space()='APPLY DATES']")
    private WebElement applyDatesBtn ;

    @FindBy(css = "span.date-input__text:nth-child(1)")
    private WebElement checkInPlaveHolder ;

    @FindBy(css = "span.date-input__text:nth-child(3)")
    private WebElement checkOutPlaveHolder ;

    @FindBy(xpath = "//button[@class='search__guests-button']")
    private WebElement guestDropdown ;


    private WebDriver driver;
    private JavascriptExecutor js;
    private WebDriverWait wait;

    public LandingPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(20));
        this.js = (JavascriptExecutor) driver;
        PageFactory.initElements(driver, this);
    }

    private void waitForSpinnerToDisappear() {
        try {
            wait.until(ExpectedConditions.invisibilityOfElementLocated(By.cssSelector(".spinner-container")));
        } catch (TimeoutException | NoSuchElementException e) {
            // Spinner might not be present, which is fine
        }
    }

    public WebElement getBannerImage() {
        waitForSpinnerToDisappear();
        wait.until(ExpectedConditions.presenceOfElementLocated(
                By.cssSelector("img[alt*='banner'], img[alt*='hero'], .banner-image, .hero-image, .header-image, header img, main img")
        ));

        // Log all images found on the page for debugging
        List<WebElement> allImages = driver.findElements(By.tagName("img"));
        for (WebElement img : allImages) {
            try {
                System.out.println("Found image with src: " + img.getAttribute("src") +
                        ", alt: " + img.getAttribute("alt") +
                        ", class: " + img.getAttribute("class"));
            } catch (StaleElementReferenceException e) {
                continue;
            }
        }

        // Return the first visible banner image
        for (WebElement image : bannerImages) {
            try {
                if (image.isDisplayed()) {
                    return image;
                }
            } catch (StaleElementReferenceException e) {
                PageFactory.initElements(driver, this);
                continue;
            }
        }

        throw new NoSuchElementException("No visible banner image found on the page");
    }

    public void selectProperty(String propertyName) {
        waitForSpinnerToDisappear();
        try {
            wait.until(ExpectedConditions.elementToBeClickable(propertyButton)).click();
            wait.until(ExpectedConditions.presenceOfElementLocated(By.className("property-dropdown")));

            By propertyOption = By.xpath(
                    String.format("//div[contains(@class, 'search__dropdown-item') and contains(text(), '%s')]", propertyName)
            );

            WebElement option = wait.until(ExpectedConditions.elementToBeClickable(propertyOption));
            option.click();
        } catch (Exception e) {
            System.out.println("Current page source during property selection: " + driver.getPageSource());
            throw e;
        }
    }

    public void clickDateRangeField() {
        waitForSpinnerToDisappear();
        wait.until(ExpectedConditions.elementToBeClickable(dateRangeField)).click();
    }

    public String getPropertyDropdownPlaceholder() {
        waitForSpinnerToDisappear();
        return wait.until(ExpectedConditions.visibilityOf(propertyButton)).getText();
    }

    public boolean isSearchButtonDisabled() {
        waitForSpinnerToDisappear();
        try {
            System.out.println("Search button 'disabled' attribute: " + searchButton.getAttribute("disabled"));
            return searchButton.getAttribute("disabled") != null;
        } catch (Exception e) {
            System.out.println("Failed to access the search button. Page source: " + driver.getPageSource());
            throw e;
        }
    }
    public void clickCheckInCheckOutField() {
        waitForSpinnerToDisappear();
        wait.until(ExpectedConditions.elementToBeClickable(dateRangeField)).click();
        logger.info("Clicked on the Check-in/Check-out date range field.");
    }
    public boolean isRateCalendarDisplayed() {
        try {
            return rateCalender.isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    public boolean isRateCalendarClosed() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        try {
            wait.until(ExpectedConditions.invisibilityOf(rateCalender));
            return !rateCalender.isDisplayed(); // Should throw if already gone
        } catch (Exception e) {
            return true; // If not displayed or already gone, treat as closed
        }
    }
    

    public void selectCheckInDateFromConfig() {
        try {
            String checkInDay = ConfigReader.getProperty("checkInDay"); // e.g., "14"

            // Parameterize the base XPath
            String dynamicXpath = String.format("(//span[contains(text(),'%s')])[1]", checkInDay);

            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
            WebElement dateElement = wait.until(ExpectedConditions.elementToBeClickable(By.xpath(dynamicXpath)));

            // Scroll to the element and click it
            ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView({block: 'center'});", dateElement);
            try {
                Thread.sleep(500); // small wait to ensure smooth scroll
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                throw new RuntimeException("Thread was interrupted while waiting for scroll", e);
            }
            dateElement.click();

        } catch (Exception e) {
            System.out.println("Failed to select check-in date from config: " + e.getMessage());
            throw new RuntimeException("Failed to select check-in date", e);
        }
    }

    public void selectCheckOutDateAfterCheckIn() {
        try {
            String checkInDayStr = ConfigReader.getProperty("checkInDay"); // e.g., "14"
            String rangeStr = ConfigReader.getProperty("rangeOfStay");     // e.g., "3"
    
            int checkInDay = Integer.parseInt(checkInDayStr);
            int range = Integer.parseInt(rangeStr);
            int checkOutDay = checkInDay + range;
    
            // Parameterize the base XPath
            String dynamicXpath = String.format("(//span[contains(text(),'%s')])[1]", checkOutDay);
    
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
            WebElement dateElement = wait.until(ExpectedConditions.elementToBeClickable(By.xpath(dynamicXpath)));
    
            // Scroll to the element and click it
            ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView({block: 'center'});", dateElement);
            try {
                Thread.sleep(500); // small wait to ensure smooth scroll
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                throw new RuntimeException("Thread was interrupted while waiting for scroll", e);
            }
            new Actions(driver).moveToElement(dateElement).click().perform();
    
        } catch (Exception e) {
            System.out.println("Failed to select check-out date after check-in: " + e.getMessage());
            throw new RuntimeException("Failed to select check-out date", e);
        }
    }
    

    public boolean isApplyButtonDisabled() {
        String classes = applyDatesBtn.getAttribute("class");
        return classes.contains("disabled");
    }

    public boolean isApplyButtonEnabled() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        try {
            wait.until(ExpectedConditions.elementToBeClickable(applyDatesBtn));
            String classes = applyDatesBtn.getAttribute("class");
            return classes.contains("active") && applyDatesBtn.isEnabled();
        } catch (Exception e) {
            return false;
        }
    }
    

    public void clickApplyDatesButton() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait.until(ExpectedConditions.elementToBeClickable(applyDatesBtn)).click();
    }

    public String getCheckInPlaceholderText() {
        waitForSpinnerToDisappear();
        return wait.until(ExpectedConditions.visibilityOf(checkInPlaveHolder)).getText();
    }

    public String getCheckOutPlaceholderText() {
        waitForSpinnerToDisappear();
        return wait.until(ExpectedConditions.visibilityOf(checkOutPlaveHolder)).getText();
    }

    public void clickSearchButton() {
        searchButton.click();
    }
    
    

}
