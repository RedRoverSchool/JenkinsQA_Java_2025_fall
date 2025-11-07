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

public class CopyFromTest extends BaseTest {
    final String myFolderName = "MyFolder";
    final String myFolderNameCopy = "MyFolderCopy";
    final String displayNameText = "DisplayNameText";
    final String descriptionText = "DescriptionText";
    final String childHealthMetricText = "ChildHealthMetricText";
    final String libraryNameText = "LibraryNameText";
    final String libraryVersion = "main";
    final String projectRepository = "https://github.com/RedRoverSchool/JenkinsQA_Java_2025_fall";

    @Test
    public void verifySettingsCopiedFromFolderTest() throws InterruptedException {
        // Acceptance Criteria: 3. All settings (checkboxes, fields, links, scripts, parameters) are copied from the existing item.

        makeFolder();
        Thread.sleep(500);
        // Open "New Item"
        getDriver().findElement(By.xpath("//span[@class='jenkins-mobile-hide']")).click();
        getDriver().findElement(By.xpath("//a[@href='/view/all/newJob']")).click();
        // Copy existing folder
        getDriver().findElement(By.xpath("//input[@name='name']")).sendKeys(myFolderNameCopy);
        clickWithScroll(getDriver(), By.xpath("//*[@id='from']"));
        getDriver().findElement(By.xpath("//*[@id='from']")).sendKeys(myFolderName);
        getDriver().findElement(By.id("ok-button")).click();
        getDriver().findElement(By.xpath("//button[@class='jenkins-button advanced-button advancedButton']")).click();
        // Verify settings
        Assert.assertEquals(getDriver().findElement(By.xpath("//input[@name='_.displayNameOrNull']")).getAttribute("value"), "", "Display name should be empty in copy");
        Assert.assertEquals(getDriver().findElement(By.xpath("//textarea[@name='_.description']")).getAttribute("value"), descriptionText, "Description should match original");
        Assert.assertEquals(getDriver().findElement(By.xpath("//input[@class='jenkins-input  auto-complete ']")).getAttribute("value"), childHealthMetricText, "ChildHealthMetricText should match original");
        Assert.assertEquals(getDriver().findElement(By.xpath("//input[@name='_.name']")).getAttribute("value"), libraryNameText, "LibraryName should match original");
        Assert.assertEquals(getDriver().findElement(By.xpath("//input[@name='_.defaultVersion']")).getAttribute("value"), libraryVersion, "LibraryVersion should match original");
        Assert.assertEquals(getDriver().findElement(By.xpath("//input[@checkdependson='credentialsId remote']")).getAttribute("value"), projectRepository, "ProjectRepository should match original");

        Assert.assertTrue(getDriver().findElement(By.xpath("//input[@name='_.implicit']")).isSelected(), "Checkbox 'Load implicitly' must be checked");
        Assert.assertFalse(getDriver().findElement(By.xpath("//input[@name='_.allowVersionOverride']")).isSelected(), "Checkbox 'Allow default version to be overridden' must be unchecked");
        Assert.assertFalse(getDriver().findElement(By.xpath("//input[@name='_.includeInChangesets']")).isSelected(), "Checkbox 'Include @Library changes in job recent changes' must be unchecked");
        Assert.assertTrue(getDriver().findElement(By.xpath("//*[@id='cb0']")).isSelected(), "Checkbox 'Cache fetched versions on controller for quick retrieval' must be checked");
        Assert.assertTrue(getDriver().findElement(By.xpath("//input[@name='_.clone']")).isSelected(), "Checkbox 'Fresh clone per build' must be checked");
    }

    private void makeFolder() {
        WebDriver driver = getDriver();
        driver.findElement(By.xpath("//a[@href='/view/all/newJob']")).click();
        driver.findElement(By.xpath("//input[@name='name']")).sendKeys("MyFolder");
        driver.findElement(By.xpath("//li[@class='com_cloudbees_hudson_plugins_folder_Folder']")).click();
        driver.findElement(By.id("ok-button")).click();

        driver.findElement(By.xpath("//input[@name='_.displayNameOrNull']")).sendKeys(displayNameText);
        driver.findElement(By.xpath("//textarea[@name='_.description']")).sendKeys(descriptionText);
        driver.findElement(By.xpath("//button[@class='jenkins-button advanced-button advancedButton']")).click();
        driver.findElement(By.xpath("//button[@class='jenkins-button hetero-list-add']")).click();
        driver.findElement(By.xpath("//button[@class='jenkins-dropdown__item '][1]")).click();
        driver.findElement(By.xpath("//input[@class='jenkins-input  auto-complete ']")).sendKeys(childHealthMetricText);

        clickWithScroll(driver, By.xpath("//button[@class='jenkins-button repeatable-add']"));
        driver.findElement(By.xpath("//input[@name='_.name']")).sendKeys(libraryNameText);
        driver.findElement(By.xpath("//input[@name='_.defaultVersion']")).sendKeys(libraryVersion);

        clickWithScroll(driver, (By.xpath("//span[contains(text(), 'Load implicitly')]")));
        driver.findElement(By.xpath("//span[contains(text(), 'Allow default version to be overridden')]")).click();
        driver.findElement(By.xpath("//span[contains(text(), 'Include @Library changes in job recent changes')]")).click();
        driver.findElement(By.xpath("//label[contains(text(), 'Cache fetched versions on controller for quick retrieval')]")).click();
        driver.findElement(By.xpath("//input[@checkdependson='credentialsId remote']")).sendKeys(projectRepository);
        clickWithScroll(driver, (By.xpath("//span[contains(text(), 'Fresh clone per build')]")));

        driver.findElement(By.xpath("//button[@name='Submit']")).click();
    }

    private void clickWithScroll(WebDriver driver, By locator) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        WebElement element = wait.until(ExpectedConditions.presenceOfElementLocated(locator));
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", element);
        wait.until(ExpectedConditions.elementToBeClickable(element)).click();
    }
}
