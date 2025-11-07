package school.redrover;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.Test;
import school.redrover.common.BaseTest;

public class FolderConfigurationTest extends BaseTest {

    public void createMyFolder() {

        final String folderName = "my folder";

        WebElement createJobButton = getDriver().findElement(By.xpath("//a[@href='newJob']"));
        createJobButton.click();
        getDriver().findElement(By.id("name")).sendKeys(folderName);
        getDriver().findElement(By.className("com_cloudbees_hudson_plugins_folder_Folder")).click();
        getDriver().findElement(By.id("ok-button")).click();
    }

    @Test
    public void testHealthMetricVisibility(){
        createMyFolder();
        WebElement healthMetricLink = getDriver()
                .findElement(By.xpath("//span[normalize-space(text())='Health metrics']"));
        Assert.assertTrue(healthMetricLink.isDisplayed());
    }

    @Test
    public void testHealthMetricButton(){
        createMyFolder();
        WebElement healthMetricButton = getDriver()
                .findElement(By.cssSelector("#main-panel section:nth-child(5) button"));
        Assert.assertTrue(healthMetricButton.isDisplayed());
    }
}
