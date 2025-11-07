package school.redrover;

import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.Test;
import school.redrover.common.BaseTest;

import java.time.Duration;

public class ConfigurationMatrix2Test extends BaseTest {
    @Test (testName = "TC_02.005.04 | Freestyle Project Configuration " +
            "> Set up Environment " +
            "> Verify the access to configuration page after refresh")
    public void testSetUpEnvironment() {

        WebDriverWait wait = new WebDriverWait(getDriver(), Duration.ofSeconds(10));
        String expectedUrl =  "http://localhost:8080/job/NewFreestyleProject/configure";

        getDriver().findElement(By.linkText("New Item")).click();
        getDriver().findElement(By.id("name")).sendKeys("NewFreestyleProject");
        getDriver().findElement(By.className("hudson_model_FreeStyleProject")).click();
        getDriver().findElement(By.id("ok-button")).click();
        getDriver().findElement(By.xpath("//button[@data-section-id='environment']")).click();
        getDriver().navigate().refresh();

        String urlAfterRefresh = getDriver().getCurrentUrl();

        wait.until(ExpectedConditions.urlToBe(expectedUrl));
        Assert.assertEquals(urlAfterRefresh, expectedUrl);
    }
}
