package school.redrover;

import org.testng.Assert;
import org.testng.annotations.Test;
import school.redrover.common.BaseTest;
import school.redrover.page.HomePage;
import school.redrover.page.ManageUsersPage;

public class CreateNewUserTest extends BaseTest {
    final String userName = "John";
    final String userPassword = "yaE@jCz7JkYXS@@";
    final String userEmail = "johnsmith@gmail.com";
    final String fullName = "John Smith";

    @Test
    public void createNewUser() {
        String newUser = new HomePage(getDriver())
                .clickGearManageJenkinsButton()
                .clickUserLink()
                .clickCreateUserButton()
                .sendUserName(userName)
                .sendPassword(userPassword)
                .sendConfirmPassword(userPassword)
                .sendEmail(userEmail)
                .clickCreateUserButton(new ManageUsersPage(getDriver()))
                .getCreatedUserName(userName);

        Assert.assertEquals(newUser, userName);
    }

    @Test(dependsOnMethods = "createNewUser")
    public void checkCreatedUser() {
        String newCheck = new HomePage(getDriver())
                .clickGearManageJenkinsButton()
                .clickUserLink()
                .getCreatedUserName(userName);
        Assert.assertEquals(newCheck, userName);
    }

    @Test(dependsOnMethods = "createNewUser")
    public void createExistingUser() {

        String newCheck = new HomePage(getDriver())
                .clickGearManageJenkinsButton()
                .clickUserLink()
                .clickCreateUserButton()
                .sendUserName(userName)
                .sendPassword(userPassword)
                .sendConfirmPassword(userPassword)
                .sendEmail(userEmail)
                .clickCreateUserButton(new ManageUsersPage(getDriver()))
                .checkCreatedUser();

        Assert.assertEquals(newCheck, "User name is already taken");
    }

    @Test(dependsOnMethods = "createNewUser")
    public void searchUser() {
        String findUser = new HomePage(getDriver())
                .clickSearchButton()
                .searchForUser(userName)
                .getUserID();

        Assert.assertTrue(findUser.contains(userName));
    }
}
