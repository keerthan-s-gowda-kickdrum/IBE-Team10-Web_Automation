package hooks;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;
import org.apache.commons.io.FileUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import utilities.ConfigReader;
import utilities.DriverManager;

import java.io.File;
import java.io.IOException;

public class Hooks {
    private static final Logger logger = LogManager.getLogger(Hooks.class);
    private static ExtentReports extent;
    private static ThreadLocal<ExtentTest> test = new ThreadLocal<>();
    private WebDriver driver;
    private final String browser = ConfigReader.getProperty("browser");

    @Before
    public void setup(Scenario scenario) {
        logger.info("Starting scenario: {}", scenario.getName());

        if (extent == null) {
            extent = new ExtentReports();
            ExtentSparkReporter sparkReporter = new ExtentSparkReporter("ExtentReports/FinalTestReport.html");
            extent.attachReporter(sparkReporter);
            extent.setSystemInfo("Project Name", "IBE");
            extent.setSystemInfo("Organisation", "KickdrumTech");
            logger.info("Extent Reports initialized...");
        }

        test.set(extent.createTest(scenario.getName()));

        // Get or create WebDriver instance
        driver = DriverManager.getDriver(browser);
        if (driver != null) {
            driver.manage().window().maximize();
            logger.info("Browser launched and maximized.");
        }
    }

    @After
    public void tearDown(Scenario scenario) {
        try {
            if (scenario.isFailed()) {
                logger.error("Scenario FAILED: {}", scenario.getName());
                captureScreenshot(scenario);
            } else {
                logger.info("Scenario PASSED: {}", scenario.getName());
                test.get().pass("Test Passed");
            }
        } finally {
            // Only quit the driver after the last scenario
                if (driver != null) {
                    driver.quit();
                    DriverManager.quitDriver();
                    logger.info("Browser closed after last scenario: {}", scenario.getName());
                }


                extent.flush();
                logger.info("Extent Reports flushed.");

        }
    }

    private void captureScreenshot(Scenario scenario) {
        if (driver != null) {
            String sanitizedScenarioName = scenario.getName().replaceAll("[^a-zA-Z0-9]", "_");
            String filename = sanitizedScenarioName + ".png";
            String directory = System.getProperty("user.dir") + "/FailedScreenshots/";

            File screenshotDir = new File(directory);
            if (!screenshotDir.exists()) {
                screenshotDir.mkdirs();
            }

            try {
                File sourceFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
                File destinationFile = new File(directory + filename);
                FileUtils.copyFile(sourceFile, destinationFile);
                logger.info("Screenshot saved at: {}", destinationFile.getAbsolutePath());
                test.get().fail("Test Failed - Screenshot Attached")
                        .addScreenCaptureFromPath("../FailedScreenshots/" + filename);
            } catch (IOException e) {
                logger.error("Error capturing screenshot: {}", e.getMessage());
            }
        }
    }

    public static ExtentTest getTest() {
        return test.get();
    }
}
