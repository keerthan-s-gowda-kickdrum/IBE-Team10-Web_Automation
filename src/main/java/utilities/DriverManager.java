package utilities;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class DriverManager {
    private static WebDriver driver;
    private static final Logger logger = LogManager.getLogger(DriverManager.class);

    private DriverManager() {}

    public static synchronized WebDriver getDriver(String browser) {
        if (driver == null) {
            driver = createDriver(browser);
            logger.info("Initialized WebDriver for browser: {}", browser);
        }
        return driver;
    }

    private static WebDriver createDriver(String browser) {
        switch (browser.toLowerCase()) {
            case "chrome":
                WebDriverManager.chromedriver().setup();
                return new ChromeDriver();
            case "firefox":
                WebDriverManager.firefoxdriver().setup();
                return new FirefoxDriver();
            case "edge":
                WebDriverManager.edgedriver().setup();
                return new EdgeDriver();
            default:
                throw new IllegalArgumentException("Invalid browser: " + browser);
        }
    }

    public static synchronized void quitDriver() {
        if (driver != null) {
            driver.quit();
            driver = null;
            logger.info("WebDriver has been quit and set to null");
        }
    }

    public static synchronized void resetDriver(String browser) {
        quitDriver();
        getDriver(browser);
    }
}