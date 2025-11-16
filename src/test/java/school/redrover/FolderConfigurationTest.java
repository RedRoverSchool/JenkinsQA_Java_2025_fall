package school.redrover;

import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.Test;
import school.redrover.common.BaseTest;
import school.redrover.page.ConfigurationFolderPage;
import school.redrover.page.FolderPage;
import school.redrover.page.HomePage;
import java.time.Duration;

public class FolderConfigurationTest extends BaseTest {

   private static final String FOLDER_NAME = "my folder";

    @Test
    public void testHealthMetricLinkIsDisplayed(){
        new HomePage(getDriver())
                .clickCreateJob()
                .sendName(FOLDER_NAME)
                .selectFolderAndSubmit();

        Assert.assertTrue(new ConfigurationFolderPage(getDriver())
                .findHealthMetricsLink()
                .isDisplayed());
    }

    @Test(dependsOnMethods = "testHealthMetricLinkIsDisplayed")
    public void testHealthMetricButtonIsDisplayed(){
        new HomePage(getDriver())
                .openJobPage(FOLDER_NAME, new FolderPage(getDriver()))
                .clickConfigure();

        Assert.assertTrue(new ConfigurationFolderPage(getDriver())
                .findHealthMetricButton()
                .isDisplayed());
    }

    @Test(dependsOnMethods = {"testHealthMetricLinkIsDisplayed"})
    public void testHealthMetricSectionNavigation(){
        getDriver().findElement(By.linkText(FOLDER_NAME)).click();
        getDriver().findElement(By.linkText("Configure")).click();
        getDriver().findElement(By.cssSelector("button.task-link[data-section-id='health-metrics']")).click();

        Assert.assertTrue(getDriver().findElement(By.id("health-metrics")).isDisplayed());
    }

    @Test(dependsOnMethods = {"testHealthMetricSectionNavigation"})
    public void testVerifyMetricTypeList(){
        getDriver().findElement(By.linkText(FOLDER_NAME)).click();
        getDriver().findElement(By.linkText("Configure")).click();
        getDriver().findElement(By.xpath("//button[normalize-space(text())='Health metrics']")).click();
        getDriver().findElement(By.xpath("//button[normalize-space(text())='Add metric']")).click();

        WebDriverWait wait = new WebDriverWait(getDriver(), Duration.ofSeconds(2));
        wait.until(ExpectedConditions
                .visibilityOfElementLocated(By.xpath("//input[@class='jenkins-dropdown__filter-input']")));

        Assert.assertTrue(getDriver()
                .findElement(By.xpath("//button[normalize-space(text())='Child item with the given name']"))
                .isDisplayed());
        Assert.assertTrue(getDriver()
                .findElement(By.xpath("//button[normalize-space(text())='Child item with worst health']"))
                .isDisplayed());
    }
}
