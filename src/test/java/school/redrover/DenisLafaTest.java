package school.redrover;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.Test;

public class DenisLafaTest {

    @Test
    public void testFullNameBox() {
        WebDriver driver = new ChromeDriver();
        driver.get("https://demoqa.com/text-box");

        WebElement inputText = driver.findElement(By.id("userName"));
        inputText.sendKeys("Denis");
        WebElement submitButton  = driver.findElement(By.id("submit"));
        submitButton.click();
        WebElement outputText = driver.findElement(By.id("name"));

        String actResultStr = outputText.getText();
        String expResultStr = "Name:Denis";

        Assert.assertEquals(actResultStr, expResultStr);
        driver.quit();
    }
}

