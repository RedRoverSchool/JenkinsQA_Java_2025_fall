package school.redrover;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.Test;
import school.redrover.common.BaseTest;

import java.time.Duration;

public class OrganisationFolderTest extends BaseTest {

    @Test
    public void testOrganisationFolderNameAndDescription() throws InterruptedException{

        final String DESCRIPTION = "Today I do more work for class project";

        getDriver().findElement(By.xpath("//a[@href='newJob']")).click();

        getDriver().findElement(By.xpath("//*[@id='name']")).sendKeys("WorksData");

        WebElement organizationFolder = getDriver()
                .findElement(By.xpath("//li[@class='jenkins_branch_OrganizationFolder']"));
        ((JavascriptExecutor) getDriver())
                .executeScript("arguments[0].scrollIntoView(true);", organizationFolder);
        organizationFolder.click();

        WebElement buttonOK = getDriver().findElement(By.xpath("//*[@id='ok-button']"));
        ((JavascriptExecutor) getDriver()).executeScript("arguments[0].scrollIntoView(true);", buttonOK);
        buttonOK.click();

        WebElement displayName = getDriver().findElement(By.xpath("//input[@name='_.displayNameOrNull']"));
        displayName.sendKeys("29.10.2025");

        WebElement descriptionText = getDriver().findElement(By.xpath("//textarea[@name='_.description']"));
        descriptionText.sendKeys(DESCRIPTION);

        WebElement saveButton = getDriver().findElement(By.xpath("//button[@name='Submit']"));
        saveButton.click();

        WebDriverWait wait = new WebDriverWait(getDriver(), Duration.ofSeconds(10));

        WebElement message = wait.until(
                ExpectedConditions.visibilityOfElementLocated(
                        By.xpath("//div[@id='view-message']")
                )
        );

        Assert.assertEquals(message.getText(), DESCRIPTION);
    }
}
