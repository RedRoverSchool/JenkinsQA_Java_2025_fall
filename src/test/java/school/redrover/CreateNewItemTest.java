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
    public void testNewItemPageByClickingCreateAJobLink() throws InterruptedException {
        getDriver().findElement(By.xpath("//span[text()='Create a job']")).click();

        Assert.assertEquals(getDriver().findElement(By.tagName("h1")).getText(), "New Item");
    }

    @Test
    public void testNewItemPageByClickingNewItemLink() throws InterruptedException {
        getDriver().findElement(By.xpath("//a[@href='/view/all/newJob']")).click();

        Assert.assertEquals(getDriver().findElement(By.tagName("h1")).getText(), "New Item");
    }

    @Test
    public void testEnterAnItemNameIsDisplayedOkButtonIdDisabled() throws InterruptedException {
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

}

