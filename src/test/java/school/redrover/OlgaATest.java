package school.redrover;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.Test;
import java.time.Duration;

public class OlgaATest {

    @Test
public void testSelenium() throws InterruptedException{
    WebDriver driver = new ChromeDriver();

    driver.get("https://www.selenium.dev/selenium/web/web-form.html");

   Thread.sleep(1000);

    WebElement textBox = driver.findElement(By.name("my-password"));
    WebElement submitButton = driver.findElement(By.cssSelector("button"));

    textBox.sendKeys("123");
    submitButton.click();

    WebElement message = driver.findElement(By.id("message"));

    Assert.assertEquals(message.getText(),"Received!");

    driver.quit();
}
    @Test
    public void testSelenium1() throws InterruptedException{
        WebDriver driver = new ChromeDriver();

        driver.get("https://www.selenium.dev/selenium/web/web-form.html");

        Thread.sleep(1000);

        WebElement textBox = driver.findElement(By.name("my-textarea"));
        WebElement submitButton = driver.findElement(By.cssSelector("button"));

        textBox.sendKeys("Hello world");
        submitButton.click();

        WebElement message = driver.findElement(By.id("message"));

        Assert.assertEquals(message.getText(),"Received!");

        driver.quit();}
}
