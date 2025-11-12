package school.redrover;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;
import org.testng.annotations.Test;
import school.redrover.common.BaseTest;

public class FolderSettingsTest extends BaseTest {

    private static final String FOLDER_NAME = "Just folder";
    private static final String DISPLAY_NAME = "Look at this";

    @Test
    public void testDisplayName() {
        getDriver().findElement(By.cssSelector("[href='newJob']")).click();

        getDriver().findElement(By.name("name")).sendKeys(FOLDER_NAME);
        getDriver().findElement(By.className("com_cloudbees_hudson_plugins_folder_Folder")).click();
        getDriver().findElement(By.cssSelector("button[type='submit']")).click();

        getDriver().findElement(By.name("_.displayNameOrNull")).sendKeys(DISPLAY_NAME);
        getDriver().findElement(By.name("Submit")).click();

        getWait5().until(ExpectedConditions.elementToBeClickable(
                getDriver().findElement(By.className("app-jenkins-logo")))).click();

        WebElement project = getDriver().findElement(By.xpath("//tr[@id='job_%s']/td[3]".formatted(FOLDER_NAME)));
        Assert.assertEquals(project.getText(), DISPLAY_NAME);
    }
}
