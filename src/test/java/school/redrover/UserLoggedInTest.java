package school.redrover;

import org.testng.Assert;
import org.testng.annotations.Test;
import school.redrover.common.BaseTest;
import school.redrover.page.HomePage;


public class UserLoggedInTest extends BaseTest {

    @Test
    public void testLoggedInUserNameInUserAccount() {
        final String expectedUserName = "admin";

        String actualUserName = new HomePage(getDriver())
                .clickUserAccountViaDropDownMenu(expectedUserName)
                .getUserNameInBreadcrumbs(expectedUserName);

        Assert.assertTrue(actualUserName.contains(expectedUserName));
    }

    @Test
    public void testAccessLoggedInUserAccountFromHome() {
        final String expectedUserName = "admin";

        String actualUserName = new HomePage(getDriver())
                .clickUserAccountViaDropDownMenu(expectedUserName)
                .getUserName();

        Assert.assertEquals(actualUserName, expectedUserName);
    }

    @Test
    public void testUserNameInUserStatusBreadcrumbs() {
        String expectedUserName = new HomePage(getDriver())
                .getUserAccountNameViaDropDownMenu();

        String actualUserName = new HomePage(getDriver())
                .clickUserAccountViaDropDownMenu(expectedUserName)
                .getUserNameAdminInBreadcrumbs();

        Assert.assertTrue(actualUserName.contains(expectedUserName));
    }

    @Test
    public void testUserNameInUserStatusHeading() {
        String expectedUserName = new HomePage(getDriver())
                .getUserAccountNameViaDropDownMenu();

        String actualUserName = new HomePage(getDriver())
                .clickUserAccountViaDropDownMenu(expectedUserName)
                .getUserName();

        Assert.assertEquals(actualUserName, expectedUserName);
    }
}
