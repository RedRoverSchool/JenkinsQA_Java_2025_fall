package school.redrover.common;

import org.openqa.selenium.*;

public class TestUtils {

    public static void clickJS(WebDriver driver, WebElement element) {
        JavascriptExecutor executor = (JavascriptExecutor)driver;
        executor.executeScript("arguments[0].click();", element);
    }

    public static void clickJS(WebDriver driver, By locator) {
        clickJS(driver, driver.findElement(locator));
    }

    public static void mouseEnterJS(WebDriver driver, WebElement element) {
        JavascriptExecutor executor = (JavascriptExecutor) driver;
        executor.executeScript("arguments[0].dispatchEvent(new Event('mouseenter'));", element);
    }

    public static void focusAndEnterByKeyboard(WebDriver driver, WebElement element){
        ((JavascriptExecutor) driver).executeScript("arguments[0].focus();", element);
        WebElement activeElement = driver.switchTo().activeElement();
        activeElement.sendKeys(Keys.ENTER);
    }
}
