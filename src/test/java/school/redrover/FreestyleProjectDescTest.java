package school.redrover;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.Test;
import school.redrover.common.BaseTest;

public class FreestyleProjectDescTest extends BaseTest {

    @Test
    public void testCreateFreestyleProjectWithDesc() {
        String description = "This is a description for Freestyle Project";
        WebDriver driver = getDriver();

        driver.findElement(By.xpath("//span[text()='Create a job']")).click();

        driver.findElement(By.id("name")).sendKeys("KM-Job1");
        driver.findElement(By.xpath("//span[text()='Freestyle project']")).click();
        driver.findElement(By.id("ok-button")).click();

        driver.findElement(By.name("description")).sendKeys(description);
        driver.findElement(By.name("Submit")).click();

        WebElement actualDesc = driver.findElement(By.id("description-content"));
        Assert.assertEquals(actualDesc.getText(), description);
    }
}
