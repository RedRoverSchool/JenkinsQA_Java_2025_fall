package school.redrover;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.testng.Assert;
import org.testng.annotations.Test;

public class RomanTTest {

    public static void scrollAndClick(WebDriver driver, Actions actions, WebElement element) {
        actions.scrollToElement(element).perform();
        actions.scrollByAmount(0, 200).perform();
        if (element.isDisplayed() && element.isEnabled()) {
            actions.moveToElement(element).click().perform();
        }
    }

    @Test
    public void testRecaptchaValidationMessage() {
        WebDriver driver = new ChromeDriver();
        Actions actions = new Actions(driver);

        driver.get("https://demoqa.com/");

        WebElement appPage = driver.findElement(By.xpath("//h5[text()='Book Store Application']"));
        scrollAndClick(driver, actions, appPage);

        WebElement login = driver.findElement(By.xpath("//span[text()='Login']"));
        scrollAndClick(driver, actions, login);

        WebElement newUser = driver.findElement(By.id("newUser"));
        scrollAndClick(driver, actions, newUser);

        driver.findElement(By.id("firstname")).sendKeys("Roman");
        driver.findElement(By.id("lastname")).sendKeys("T");
        driver.findElement(By.id("userName")).sendKeys("romant");
        driver.findElement(By.id("password")).sendKeys("123456");

        WebElement register = driver.findElement(By.id("register"));
        scrollAndClick(driver, actions, register);

        WebElement message = driver.findElement(By.id("output"));
        Assert.assertTrue(message.isDisplayed(), "Please verify reCaptcha to register!");

        driver.quit();
    }
}
