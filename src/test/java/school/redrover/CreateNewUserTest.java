package school.redrover;

import org.testng.Assert;
import org.testng.annotations.Test;
import school.redrover.common.BaseTest;
import school.redrover.page.HomePage;
import school.redrover.page.UsersPage;

public class CreateNewUserTest extends BaseTest {
    final String userName = "John";
    final String userPassword = "yaE@jCz7JkYXS@@";
    final String userEmail = "johnsmith@gmail.com";
    final String fullName = "John Smith";

    @Test
    public void createNewUser() {
        String newUser = new HomePage(getDriver())
                .clickGearManageJenkinsButton()
                .clickUserButton()
                .clickCreateUserButton()
                .sendUserName(userName)
                .sendPassword(userPassword)
                .sendConfirmPassword(userPassword)
                .sendEmail(userEmail)
                .clickCreateUserButton(new UsersPage(getDriver()))
                .getUserName(userName);

        Assert.assertEquals(newUser, userName);
    }

}
