package school.redrover;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.time.Duration;

public class GroupFutureJavaExpertsTest {
    @Test
    public void testCheckBox(){
        WebDriver driver = new FirefoxDriver();
        driver.manage().window().maximize();
        driver.get("https://demoqa.com/checkbox");
        driver.manage().timeouts().implicitlyWait(Duration.ofMillis(500));
        driver.findElement(By.cssSelector(".rct-checkbox svg")).click();

        WebElement resultMSG = driver.findElement(By.id("result"));
        Assert.assertTrue(resultMSG.isDisplayed());
        driver.quit();
    }
}
