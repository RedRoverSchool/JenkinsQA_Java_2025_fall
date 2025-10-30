package school.redrover;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.Test;
import school.redrover.common.BaseTest;

public class CreateNewItem1Test extends BaseTest {

    @Test
    public void testItemsAreDisplayed() {

        String[] expectedItemsList = {
                "Freestyle project",
                "Pipeline",
                "Multi-configuration project",
                "Folder",
                "Multibranch Pipeline",
                "Organization Folder"
        };

        String[] actualItemsList = new String[expectedItemsList.length];

        getDriver().findElement(By.xpath("//span[text()='Create a job']")).click();

        for (int i = 0; i < expectedItemsList.length; i++) {
            actualItemsList[i] = getDriver().findElement(By.xpath("//span[text()='%s']".formatted(expectedItemsList[i]))).getText();
        }

        Assert.assertEquals(actualItemsList,expectedItemsList);
    }

    @Test
    public void testInvalidItemNameField() {

        String invalidChars = " !@#$%^&*[]|\\;:<>?/";

        getDriver().findElement(By.xpath("//span[text()='Create a job']")).click();

        WebElement nameInput = getDriver().findElement(By.id("name"));

        for (char ch : invalidChars.toCharArray()) {
            nameInput.clear();
            nameInput.sendKeys("" + ch);
            String dataValid = nameInput.getAttribute("data-valid");

            Assert.assertEquals(dataValid, "false",
                    "Character '" + ch + "' should not be allowed");
        }
    }
}
