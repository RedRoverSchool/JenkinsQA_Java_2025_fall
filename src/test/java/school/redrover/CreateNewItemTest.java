package school.redrover;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.Test;
import school.redrover.common.BaseTest;

import java.time.Duration;
import java.util.List;


public class CreateNewItemTest extends BaseTest {

    @Test
    //TC01-001-01
    public void testNewItemPageByClickingCreateAJobLink() {
        getDriver().findElement(By.xpath("//span[text()='Create a job']")).click();

        Assert.assertEquals(getDriver().findElement(By.tagName("h1")).getText(), "New Item");
    }

    @Test
    //TC01-001-01
    public void testNewItemPageByClickingNewItemLink() {
        getDriver().findElement(By.xpath("//a[@href='/view/all/newJob']")).click();

        Assert.assertEquals(getDriver().findElement(By.tagName("h1")).getText(), "New Item");
    }

    @Test
    //TC01-001-01
    public void testEnterAnItemNameIsDisplayedOkButtonIdDisabled() {
        getDriver().findElement(By.xpath("//span[text()='Create a job']")).click();

        WebElement okButton = getDriver().findElement(By.id("ok-button"));
        String isDisabled = okButton.getAttribute("disabled");

        Assert.assertEquals(getDriver().findElement(By.className("jenkins-form-label")).getText(), "Enter an item name");
        Assert.assertNotNull(isDisabled, "true");
    }

    @Test
    public void testNewItemTypesAccessibility() {
        WebElement newItemButton = new WebDriverWait(getDriver(), Duration.ofSeconds(5))
                .until(ExpectedConditions.visibilityOfElementLocated(By.xpath(".//div[@id='tasks']/div[1]/span/a")));

        newItemButton.click();

        List<String> expectedItemTypes = List.of("Freestyle project", "Pipeline", "Multi-configuration project", "Folder", "Multibranch Pipeline", "Organization Folder");
        List<WebElement> itemTypes = getDriver().findElements(By.xpath(".//span[@class='label']"));

        List<String> itemTypeList = itemTypes.stream()
                .map(WebElement::getText)
                .toList();

        Assert.assertEquals(itemTypeList, expectedItemTypes);
    }

    @Test
    //TC 01-001-06
    public void testErrorMessageForDuplicateItemNames() throws InterruptedException {
        WebDriverWait wait = new WebDriverWait(getDriver(), Duration.ofSeconds(5));
        final String jobName = "AS new job";

        getDriver().findElement(By.xpath("//span[text()='Create a job']")).click();

        wait.until(ExpectedConditions.elementToBeClickable(getDriver().findElement(By.id("name")))).sendKeys(jobName);
        getDriver().findElement(By.className("hudson_model_FreeStyleProject")).click();
        getDriver().findElement(By.id("ok-button")).click();

        wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//button[@name='Submit']"))).click();

        wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//span[@class='jenkins-mobile-hide']"))).click();

        wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//a[@href='/view/all/newJob']"))).click();

        wait.until(ExpectedConditions.elementToBeClickable(By.id("name"))).sendKeys(jobName);

        getDriver().findElement(By.className("hudson_model_FreeStyleProject")).click();

        final String errorMessage = "itemname-invalid";

        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id(errorMessage)));

        Assert.assertEquals(getDriver().findElement(By.id(errorMessage)).getText(), "» A job already exists with the name ‘AS new job’");
    }
}

