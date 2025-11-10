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

import static org.openqa.selenium.support.ui.ExpectedConditions.visibilityOfElementLocated;

public class CopyFromTest extends BaseTest {
    private static final String MY_FOLDER_NAME = "MyFolder";
    private static final String MY_FOLDER_NAME_COPY = "MyFolderCopy";
    private static final String DISPLAY_NAME_TEXT = "DisplayNameText";
    private static final String DESCRIPTION_TEXT = "DescriptionText";
    private static final String CHILD_HEALTH_METRIC_TEXT = "ChildHealthMetricText";
    private static final String LIBRARY_NAME_TEXT = "LibraryNameText";
    private static final String LIBRARY_VERSION = "main";
    private static final String PROJECT_REPOSITORY = "https://github.com/RedRoverSchool/JenkinsQA_Java_2025_fall";

    @Test
    public void verifySettingsCopiedFromFolderTest() {
        makeFolder();

        getWait2().until(ExpectedConditions.elementToBeClickable(By.xpath("//span[@class='jenkins-mobile-hide']"))).click();
        getDriver().findElement(By.xpath("//a[@href='/view/all/newJob']")).click();
        getDriver().findElement(By.xpath("//input[@name='name']")).sendKeys(MY_FOLDER_NAME_COPY);
        clickWithScroll(getDriver(), By.xpath("//*[@id='from']"));
        getDriver().findElement(By.xpath("//*[@id='from']")).sendKeys(MY_FOLDER_NAME);
        getDriver().findElement(By.id("ok-button")).click();
        getDriver().findElement(By.xpath("//button[@class='jenkins-button advanced-button advancedButton']")).click();

        Assert.assertEquals(getDriver().findElement(By.xpath("//input[@name='_.displayNameOrNull']")).getAttribute("value"), "", "Display name should be empty in copy");
        Assert.assertEquals(getDriver().findElement(By.xpath("//textarea[@name='_.description']")).getAttribute("value"), DESCRIPTION_TEXT, "Description should match original");
        Assert.assertEquals(getDriver().findElement(By.xpath("//input[@class='jenkins-input  auto-complete ']")).getAttribute("value"), CHILD_HEALTH_METRIC_TEXT, "ChildHealthMetricText should match original");
        Assert.assertEquals(getDriver().findElement(By.xpath("//input[@name='_.name']")).getAttribute("value"), LIBRARY_NAME_TEXT, "LibraryName should match original");
        Assert.assertEquals(getDriver().findElement(By.xpath("//input[@name='_.defaultVersion']")).getAttribute("value"), LIBRARY_VERSION, "LibraryVersion should match original");
        Assert.assertEquals(getDriver().findElement(By.xpath("//input[@checkdependson='credentialsId remote']")).getAttribute("value"), PROJECT_REPOSITORY, "ProjectRepository should match original");

        Assert.assertTrue(getDriver().findElement(By.xpath("//input[@name='_.implicit']")).isSelected(), "Checkbox 'Load implicitly' must be checked");
        Assert.assertFalse(getDriver().findElement(By.xpath("//input[@name='_.allowVersionOverride']")).isSelected(), "Checkbox 'Allow default version to be overridden' must be unchecked");
        Assert.assertFalse(getDriver().findElement(By.xpath("//input[@name='_.includeInChangesets']")).isSelected(), "Checkbox 'Include @Library changes in job recent changes' must be unchecked");
        Assert.assertTrue(getDriver().findElement(By.xpath("//*[@id='cb0']")).isSelected(), "Checkbox 'Cache fetched versions on controller for quick retrieval' must be checked");
        Assert.assertTrue(getDriver().findElement(By.xpath("//input[@name='_.clone']")).isSelected(), "Checkbox 'Fresh clone per build' must be checked");
    }

