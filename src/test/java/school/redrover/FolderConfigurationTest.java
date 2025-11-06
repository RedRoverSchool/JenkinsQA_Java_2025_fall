package school.redrover;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.Test;
import school.redrover.common.BaseTest;

public class FolderConfigurationTest extends BaseTest {
    final String folderName = "my folder";

    @Test
    public void testHealthMetricVisibility() throws InterruptedException {
        WebElement createJobButton = getDriver().findElement(By.xpath("//a[@href='newJob']"));
        createJobButton.click();

        getDriver().findElement(By.id("name")).sendKeys(folderName);
        getDriver().findElement(By.className("com_cloudbees_hudson_plugins_folder_Folder")).click();
        getDriver().findElement(By.id("ok-button")).click();

        Thread.sleep(2000);

        WebElement healthMetricLink = getDriver().findElement(By.xpath("//*[@id='tasks']/div[2]//span[2]"));
        Assert.assertEquals(healthMetricLink.getText(), "Health metrics");
    }
}
