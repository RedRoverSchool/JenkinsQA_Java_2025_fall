package school.redrover;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.Test;
import school.redrover.common.BaseTest;


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

}

