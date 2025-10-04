package school.redrover;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.time.Duration;

public class IliaZagorodniyTest {

    @Test
    public void testSelenium() {
        WebDriver driver = new ChromeDriver();

        driver.get("https://www.selenium.dev/selenium/web/web-form.html");

        driver.manage().timeouts().implicitlyWait(Duration.ofMillis(500));

        WebElement textName = driver.findElement(By.name("my-text"));
        WebElement textPass = driver.findElement(By.name("my-password"));
        WebElement submitButton = driver.findElement(By.cssSelector("button"));

        textName.sendKeys("Selenium");
        textPass.sendKeys("Qwe123");
        submitButton.click();

        WebElement message = driver.findElement(By.id("message"));
        message.getText();

        driver.quit();
    }

    @Test
    public void testGoogle() throws InterruptedException {

        WebDriver driver = new ChromeDriver();

        driver.get("https://www.google.com/");

        WebElement textBox = driver.findElement(By.id("APjFqb"));
        textBox.sendKeys("Selenium");
        Thread.sleep(100);


        WebElement submitButton = driver.findElement(By.name("btnK"));
        submitButton.click();

        WebElement message = driver.findElement(By.cssSelector("#rso > div:nth-child(3) > div > div > div > div > div > div > div > div.yuRUbf > div > span > a > h3"));

        Assert.assertEquals(message.getText(), "Selenium");

        driver.quit();

    }

}
