package school.redrover;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.Test;

public class GoogleTest {

    @Test
    public void testGoogle() throws InterruptedException {
        WebDriver driver = new ChromeDriver();
        driver.get("https://www.google.com");

        WebElement textBox = driver.findElement(By.id("APjFqb"));
        textBox.sendKeys("selenium");

        Thread.sleep(1000);

        WebElement submitButton = driver.findElement(By.name("btnK"));
        submitButton.click();

        WebElement message = driver.findElement(By.cssSelector("div.A6K0A:nth-child(6) > div:nth-child(1) > div:nth-child(1) > div:nth-child(1) > div:nth-child(1) > div:nth-child(1) > span:nth-child(1) > a:nth-child(1) > h3:nth-child(1)"));

        Assert.assertEquals(message.getText(), "Selenium");

        driver.quit();
    }
}
