package school.redrover;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.Test;
import school.redrover.common.BaseTest;

public class WelcomeDashboardTest extends BaseTest {

    @Test
    public void testWelcomeMessageDisplayed() {
        WebElement welcomeMessage = getDriver().findElement(By.xpath("//div[@class='empty-state-block']/h1"));

        Assert.assertTrue(welcomeMessage.isDisplayed());
        Assert.assertEquals(welcomeMessage.getText(), "Welcome to Jenkins!");
    }
}
