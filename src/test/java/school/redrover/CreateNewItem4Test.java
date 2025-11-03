package school.redrover;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.Test;
import school.redrover.common.BaseTest;

import java.util.List;

public class CreateNewItem4Test extends BaseTest {

    @Test
    public void testNewItemPageAccessibility() throws InterruptedException {

        getDriver().findElement(By.linkText("New Item")).click();

        WebElement label = getDriver().findElement(By.xpath("//label[text()='Enter an item name']"));
        WebElement inputField = getDriver().findElement(By.id("name"));

        Assert.assertTrue(label.isDisplayed(), "Label 'Enter an item name' should be displayed");
        Assert.assertTrue(inputField.isDisplayed(), "Input field 'name' should be visible");
    }

    @Test
    public void itemNameInput() {

        getDriver().findElement(By.linkText("New Item")).click();
        getDriver().findElement(By.xpath("//*[@id='name']")).sendKeys("Uliana_123");

        List<WebElement> validationMessages = getDriver().findElements(By.className("input-validation-message"));

        boolean allValidationMessagesDisabled = true;

        for (WebElement message : validationMessages) {
            if (!message.getAttribute("class").contains("input-message-disabled")) {
                allValidationMessagesDisabled = false;
            }
        }
        Assert.assertTrue(allValidationMessagesDisabled,
                "All validation messages should be disabled for valid input");
    }
}