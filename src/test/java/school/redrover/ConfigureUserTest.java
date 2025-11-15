package school.redrover;

import org.testng.Assert;
import org.testng.annotations.Test;
import school.redrover.common.BaseTest;
import school.redrover.page.HomePage;

public class ConfigureUserTest extends BaseTest {

    @Test
    public void testAccessUserAccountFromHome() {
        final String expUserName = "admin";

        String actUserName = new HomePage(getDriver())
                .clickUserAccountButton()
                .getUserName();

        Assert.assertEquals(actUserName, expUserName);
    }
}
