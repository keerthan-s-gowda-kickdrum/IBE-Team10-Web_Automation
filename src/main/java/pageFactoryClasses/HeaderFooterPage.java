package pageFactoryClasses;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;

public class HeaderFooterPage {
    private WebDriverWait wait;

    @FindBy(css = "header.header")
    private WebElement mainHeader;

    @FindBy(css = ".header__logo")
    private WebElement logo;

    @FindBy(css = ".header__title")
    private WebElement bookingEngineName;

    @FindBy(css = ".header__link:not(.header__hamburger)")
    private WebElement bookingsLink;

    @FindBy(css = ".header__dropdown-toggle")
    private WebElement languageDropdown;

    @FindBy(css = ".header__currency")
    private WebElement currencyDropdown;

    @FindBy(css = ".header__login-btn")
    private WebElement loginButton;

    @FindBy(css = ".footer, footer")
    private WebElement footer;

    @FindBy(css = ".footer img, footer img, .footer .logo, footer .logo")
    private WebElement footerLogo;

    @FindBy(css = ".footer .copyright, footer .copyright, .footer p, footer p")
    private WebElement copyrightText;

    public HeaderFooterPage(WebDriver driver) {
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        PageFactory.initElements(driver, this);
    }

    public boolean isHeaderVisible() {
        return wait.until(ExpectedConditions.visibilityOf(mainHeader)).isDisplayed();
    }

    public boolean isLogoDisplayed() {
        return wait.until(ExpectedConditions.visibilityOf(logo)).isDisplayed();
    }

    public boolean isBookingEngineNameDisplayed() {
        return wait.until(ExpectedConditions.visibilityOf(bookingEngineName)).isDisplayed();
    }

    public boolean isBookingsLinkPresent() {
        return wait.until(ExpectedConditions.visibilityOf(bookingsLink)).isDisplayed();
    }

    public boolean isLanguageDropdownAvailable() {
        return wait.until(ExpectedConditions.visibilityOf(languageDropdown)).isDisplayed();
    }

    public boolean isCurrencyDropdownAvailable() {
        return wait.until(ExpectedConditions.visibilityOf(currencyDropdown)).isDisplayed();
    }

    public boolean isLoginButtonVisible() {
        return wait.until(ExpectedConditions.visibilityOf(loginButton)).isDisplayed();
    }

    public boolean isFooterVisible() {
        return wait.until(ExpectedConditions.visibilityOf(footer)).isDisplayed();
    }

    public boolean isFooterLogoDisplayed() {
        return wait.until(ExpectedConditions.visibilityOf(footerLogo)).isDisplayed();
    }

    public String getCopyrightText() {
        return wait.until(ExpectedConditions.visibilityOf(copyrightText)).getText();
    }

    public boolean isCopyrightDisplayed() {
        return wait.until(ExpectedConditions.visibilityOf(copyrightText)).isDisplayed();
    }
}
