package school.redrover;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.time.Duration;

public class DenisGorbachevTest {
    @Test
    public void DenisTest1() {

        WebDriver driver = new ChromeDriver();
        driver.get("https://www.selenium.dev/selenium/web/web-form.html");
        driver.getTitle();
        driver.manage().timeouts().implicitlyWait(Duration.ofMillis(500));
        WebElement textBox = driver.findElement(By.xpath("//textarea[@name='my-textarea']"));
        WebElement submitButton = driver.findElement(By.xpath("//button"));
        textBox.sendKeys("Selenium Test");
        submitButton.click();
        WebElement message = driver.findElement(By.id("message"));
        Assert.assertEquals(message.getText(), "Received!");
        driver.quit();
    }

    @Test
    public void DenisTest2() {
        WebDriver driver = new ChromeDriver();

        driver.get("https://habr.com/");
        driver.getTitle();
        driver.manage().timeouts().implicitlyWait(Duration.ofMillis(500));
        WebElement searchButton = driver.findElement(By.cssSelector("[class*='tm-header-user-menu__search']"));
        searchButton.click();
        WebElement textBox = driver.findElement(By.cssSelector("[class*='tm-search__input tm-input-text-decorated__input']"));
        WebElement submitButton = driver.findElement(By.cssSelector("[class*='tm-search__icon']"));
        textBox.sendKeys("Selenium Test");
        submitButton.click();
        WebElement message = driver.findElement(By.className("searched-item"));
        Assert.assertEquals(message.getText(), "Selenium Tests");
        driver.quit();
    }
}
