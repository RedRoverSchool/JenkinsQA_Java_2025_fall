package school.redrover;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.Test;
import school.redrover.common.BaseTest;
import school.redrover.page.HomePage;

import java.time.Duration;
import java.util.List;


public class CreateNewItemTest extends BaseTest {

    @Test
    public void testNewItemPageByClickingCreateAJobLink() {
        String result = new HomePage(getDriver())
                .clickCreateJob()
                .getHeadingText();

        Assert.assertEquals(result, "New Item");
    }

    @Test
    public void testNewItemPageByClickingNewItemLink() {
        String result = new HomePage(getDriver())
                .clickNewItemOnLeftMenu()
                .getHeadingText();

        Assert.assertEquals(result, "New Item");
    }

    @Test
    public void testEnterAnItemNameIsDisplayedOkButtonIdDisabled() {
          Boolean result = new HomePage(getDriver())
                  .clickCreateJob()
                  .isOkButtonEnabled();

          Assert.assertFalse(result);
    }

    @Test
    public void testNewItemTypesAccessibility() {
        final List<String> expectedItemTypes = List.of(
                "Freestyle project",
                "Pipeline",
                "Multi-configuration project",
                "Folder",
                "Multibranch Pipeline",
                "Organization Folder"
        );

        WebElement newItemButton = new WebDriverWait(getDriver(), Duration.ofSeconds(5))
                .until(ExpectedConditions.visibilityOfElementLocated(By.xpath(".//div[@id='tasks']/div[1]/span/a")));
        newItemButton.click();

        List<String> actualTypeList = getDriver()
                .findElements(By.xpath(".//span[@class='label']"))
                .stream()
                .map(WebElement::getText)
                .toList();

        Assert.assertEquals(actualTypeList, expectedItemTypes);
    }

    @Test
    public void testErrorMessageForDuplicateItemNames() throws InterruptedException {
        final String jobName = "AS new job";
        final String errorMessage = "itemname-invalid";

        getDriver().findElement(By.xpath("//span[text()='Create a job']")).click();

        getWait5().until(ExpectedConditions.elementToBeClickable(getDriver().findElement(By.id("name")))).sendKeys(jobName);
        getDriver().findElement(By.className("hudson_model_FreeStyleProject")).click();
        getDriver().findElement(By.id("ok-button")).click();

        getWait5().until(ExpectedConditions.elementToBeClickable(By.xpath("//button[@name='Submit']"))).click();
        getWait5().until(ExpectedConditions.elementToBeClickable(By.xpath("//span[@class='jenkins-mobile-hide']"))).click();
        getWait5().until(ExpectedConditions.elementToBeClickable(By.xpath("//a[@href='/view/all/newJob']"))).click();
        getWait5().until(ExpectedConditions.elementToBeClickable(By.id("name"))).sendKeys(jobName);
        getDriver().findElement(By.className("hudson_model_FreeStyleProject")).click();

        getWait5().until(ExpectedConditions.visibilityOfElementLocated(By.id(errorMessage)));
        Assert.assertEquals(getDriver().findElement(By.id(errorMessage)).getText(), "» A job already exists with the name ‘AS new job’");
    }

    @Test
    public void testAcceptsAlphanumericAndUnderscores() {
        final String projectName = "test_name1";

        List<String> projectList = new HomePage(getDriver())
                .clickCreateJob()
                .sendName(projectName)
                .selectPipelineAndSubmit()
                .gotoHomePage()
                .getProjectList();

        Assert.assertNotEquals(projectList.size(), 0);
        Assert.assertEquals(projectList.get(0), projectName);
    }
}

