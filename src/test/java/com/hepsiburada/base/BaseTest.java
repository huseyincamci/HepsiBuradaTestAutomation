package com.hepsiburada.base;

import com.thoughtworks.gauge.*;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

public class BaseTest {


    protected static WebDriver driver;
    protected static WebDriverWait webDriverWait;
    protected static Logger logger = LoggerFactory.getLogger(BaseTest.class);
    DesiredCapabilities capabilities;

    @BeforeScenario
    public void setUp(ExecutionContext executionContext) throws Exception {

        Files.deleteIfExists(Paths.get("information.csv"));

        logger.info("" + executionContext.getCurrentScenario().getName());
        String baseUrl = "https://www.hepsiburada.com/";


        capabilities = DesiredCapabilities.chrome();
        Map<String, Object> prefs = new HashMap<String, Object>();
        prefs.put("profile.default_content_setting_values.notifications", 2);

        ChromeOptions options = new ChromeOptions();
        System.setProperty("webdriver.chrome.driver", "drivers/chromedriver"
                + "");


        driver = new ChromeDriver(options);
        driver.manage().window().maximize();

        webDriverWait = new WebDriverWait(driver, 10, 50);
        driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
        driver.get(baseUrl);
    }

    @BeforeStep
    public void beforeStep(ExecutionContext executionContext) {

        logger.info(executionContext.getCurrentStep().getDynamicText());
    }

    @AfterStep
    public void afterStep(ExecutionContext executionContext) {

        if (executionContext.getCurrentStep().getIsFailing()) {
            logger.error(executionContext.getCurrentScenario().getName());
            logger.error(executionContext.getCurrentStep().getDynamicText());
            logger.error(executionContext.getCurrentStep().getErrorMessage() + "\r\n"
                    + executionContext.getCurrentStep().getStackTrace());
        }
    }

    @AfterScenario
    public void tearDown() {
        driver.quit();
    }


}
