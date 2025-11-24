package school.redrover;

import org.testng.Assert;
import org.testng.annotations.Test;
import school.redrover.common.BaseTest;
import school.redrover.common.ProjectUtils;
import school.redrover.page.HomePage;
import school.redrover.page.LoginPage;

public class SignInOutTest extends BaseTest {

    @Test
    public void testSignOutByClickUserMenu() {

        String title = new HomePage(getDriver())
                .clickSignOut()
                .getTitle();

        Assert.assertEquals(title, "Sign in to Jenkins");
    }

    @Test(dependsOnMethods = "testSignOutByClickUserMenu")
    public void testGoToProfileAfterSignOut() {

        String title = new LoginPage(getDriver())
                .getUrlProfile();

        Assert.assertEquals(title, "Sign in to Jenkins");
    }

    @Test(dependsOnMethods = {"testGoToProfileAfterSignOut"})
    public void testSignInAfterSignOut() {

        String title = new LoginPage(getDriver())
                .signIn(ProjectUtils.getUserName(),ProjectUtils.getPassword())
                .getHeadingText();

        Assert.assertEquals(title, "Welcome to Jenkins!");
    }
}
