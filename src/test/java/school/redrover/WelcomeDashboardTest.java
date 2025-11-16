package school.redrover;

import org.testng.Assert;
import org.testng.annotations.Test;
import school.redrover.common.BaseTest;
import school.redrover.page.HomePage;

public class WelcomeDashboardTest extends BaseTest {

    @Test
    public void testWelcomeMessageDisplayed() {
        String welcomeMessage = new HomePage(getDriver())
                .getHeadingText();

        Assert.assertEquals(welcomeMessage, "Welcome to Jenkins!");
    }
}