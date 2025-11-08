package school.redrover;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;
import org.testng.annotations.Test;
import school.redrover.common.BaseTest;

public class CreateFolderTest extends BaseTest {

    @Test
    public void testCreateFolder() {
        final String folder = "Test Folder";

        getDriver().findElement(By.xpath("//a[@href='newJob']"))
                .click();
        getDriver().findElement(By.id("name"))
                .sendKeys("FolderOne");
        getDriver().findElement(By.xpath("//span[text()='Folder']"))
                .click();
        WebElement organizationButton = getWait5().until(
                ExpectedConditions.elementToBeClickable(By.id("ok-button")));
        organizationButton.click();

        WebElement presenceElement = getWait5().until(ExpectedConditions.presenceOfElementLocated(
                By.xpath("//*[@name='_.displayNameOrNull']")));
        presenceElement.sendKeys(folder);

        getDriver().findElement(By.name("Submit"))
                .click();

        WebElement message = getWait5().until(ExpectedConditions.visibilityOfElementLocated(
                By.xpath("//div[@id='main-panel']//h1")));

        Assert.assertEquals(message.getText(), folder);
    }

}