package pageFactoryClasses;

import java.time.Duration;
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

    @FindBy(xpath = "//div[@class='_container_1l40t_7']")
    private WebElement roomResultsPageContainer;

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

}
