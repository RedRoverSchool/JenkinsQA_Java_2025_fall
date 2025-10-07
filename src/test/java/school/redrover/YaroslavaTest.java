package school.redrover;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.time.Duration;

public class YaroslavaTest{
    @Test
    public void testProductsButton() {
        WebDriver driver = new ChromeDriver();
        driver.get("https://automationexercise.com/");

        WebElement products = driver.findElement(By.xpath("//a[@href='/products']"));

        Assert.assertEquals(products.getText(), " Products");

        driver.quit();
    }
    @Test
    public void testEmail(){
        WebDriver driver = new ChromeDriver();
        driver.get("https://automationexercise.com/");

        WebElement cart = driver.findElement(By.xpath("//a[@href='/view_cart']"));
        cart.click();

        WebElement field = driver.findElement(By.id("susbscribe_email"));
        field.sendKeys("Ted@gmail.com");

        WebElement go = driver.findElement(By.cssSelector(".fa.fa-arrow-circle-o-right"));
        go.click();

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
        WebElement succsessfully = wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.cssSelector(".alert-success.alert")));

        Assert.assertEquals(succsessfully.getText(), "You have been successfully subscribed!");

        driver.quit();
    }
}
