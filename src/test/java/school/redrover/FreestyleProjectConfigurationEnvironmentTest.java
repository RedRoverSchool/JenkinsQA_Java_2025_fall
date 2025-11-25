package school.redrover;

import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;
import org.testng.annotations.Test;
import school.redrover.common.BaseTest;

public class FreestyleProjectConfigurationEnvironmentTest extends BaseTest {

    @Test
    public void testTestAccessAfterRefresh() {

        getDriver().findElement(By.linkText("New Item")).click();
        getDriver().findElement(By.id("name")).sendKeys("NewFreestyleProject");
        getDriver().findElement(By.className("hudson_model_FreeStyleProject")).click();
        getDriver().findElement(By.id("ok-button")).click();
        getDriver().findElement(By.xpath("//button[@data-section-id='environment']")).click();

        String urlBeforeRefresh = getDriver().getCurrentUrl();
        getDriver().navigate().refresh();
        String urlAfterRefresh = getDriver().getCurrentUrl();
        getWait10().until(ExpectedConditions.urlToBe(urlBeforeRefresh));

        Assert.assertEquals(urlAfterRefresh, urlBeforeRefresh);
    }
}