    @Test(dependsOnMethods = "verifySettingsCopiedFromFolderTest")
    public void verifyCopiedSettingsAreEditable() {
        getWait2().until(ExpectedConditions.elementToBeClickable(
                By.xpath("//a[@href='job/MyFolderCopy/'][@class='jenkins-table__link model-link inside']"))).click();
        getWait10().until(visibilityOfElementLocated(By.cssSelector("a[href$='configure']"))).click();

        getDriver().findElement(By.xpath("//input[@name='_.displayNameOrNull']")).sendKeys(DISPLAY_NAME_TEXT + "2");
        getDriver().findElement(By.xpath("//textarea[@name='_.description']")).sendKeys(DESCRIPTION_TEXT + "2");
        getDriver().findElement(By.xpath("//input[@checkdependson='name']")).sendKeys(CHILD_HEALTH_METRIC_TEXT + "2");
        getDriver().findElement(By.xpath("//input[@name='_.name']")).sendKeys(LIBRARY_NAME_TEXT + "2");
        getDriver().findElement(By.xpath("//input[@name='_.defaultVersion']")).sendKeys(LIBRARY_VERSION + "2");
        clickWithScroll(getDriver(), (By.xpath("//span[contains(text(), 'Load implicitly')]")));
        getDriver().findElement(By.xpath("//span[contains(text(), 'Allow default version to be overridden')]")).click();
        getDriver().findElement(By.xpath("//span[contains(text(), 'Include @Library changes in job recent changes')]")).click();
        getDriver().findElement(By.xpath("//label[contains(text(), 'Cache fetched versions on controller for quick retrieval')]")).click();
        getDriver().findElement(By.xpath("//input[@checkdependson='credentialsId remote']")).sendKeys(PROJECT_REPOSITORY + "/2");
        clickWithScroll(getDriver(), (By.xpath("//span[contains(text(), 'Fresh clone per build')]")));
        getDriver().findElement(By.xpath("//button[@name='Submit']")).click();

        WebElement newName = getWait5().until(ExpectedConditions.visibilityOfElementLocated(
                By.xpath("//h1[@class='job-index-headline page-headline']")));
        Assert.assertEquals(newName.getText(), DISPLAY_NAME_TEXT + "2");
    }

    private void makeFolder() {
        WebDriver driver = getDriver();
        driver.findElement(By.xpath("//a[@href='/view/all/newJob']")).click();
        driver.findElement(By.xpath("//input[@name='name']")).sendKeys("MyFolder");
        driver.findElement(By.xpath("//li[@class='com_cloudbees_hudson_plugins_folder_Folder']")).click();
        driver.findElement(By.id("ok-button")).click();

        driver.findElement(By.xpath("//input[@name='_.displayNameOrNull']")).sendKeys(DISPLAY_NAME_TEXT);
        driver.findElement(By.xpath("//textarea[@name='_.description']")).sendKeys(DESCRIPTION_TEXT);
        driver.findElement(By.xpath("//button[@class='jenkins-button advanced-button advancedButton']")).click();
        driver.findElement(By.xpath("//button[@class='jenkins-button hetero-list-add']")).click();
        driver.findElement(By.xpath("//button[@class='jenkins-dropdown__item '][1]")).click();
        driver.findElement(By.xpath("//input[@class='jenkins-input  auto-complete ']")).sendKeys(CHILD_HEALTH_METRIC_TEXT);

        clickWithScroll(driver, By.xpath("//button[@class='jenkins-button repeatable-add']"));
        driver.findElement(By.xpath("//input[@name='_.name']")).sendKeys(LIBRARY_NAME_TEXT);
        driver.findElement(By.xpath("//input[@name='_.defaultVersion']")).sendKeys(LIBRARY_VERSION);

        clickWithScroll(driver, (By.xpath("//span[contains(text(), 'Load implicitly')]")));
        driver.findElement(By.xpath("//span[contains(text(), 'Allow default version to be overridden')]")).click();
        driver.findElement(By.xpath("//span[contains(text(), 'Include @Library changes in job recent changes')]")).click();
        driver.findElement(By.xpath("//label[contains(text(), 'Cache fetched versions on controller for quick retrieval')]")).click();
        driver.findElement(By.xpath("//input[@checkdependson='credentialsId remote']")).sendKeys(PROJECT_REPOSITORY);
        clickWithScroll(driver, (By.xpath("//span[contains(text(), 'Fresh clone per build')]")));

        driver.findElement(By.xpath("//button[@name='Submit']")).click();
    }

    private void clickWithScroll(WebDriver driver, By locator) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
        WebElement element = wait.until(ExpectedConditions.presenceOfElementLocated(locator));
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", element);
        wait.until(ExpectedConditions.elementToBeClickable(element)).click();
    }
}
