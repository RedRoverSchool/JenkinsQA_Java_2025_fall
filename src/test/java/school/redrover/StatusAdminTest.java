package school.redrover;

import org.testng.Assert;
import org.testng.annotations.Test;
import school.redrover.common.BaseTest;
import school.redrover.page.HomePage;

public class StatusAdminTest extends BaseTest {

    @Test
    public void testDisplayAdminNameInUserStatusBreadcrumbs() {
        final String[] expectedUserName = {null};
        String actualUserName = new HomePage(getDriver())
                .clickUserAccountIcon()
                .getUserNameFromDropDownMenu(string -> {
                    expectedUserName[0] = string;
                })
                .clickUserAccountViaDropDownMenu(expectedUserName[0])
                .getAdminNameInBreadcrumbs();

        Assert.assertTrue(actualUserName.contains(expectedUserName[0]));
    }

    @Test
    public void testDisplayAdminNameInUserStatusHeading() {
        final String[] expectedUserName = {null};
        String actualUserName = new HomePage(getDriver())
                .clickUserAccountIcon()
                .getUserNameFromDropDownMenu(string -> {
                    expectedUserName[0] = string;
                })
                .clickUserAccountViaDropDownMenu(expectedUserName[0])
                .getUserName();

        Assert.assertEquals(actualUserName, expectedUserName[0]);
    }
}
