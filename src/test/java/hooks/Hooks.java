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

        driver = DriverManager.getDriver(browser);
        driver.manage().window().maximize();
        logger.info("Browser launched and maximized.");
    }

    @After
    public void tearDown(Scenario scenario) {
        if (scenario.isFailed()) {
            logger.error("Scenario FAILED: {}", scenario.getName());

            // Sanitize scenario name for a valid filename
            String sanitizedScenarioName = scenario.getName().replaceAll("[^a-zA-Z0-9]", "_");
            String filename = sanitizedScenarioName + ".png";
            String directory = System.getProperty("user.dir") + "/FailedScreenshots/";

            // Ensure directory exists
            File screenshotDir = new File(directory);
            if (!screenshotDir.exists()) {
                screenshotDir.mkdirs();
            }

            // Capture the screenshot
            File sourceFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
            File destinationFile = new File(directory + filename);

            try {
                FileUtils.copyFile(sourceFile, destinationFile);
                logger.info("Screenshot saved at: {}", destinationFile.getAbsolutePath());

                // Attach screenshot to Extent Reports
                test.get().fail("Test Failed - Screenshot Attached").addScreenCaptureFromPath("../FailedScreenshots/" + filename);

            } catch (IOException e) {
                logger.error("Error capturing screenshot: {}", e.getMessage());
            }
        } else {
            logger.info("Scenario PASSED: {}", scenario.getName());
            test.get().pass("Test Passed");
        }

        if (driver != null) {
            driver.quit();
            logger.info("Browser closed after scenario: {}", scenario.getName());
        }

        extent.flush();
        logger.info("Extent Reports flushed.");
    }

    public static ExtentTest getTest() {
        return test.get();
    }
}
