package school.redrover;

import org.testng.Assert;
import org.testng.annotations.Test;
import school.redrover.common.BaseTest;
import school.redrover.page.HomePage;

public class ConfigureUserTest extends BaseTest {

    @Test
    public void testUserNameAndIDInUserAccount() {
        final String expectedUserName = "admin";

        HomePage homePage = new HomePage(getDriver());

        String actualUserName = homePage.clickUserAccountViaIconInHeader().getUserName();
        String actualUserID = homePage.clickUserAccountViaIconInHeader().getUserID();

        Assert.assertEquals(actualUserName, expectedUserName);
        Assert.assertTrue(actualUserID.contains(expectedUserName));
    }
}
