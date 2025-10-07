package school.redrover;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.time.Duration;

public class YaroslavaTest {
    private WebDriver driver;

    @BeforeMethod
    public void startBeforeTest() {
        driver = new ChromeDriver();
        driver.get("https://automationexercise.com/");
    }

    @Test
    public void testProductsButton() {
        WebElement products = driver.findElement(By.xpath("//a[@href='/products']"));

        Assert.assertEquals(products.getText(), " Products");
    }

    @Test
    public void testEmail() {
        WebElement cart = driver.findElement(By.xpath("//a[@href='/view_cart']"));
        cart.click();

        WebElement field = driver.findElement(By.id("susbscribe_email"));
        field.sendKeys("Ted@gmail.com");

        WebElement go = driver.findElement(By.cssSelector(".fa.fa-arrow-circle-o-right"));
        go.click();

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
        WebElement successfulMessage = wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.cssSelector(".alert-success.alert")));

        Assert.assertEquals(successfulMessage.getText(), "You have been successfully subscribed!");
    }

    @AfterMethod
    public void quitDriver() {
        if (driver != null) {
            driver.quit();
        }
    }
}
