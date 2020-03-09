package com.hepsiburada.step;

import com.hepsiburada.base.BaseTest;
import com.hepsiburada.helper.ElementHelper;
import com.hepsiburada.helper.StoreHelper;
import com.hepsiburada.model.ElementInfo;
import com.thoughtworks.gauge.Step;
import org.junit.Assert;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.security.Key;
import java.util.List;

public class BaseStep extends BaseTest {
    public Actions actions;

    public BaseStep() {
        actions = new Actions(driver);
    }

    public By getBy(String key) {
        ElementInfo elementInfo = StoreHelper.INSTANCE.findElementInfoByKey(key);
        By infoParam = ElementHelper.getElementByElementInfo(elementInfo);
        return infoParam;
    }

    public List<WebElement> findElementsByKey(String key) {
        WebDriverWait webDriverWait = new WebDriverWait(driver, 30);

        ElementInfo elementInfo = StoreHelper.INSTANCE.findElementInfoByKey(key);
        By infoParam = ElementHelper.getElementByElementInfo(elementInfo);
        webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(infoParam));
        return driver.findElements(infoParam);
    }

    public WebElement findElementByKey(String key) {
        By infoParam = getBy(key);
        WebDriverWait webDriverWait = new WebDriverWait(driver, 30);

        WebElement webElement = webDriverWait.until(ExpectedConditions.presenceOfElementLocated(infoParam));
        ((JavascriptExecutor) driver).executeScript(
                "arguments[0].scrollIntoView({behavior: 'smooth', block: 'center', inline: 'center'})",
                webElement);
        return webElement;
    }

    @Step("Wait for <key>")
    public WebElement waitElementLoadByKey(String key) {

        WebElement webElement;
        int loopCount = 0;
        while (loopCount < 200) {
            try {
                webElement = findElementByKey(key);
                //logger.info("Element:'" + key + "' found.");
                return webElement;

            } catch (WebDriverException e) {
            }
            loopCount++;
            waitByMilliSeconds(200);
        }
        Assert.fail("Element: '" + key + "' doesn't exist.");
        return null;
    }

    @Step("Wait <value> seconds")
    public void waitBySeconds(int seconds) {
        try {
            //logger.info("Waiting for " + seconds + " seconds.");
            Thread.sleep(seconds * 1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Step("Wait <value> milliseconds")
    public void waitByMilliSeconds(long milliseconds) {
        try {
            Thread.sleep(milliseconds);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Step("Hover on <key>")
    public void hoverElement(String key) {
        if (!key.equals("")) {
            WebElement element = findElementByKey(key);
            hoverElement(element);
            waitByMilliSeconds(500);
        }
    }

    @Step("Enter <key>")
    public void enter(String key) {
        findElementByKey(key).sendKeys(Keys.RETURN);
    }

    private void hoverElement(WebElement element) {
        actions.moveToElement(element).build().perform();
    }

    public void writeToCsv(String filePath, String productName, String price) {
        try {
            File myObj = new File(filePath);
            if (myObj.createNewFile()) {
                System.out.println("File created: " + myObj.getName());
            } else {
                System.out.println("File already exists.");
            }

            FileWriter writer = new FileWriter(filePath, true);
            writer.write(productName + " - " + price);
            writer.write("\n");
            writer.close();

        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }
}
