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
}
