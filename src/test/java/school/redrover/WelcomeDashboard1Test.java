package school.redrover;

import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.annotations.Test;
import school.redrover.common.BaseTest;

public class WelcomeDashboard1Test extends BaseTest {
    @Test
    public void testWelcomeDashboardSetUtAgent() {
        getDriver().findElement(By.xpath("//a[@href='computer/new']")).click();

        Assert.assertEquals(
                getDriver().findElement(By.xpath("//h1")).getText(),
                "New node",
                "New node don`t visible");

        Assert.assertTrue(
                getDriver().findElement(By.xpath("//form")).isDisplayed(),
                "New Node form is not visible");
    }
}
