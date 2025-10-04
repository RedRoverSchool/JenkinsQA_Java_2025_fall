package school.redrover;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.time.Duration;



public class IraTest {


    @Test
    public void buyBuketTest()   {
        WebDriver driver = new ChromeDriver();

        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
        driver.get("https://www.saucedemo.com/");
        driver.findElement(By.id("user-name")).sendKeys("standard_user");
        driver.findElement(By.id("password")).sendKeys("secret_sauce");
        driver.findElement(By.xpath("//input[@class='submit-button btn_action']")).click();
        WebElement header = driver.findElement(By.xpath("//div[@class='header_label']"));
        Assert.assertEquals(header.getText(), "Swag Labs");
    }

    @Test
    public void addToCardTest(){
        WebDriver driver = new ChromeDriver();
            driver.manage().window().maximize();
            driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
            driver.get("https://www.saucedemo.com/");
            driver.findElement(By.id("user-name")).sendKeys("standard_user");
            driver.findElement(By.id("password")).sendKeys("secret_sauce");
            driver.findElement(By.xpath("//input[@class='submit-button btn_action']")).click();
            WebElement header = driver.findElement(By.xpath("//div[@class='header_label']"));
        Assert.assertEquals(header.getText(), "Swag Labs");
        driver.findElement(By.xpath("//div[text()='Sauce Labs Backpack']")).click();
        driver.findElement(By.id("add-to-cart")).click();
        String basket = driver.findElement(By.cssSelector(".shopping_cart_badge")).getText();
        Assert.assertEquals(basket,"1");

        }
}
