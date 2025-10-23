package school.redrover;

import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.annotations.Test;
import school.redrover.common.BaseTest;

public class HomePageTest extends BaseTest {

    @Test
    public void testHome() {

        Assert.assertEquals(
                getDriver().findElement(By.cssSelector("span.jenkins-mobile-hide")).getText(),
                "Jenkins---");
    }
}
