package school.redrover;

import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;
import org.testng.annotations.Test;
import school.redrover.common.BaseTest;
import school.redrover.page.HomePage;
import school.redrover.page.UserAccountPage;


public class ConfigureUserTest extends BaseTest {

    @Test
    public void testAccessUserAccountFromHome() {
        final String expUserName = "admin";

        UserAccountPage userAccountPage = new HomePage(getDriver()).clickUserAccountButton();

        getWait10().until(ExpectedConditions.urlContains("/user/"));

        String actUserName = userAccountPage.getUserName();

        Assert.assertEquals(actUserName, expUserName);
    }
}
