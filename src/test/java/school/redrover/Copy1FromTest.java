package school.redrover;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.Test;
import school.redrover.common.BaseTest;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

public class Copy1FromTest extends BaseTest {

    @Test
    public void testCopyFrom() {
        final String firstJobName = "original_project";
        final String secondJobName = "new_project";

        JavascriptExecutor js = (JavascriptExecutor) getDriver();

        getDriver().findElement(By.xpath("//a[@href='newJob']")).click();
        getDriver().findElement(By.id("name")).sendKeys(firstJobName);
        getDriver().findElement(By.className("hudson_model_FreeStyleProject")).click();
        getDriver().findElement(By.id("ok-button")).click();

        getDriver().findElement(By.name("description")).sendKeys("There is a some description");
        getDriver().findElement(By.xpath("//label[text()='Discard old builds']")).click();
        getDriver().findElement(By.name("_.daysToKeepStr")).sendKeys("10");
        getDriver().findElement(By.name("_.numToKeepStr")).sendKeys("20");
        js.executeScript("window.scrollBy(0, 800);");
        getDriver().findElement(By.xpath("//label[text()='Git']")).click();
        getDriver().findElement(By.name("_.url")).sendKeys("git.com/testUrl");
        js.executeScript("window.scrollBy(0, 800);");
        getDriver().findElement(By.xpath("//label[text()='Trigger builds remotely (e.g., from scripts)']")).click();
        getDriver().findElement(By.name("authToken")).sendKeys("example.com/test");
        getDriver().findElement(By.name("Submit")).click();

        getWait2().until(ExpectedConditions.elementToBeClickable(By.xpath("//span[text()='Jenkins']"))).click();

        getDriver().findElement(By.cssSelector("a[href='/view/all/newJob']")).click();
        getDriver().findElement(By.id("name")).sendKeys(secondJobName);
        getDriver().findElement(By.id("from")).sendKeys(firstJobName);
        getDriver().findElement(By.id("ok-button")).click();
        getDriver().findElement(By.name("Submit")).click();

        List<String> firstItemSettingsList = createSettingsListFromItem(firstJobName);
        List<String> secondItemSettingsList = createSettingsListFromItem(secondJobName);

        Assert.assertEquals(firstItemSettingsList, secondItemSettingsList);
    }

    private List<String> createSettingsListFromItem(String jobName) {
        List<String> settingsList = new ArrayList<>();

        getWait2().until(ExpectedConditions.elementToBeClickable(By.cssSelector("span.jenkins-mobile-hide"))).click();

        getDriver().findElement(By.xpath("//a[@href='job/" + jobName + "/']")).click();
        getDriver().findElement(By.xpath("//a[@href='/job/" + jobName + "/configure']")).click();

        settingsList.add(getDriver().findElement(By.name("description")).getText());
        settingsList.add(getDriver().findElement(By.name("_.daysToKeepStr")).getAttribute("value"));
        settingsList.add(getDriver().findElement(By.name("_.numToKeepStr")).getAttribute("value"));
        JavascriptExecutor js = (JavascriptExecutor) getDriver();
        js.executeScript("window.scrollBy(0, 800);");
        settingsList.add(getDriver().findElement(By.name("_.url")).getAttribute("value"));
        js.executeScript("window.scrollBy(0, 800);");
        settingsList.add(getDriver().findElement(By.name("authToken")).getAttribute("value"));

        return settingsList;
    }
}
