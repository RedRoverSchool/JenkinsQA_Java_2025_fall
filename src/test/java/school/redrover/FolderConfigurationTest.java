package school.redrover;

import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.Test;
import school.redrover.common.BaseTest;
import java.time.Duration;

public class FolderConfigurationTest extends BaseTest {

   private static final String FOLDER_NAME = "my folder";

    @Test
    public void testHealthMetricLinkIsDisplayed(){
        getDriver().findElement(By.xpath("//a[@href='newJob']")).click();
        getDriver().findElement(By.id("name")).sendKeys(FOLDER_NAME);
        getDriver().findElement(By.className("com_cloudbees_hudson_plugins_folder_Folder")).click();
        getDriver().findElement(By.id("ok-button")).click();

        Assert.assertTrue(getDriver()
                .findElement(By.xpath("//span[normalize-space(text())='Health metrics']"))
                .isDisplayed());
    }

    @Test(dependsOnMethods = {"testHealthMetricLinkIsDisplayed"})
    public void testHealthMetricButtonIsDisplayed(){
        getDriver().findElement(By.linkText(FOLDER_NAME)).click();
        getDriver().findElement(By.linkText("Configure")).click();

        Assert.assertTrue(getDriver()
                .findElement(By.xpath("//button[normalize-space(text())='Health metrics']"))
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
