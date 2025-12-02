package school.redrover;

import org.testng.Assert;
import org.testng.annotations.Test;
import school.redrover.common.BaseTest;
import school.redrover.page.HomePage;

public class UserLoggedInNewTest extends BaseTest {


    @Test
    public void testUserNameInUserStatusBreadcrumbs() {
        String expectedUserName = new HomePage(getDriver())
                .getUserAccountNameViaDropDownMenu();

        String actualUserName = new HomePage(getDriver())
                .clickUserAccountViaDropDownMenu(expectedUserName)
                .getUserNameInBreadcrumbs();

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

    @Test
    public void testLoggedInUserNameInUserAccount() {
        String expectedUserName = new HomePage(getDriver())
                .getUserAccountNameViaDropDownMenu();

        String actualBreadcrumbsUserName = new HomePage(getDriver())
                .clickUserAccountViaDropDownMenu(expectedUserName)
                .getUserNameInBreadcrumbs();

        String actualStatusUserName = new HomePage(getDriver())
                .clickUserAccountViaDropDownMenu(expectedUserName)
                .getUserName();
        Assert.assertTrue(actualBreadcrumbsUserName.contains(expectedUserName));
        Assert.assertEquals(actualStatusUserName, expectedUserName);
    }





}
