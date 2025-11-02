package school.redrover;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.Test;
import school.redrover.common.BaseTest;

import java.time.Duration;

public class OrganizationFolder1Test extends BaseTest {

    @Test
    public void testCreateOrganizationFolder() {

        final String DISPLAYNAME = "Project 1";

        getDriver().findElement(By.xpath("//a[@href='newJob']")).click();
        getDriver().findElement(By.id("name")).sendKeys("Organization Folder Project");

        WebElement organizationFolder = getDriver()
                .findElement(By.xpath("//li[@class='jenkins_branch_OrganizationFolder']"));
        ((JavascriptExecutor) getDriver())
                .executeScript("arguments[0].scrollIntoView(true);", organizationFolder);
        organizationFolder.click();

        getDriver().findElement(By.id("ok-button")).click();

        getDriver().findElement(By.xpath("//input[@name='_.displayNameOrNull']"))
                .sendKeys(DISPLAYNAME);

        WebElement saveOrganizationFolder = getDriver().findElement(By.xpath("//button[@name='Submit']"));
        ((JavascriptExecutor) getDriver())
                .executeScript("arguments[0].scrollIntoView(true);", saveOrganizationFolder);
        saveOrganizationFolder.click();

        WebDriverWait wait = new WebDriverWait(getDriver(), Duration.ofSeconds(10));
        WebElement resultName = wait.until(
                ExpectedConditions.visibilityOfElementLocated(
                        By.xpath("//h1[@class='job-index-headline page-headline']")
                )
        );

        Assert.assertEquals(resultName.getText(), DISPLAYNAME);
    }
}
