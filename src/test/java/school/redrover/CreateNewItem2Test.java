package school.redrover;

import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.annotations.Test;
import school.redrover.common.BaseTest;

public class CreateNewItem2Test extends BaseTest {

    @Test
    public void createNewItemTest() {
        getDriver().findElement(By.cssSelector("#tasks > :nth-child(1)")).click();
        Assert.assertEquals(getDriver().getTitle(), "New Item - Jenkins");
    }

    @Test
    public void checkWrongCharacter() throws InterruptedException {

        String inputItem = "Test";

        getDriver().findElement(By.cssSelector("#tasks > :nth-child(1)")).click();
        getDriver().findElement(By.id("name")).sendKeys(inputItem);
        Thread.sleep(2000);
        getDriver().findElement(By.cssSelector(".hudson_model_FreeStyleProject")).click();
        Assert.assertFalse(getDriver().findElement(By.id("itemname-required")).isDisplayed());
        Assert.assertFalse(getDriver().findElement(By.id("itemname-invalid")).isDisplayed());

    }

}
