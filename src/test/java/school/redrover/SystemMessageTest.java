package school.redrover;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.Test;
import school.redrover.common.BaseTest;

import java.time.Duration;

public class SystemMessageTest extends BaseTest {

    @Test
    public void testCreateMessage() {
        final String expectedMessage = "This is the best project!";

        getDriver().findElement(By.id("root-action-ManageJenkinsAction")).click();
        getDriver().findElement(By.xpath("//a[@href='configure']")).click();

        WebElement input = getDriver().findElement(By.name("system_message"));
        input.clear();
        input.sendKeys(expectedMessage);
        getDriver().findElement(By.name("Submit")).click();

        String actualMessage = new WebDriverWait(getDriver(), Duration.ofSeconds(10))
                .until(ExpectedConditions.visibilityOfElementLocated(
                        By.id("systemmessage"))).getText();

        Assert.assertEquals(actualMessage, expectedMessage);

    }
}
