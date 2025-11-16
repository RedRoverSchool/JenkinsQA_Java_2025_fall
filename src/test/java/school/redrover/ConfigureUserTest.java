package school.redrover;

import org.testng.Assert;
import org.testng.annotations.Test;
import school.redrover.common.BaseTest;
import school.redrover.page.HomePage;

public class ConfigureUserTest extends BaseTest {

    @Test
    public void testUserNameInUserAccount() {
        final String expectedUserName = "admin";

        String actualUserName = new HomePage(getDriver())
                .clickUserAccountViaIconInHeader()
                .getUserName();

        Assert.assertEquals(actualUserName, expectedUserName);
    }
}
