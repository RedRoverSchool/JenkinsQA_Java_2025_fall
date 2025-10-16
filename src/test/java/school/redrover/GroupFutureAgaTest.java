package school.redrover;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.Test;

public class GroupFutureAgaTest {

    @Test
    public void testSelenium() throws InterruptedException{
        WebDriver driver = new ChromeDriver();
        driver.get("https://www.selenium.dev/selenium/web/web-form.html");
        Thread.sleep(1000);

        driver.findElement(By.name("my-password")).sendKeys("123");;
        driver.findElement(By.cssSelector("button")).click();

        WebElement message = driver.findElement(By.id("message"));
        Assert.assertEquals(message.getText(),"Received!");

        driver.quit();
    }

    @Test
    public void testSelenium1() throws InterruptedException{
        WebDriver driver = new ChromeDriver();
        driver.get("https://www.selenium.dev/selenium/web/web-form.html");
        Thread.sleep(1000);

        driver.findElement(By.name("my-textarea")).sendKeys("Hello world");
        driver.findElement(By.cssSelector("button")).click();


        WebElement message = driver.findElement(By.id("message"));
        Assert.assertEquals(message.getText(),"Received!");

        driver.quit();}
}


