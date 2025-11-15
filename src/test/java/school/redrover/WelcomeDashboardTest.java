package school.redrover;

import org.testng.Assert;
import org.testng.annotations.Test;
import school.redrover.common.BaseTest;
import school.redrover.page.LoginPage;

public class WelcomeDashboardTest extends BaseTest {

    @Test
    public void testWelcomeMessageDisplayed() {
        String welcomeMessage = new LoginPage(getDriver())
                .getTitle();

        Assert.assertEquals(welcomeMessage, "Welcome to Jenkins!");
    }
}