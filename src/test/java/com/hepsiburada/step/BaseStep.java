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

import java.io.*;
import java.util.List;
import java.util.Scanner;

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
        WebDriverWait webDriverWait = new WebDriverWait(driver, 5);

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
        while (loopCount < 10) {
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

    @Step({"Click to element <key> with focus",
            "<key> elementine focus ile tıkla"})
    public void clickElementWithFocus(String key) {
        actions.moveToElement(findElementByKey(key));
        actions.click();
        actions.build().perform();
        logger.info(key + " elementine focus ile tıklandı.");
    }

    private void hoverElement(WebElement element) {
        actions.moveToElement(element).build().perform();
    }

    public void writeToCsv(String filePath, String text) {
        try {
            File file = new File(filePath);
            file.createNewFile();

            FileWriter writer = new FileWriter(filePath, true);
            writer.write(text);
            writer.write("\n");
            writer.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String readCsvFile(String path) {
        try {
            File file = new File(path);
            Scanner scanner = new Scanner(file);
            StringBuilder stringBuilder = new StringBuilder();
            while (scanner.hasNextLine()) {
                stringBuilder.append(scanner.nextLine());
                stringBuilder.append('\n');
            }

            return stringBuilder.toString();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }
}
