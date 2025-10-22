package school.redrover;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.Test;

public class AssyaTest {

    @Test
    public void TestfullName() {
        WebDriver driver = new ChromeDriver();

        driver.get("https://demoqa.com/text-box");

        WebElement textbox = driver.findElement(By.id("userName"));
        textbox.sendKeys("Assya");
        WebElement submitButton = driver.findElement(By.cssSelector("button#submit"));
        submitButton.click();
        WebElement massage = driver.findElement(By.id("name"));

        Assert.assertEquals(massage.getText(), "Name:Assya");

        driver.quit();

    }
}


