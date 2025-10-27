package school.redrover;

import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.annotations.Test;
import school.redrover.common.BaseTest;

public class NewJobPageTest extends BaseTest {

    @Test
    public  void testItemNameEmpty(){

        getDriver().findElement(By.cssSelector("#tasks a")).click();
        getDriver().findElement((By.cssSelector("[class$='OrganizationFolder']"))).click();

        Assert.assertEquals(getDriver().findElement(By.id("itemname-required")).getText(), "» This field cannot be empty, please enter a valid name");
        Assert.assertFalse(getDriver().findElement(By.id("ok-button")).isEnabled());

    }
}
