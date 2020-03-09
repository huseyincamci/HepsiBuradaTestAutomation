package com.hepsiburada.step;

import com.thoughtworks.gauge.Step;
import org.junit.Assert;
import org.openqa.selenium.WebElement;

import java.util.List;
import java.util.Random;

public class StepImplementation extends BaseStep {

    @Step("Click login link")
    public void clickLoginLink() {
        hoverElement("hoverAccount");
        WebElement loginLink = findElementByKey("loginLink");
        loginLink.click();
    }

    @Step("Write <value> to email input")
    public void writeEmailValue(String value) {
        WebElement emailInput = findElementByKey("email");
        sendKeys(emailInput, "testautomationzerotohero@gmail.com");
        // waitBySeconds(2);
    }

    @Step("Write <value> to password input")
    public void writePasswordValue(String value) {
        WebElement passwordInput = findElementByKey("password");
        sendKeys(passwordInput, value);
        // waitBySeconds(2);
    }

    @Step("Login ol")
    public void login() {
        WebElement element = findElementByKey("loginBtn");
        element.click();
        //waitBySeconds(2);
    }

    @Step("Validate login")
    public void validateLogin() {
        waitBySeconds(2);
        WebElement loginUserNameLabel = findElementByKey("loginUserName");
        String loginUserName = loginUserNameLabel.getText();
        Assert.assertEquals("Test Automation", loginUserName);
        logger.info("Login işlemi başarılı");
    }

    @Step("Check total price in shopping cart")
    public void checkTotalPrice() {
        WebElement element = findElementByKey("cartItemCount");
        int itemCount = Integer.parseInt(element.getText());
        if (itemCount == 0) {
            logger.info("Total price: 0");
        }
        goShoppingCartPage();
    }

    @Step("Click random category")
    public void clickRandomCategory() {
        List<WebElement> elements = findElementsByKey("topCategory");
        WebElement topCategory = getRandomElement(elements);
        actions.moveToElement(topCategory).build().perform();
        waitBySeconds(1);
        List<WebElement> subCategories = findElementsByKey("subCategory");
        WebElement subCategory = getRandomElement(subCategories);
        subCategory.click();
        //  waitBySeconds(2);
    }

    @Step("Select random brand")
    public void selectRandomBrand() {
        List<WebElement> elements = findElementsByKey("brandName");
        WebElement brand = getRandomElement(elements);
        brand.click();
        //  waitBySeconds(2);
    }

    @Step("Write <value> value to <key> element")
    public void writeValueToElement(String value, String key) {
        WebElement element = findElementByKey(key);
        sendKeys(element, value);
        // waitBySeconds(2);
    }

    @Step("Select random product")
    public void selectRandomProduct() {
        List<WebElement> elements = findElementsByKey("listProduct");

        WebElement element = getRandomElement(elements);
        element.click();

    }

    @Step("Check product count in added product's count and my cart icon")
    public void checkProductCount() {
        WebElement productCount = findElementByKey("quantity");
        int count = Integer.parseInt(productCount.getText().trim());

        WebElement element = findElementByKey("cartItemCount");
        int itemCount = Integer.parseInt(element.getText());

        Assert.assertEquals(count, itemCount);
    }

    @Step("Write the product name and the price to csv file")
    public void writeProductNameAndPriceToCsv() {
        String productName = findElementByKey("productName").getText();
        String price = findElementByKey("priceInProductDetail").getText();
        writeToCsv("information.csv", productName, price);
    }

    @Step("Ürün sayısını <count> arttır")
    public void increaseProduct(String count) {
        int adet = Integer.parseInt(count);
        int i = 0;
        while (i++ < adet) {
            clickElementByKey("increase");
            waitBySeconds(2);
        }
    }

    @Step("Ürün toplamı ve kargo tutarı csv ye yazdır")
    public void implementation1() {
        WebElement element = findElementByKey("totalPrice");
        String totalPrice = element.getText();
        WebElement shipping = findElementByKey("shippingPrice");
        String shippingPrice = shipping.getText();
        writeToCsv("productandshippingprice.csv", totalPrice, shippingPrice);
    }


    public void getProductPrice(WebElement element) {
        WebElement priceLabel = element.findElement(getBy("productPriceInProductList"));
        String priceTxt = priceLabel.getText().split(" ")[0].replace(".", "").replace(",", ".");
        float price = Float.parseFloat(priceTxt);
        Assert.assertEquals("", price, getPriceInProductDetail());
    }

    public float getPriceInProductDetail() {
        WebElement priceInProductDetail = findElementByKey("priceInProductDetail");
        String priceTxt = priceInProductDetail.getText().split(" ")[0].replace(".", "").replace(",", ".");
        return Float.parseFloat(priceTxt);
    }

    @Step("Click element by <key>")
    public void clickElementByKey(String key) {
        waitElementLoadByKey(key).click();
    }

    public WebElement getRandomElement(List<WebElement> elements) {
        Random random = new Random();
        int randomCount = random.nextInt(elements.size());
        while (true) {
            if (randomCount == 0)
                randomCount = random.nextInt(elements.size());
            else
                break;
        }
        return elements.get(randomCount);
    }

    @Step("Go shopping cart page")
    public void goShoppingCartPage() {
        waitBySeconds(3);
        WebElement element = findElementByKey("shoppingCart");
        element.click();
        //waitBySeconds(2);
        getTotalPrice();
    }

    @Step("If shopping cart has items, Clear shopping cart")
    public void clearShoppingCart() {

        WebElement element = null;
        try {
            element = findElementByKey("emptyCart");
        } catch (Exception ex) {
        }
        if (element != null)
            logger.info("Sepet boş");
        else {
            List<WebElement> elements = findElementsByKey("btnRemoveProduct");
            int size = elements.size();
            int i = 0;
            while (i < size) {
                clickElementByKey("btnRemoveProduct");
                i++;
                waitBySeconds(1);
            }
            logger.info("Sepet boşaltıldı.");
        }

    }

    @Step("Clear all addresses")
    public void clearAddresses() {
        List<WebElement> elements = findElementsByKey("btnDeleteAddress");
        int size = elements.size();
        int i = 0;
        while (i < size / 2) {
            clickElementByKey("btnDeleteAddress");
            clickElementByKey("btnConfirmDelete");
            i++;
            waitBySeconds(3);
        }
    }

    public void getTotalPrice() {
        WebElement price = null;
        try {
            price = findElementByKey("totalPrice");
        } catch (Exception ex) {
        }

        if (price == null)
            logger.info("Total price: 0 TL");
        else {
            WebElement element = findElementByKey("totalPrice");
            String totalPrice = element.getText();
            logger.info("Total price: " + totalPrice);
        }
    }

    public void sendKeys(WebElement element, String value) {
        element.sendKeys(value);
    }

    @Step("Go <value> address")
    public void goToUrl(String url) {
        driver.get(url);
    }

    @Step("Tutarı kontrol et")
    public void implementation2() {
        WebElement element = findElementByKey("sepetUrunFiyati");
        String price = element.getText().split(" ")[0];
        WebElement totalPriceLabel = findElementByKey("totalPrice");
        String totalPrice = totalPriceLabel.getText().split(" ")[0];
        Assert.assertEquals(price, totalPrice);
    }
}
