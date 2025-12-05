package school.redrover.common;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.Keys;

public class TestUtils {

    public static void clickJS(WebDriver driver, WebElement element) {
        JavascriptExecutor executor = (JavascriptExecutor) driver;
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

    public static String getTextJS(WebDriver driver, WebElement element) {
        return (String) ((JavascriptExecutor) driver)
                .executeScript("return arguments[0].innerText;", element);
    }
}
