package school.redrover;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.Test;
import school.redrover.common.BaseTest;

public class CreateNewItem4Test extends BaseTest {

    @Test
    public void testNewItemPageAccessibility() throws InterruptedException {

        getDriver().findElement(By.linkText("New Item")).click();

        Thread.sleep(2000);

        WebElement label = getDriver().findElement(By.xpath("//label[text()='Enter an item name']"));
        WebElement inputField = getDriver().findElement(By.id("name"));

        Assert.assertTrue(label.isDisplayed(), "Label 'Enter an item name' should be displayed");
        Assert.assertTrue(inputField.isDisplayed(), "Input field 'name' should be visible");
    }
}