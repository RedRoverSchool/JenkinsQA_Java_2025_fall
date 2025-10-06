package school.redrover;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.Test;

public class RinatTest {
    @Test
    public void testLoginWithValidCredentials() {
        WebDriver driver = new ChromeDriver();

        driver.get("https://www.automationexercise.com/");

        driver.findElement(By.xpath("//button[@aria-label='Соглашаюсь']")).click();

        Assert.assertEquals(driver.getTitle(), "Automation Exercise");

        driver.findElement(By.xpath("//a[@href='/login']")).click();

        Assert.assertTrue(driver.findElement(By.xpath("//h2[text()='Login to your account']")).isDisplayed());

        driver.quit();

    }
}
