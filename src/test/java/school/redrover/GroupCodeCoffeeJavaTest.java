package school.redrover;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.Test;

public class GroupCodeCoffeeJavaTest {
    @Test
    public void testDemoQa() throws InterruptedException {
        WebDriver driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.get("https://demoqa.com/text-box");
        WebElement fullName = driver.findElement(By.id("userName"));
        fullName.sendKeys("Алиса Т.");

        Thread.sleep(1000);
        WebElement email = driver.findElement(By.cssSelector("#userEmail"));
        email.sendKeys("Alisya152@gmail.com");

        Thread.sleep(3000);
        WebElement submitButton = driver.findElement(By.cssSelector("#submit"));
        submitButton.click();

        WebElement resultName = driver.findElement(By.cssSelector("#name"));
        WebElement resultEmail = driver.findElement(By.cssSelector("#email"));

        Thread.sleep(3000);
        // Проверяем правильность текста
        Assert.assertEquals(resultName.getText(), "Name:Алиса Т.");
        Assert.assertEquals(resultEmail.getText(), "Email:Alisya152@gmail.com");

        Thread.sleep(3000);
        driver.close();
    }
}
