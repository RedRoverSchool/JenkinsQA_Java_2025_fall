package school.redrover;

import org.testng.Assert;
import org.testng.annotations.Test;
import org.testng.annotations.Ignore;
import school.redrover.common.BaseTest;
import school.redrover.page.HomePage;


public class UserLoggedInTest extends BaseTest {

    private static final String EXP_USER_NAME = "admin";

    @Test
    public void testLoggedInUserNameInUserAccountBreadcrumbs() {
        String actualUserName = new HomePage(getDriver())
                .clickUserAccountViaDropDownMenu(EXP_USER_NAME)
                .getUserNameInBreadcrumbs(EXP_USER_NAME);

        Assert.assertTrue(actualUserName.contains(EXP_USER_NAME));
    }

    @Test
    public void testAccessLoggedInUserAccountFromHome() {
        String actualUserName = new HomePage(getDriver())
                .clickUserAccountViaDropDownMenu(EXP_USER_NAME)
                .getUserName();

        Assert.assertEquals(actualUserName, EXP_USER_NAME);
    }

    @Ignore
    @Test
    public void testAddDescriptionForLoggedInUser() {
        final String expectedDescription = "Lorem ipsum dolor sit amet.";

        String actualDescription = new HomePage(getDriver())
                .clickUserAccountIcon()
                .clickEditDescription()
                .sendDescriptionAndSave(expectedDescription)
                .getDescriptionText();

        Assert.assertEquals(actualDescription, expectedDescription);
    }

    @Test
    public void testUserNameInUserStatusBreadcrumbs() {
        String actualUserName = new HomePage(getDriver())
                .clickUserAccountViaDropDownMenu(EXP_USER_NAME)
                .getUserNameInBreadcrumbs(EXP_USER_NAME);

        Assert.assertEquals(actualUserName, EXP_USER_NAME);
    }
}
