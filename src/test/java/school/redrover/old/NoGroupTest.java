package school.redrover.old;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.time.Duration;

public class NoGroupTest {
    
    @Test
    public void testOrder(){

        WebDriver driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(2));
        driver.get("https://automationexercise.com/");

        driver.findElement(By.xpath("//*[@id=\"accordian\"]/div[2]/div[1]/h4/a")).click();
        driver.findElement(By.xpath("//*[@id=\"Men\"]/div/ul/li[2]/a")).click();
        driver.findElement(By.xpath("/html/body/section/div/div[2]/div[2]/div/div[4]/div/div[2]/ul/li/a")).click();
        driver.findElement(By.id("quantity")).clear();
        driver.findElement(By.id("quantity")).sendKeys("3");
        driver.findElement(By.cssSelector("body > section > div > div > div.col-sm-9.padding-right > div.product-details > div.col-sm-7 > div > span > button")).click();
        driver.findElement(By.xpath("//*[@id=\"cartModal\"]/div/div/div[2]/p[2]/a/u")).click();
        driver.findElement(By.xpath("//*[@id=\"product-37\"]/td[6]/a")).click();

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
        WebElement emptyCart = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id='empty_cart']/p/b")));

        Assert.assertEquals(emptyCart.getText(), "Cart is empty!");

        driver.quit();

    }
    
    
}
