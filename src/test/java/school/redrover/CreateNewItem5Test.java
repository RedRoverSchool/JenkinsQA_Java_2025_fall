package school.redrover;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.Test;
import school.redrover.common.BaseTest;


public class CreateNewItem5Test extends BaseTest {

    @Test
    public void createNewItem () {
        WebElement newItemButton = getDriver().findElement(By.xpath("//*[@id=\"tasks\"]/div[1]/span/a"));
        newItemButton.click();

        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        WebElement checkTextInput = getDriver().findElement(By.id("name"));
        Assert.assertTrue(checkTextInput.isDisplayed(), "The input field is not present on the page");
    }
}
