package school.redrover;

import org.testng.Assert;
import org.testng.annotations.Test;
import school.redrover.common.BaseTest;
import school.redrover.page.HomePage;
import java.util.List;


public class CreateUserTest extends BaseTest {

    @Test
    public void testCreateUser() {
        final String userName = "testUserLogin";
        final String userPassword = "testUserPassword";
        final String userEmail = "testUser@jenkins.com";

        String actualUserName = new HomePage(getDriver())
                .clickGearManageJenkinsButton()
                .clickUserLink()
                .clickCreateUserButton()
                .sendUserName(userName)
                .sendPassword(userPassword)
                .sendConfirmPassword(userPassword)
                .sendEmail(userEmail)
                .clickCreateUserButton()
                .getCreatedUserName(userName);

        Assert.assertEquals(actualUserName, userName);
    }

    @Test
    public void testCheckingEmptyInput() {

        final List<String> expectedErrors = List.of(
                "\"\" is prohibited as a username for security reasons.",
                "Password is required",
                "Password is required",
                "\"\" is prohibited as a full name for security reasons.",
                "Invalid e-mail address"
        );

        List <String> actualErrors = new HomePage(getDriver())
                .clickGearManageJenkinsButton()
                .clickUserLink()
                .clickCreateUserButton()
                .clickCreateUserButtonNegative()
                .getAllErrors();

        Assert.assertEquals(actualErrors, expectedErrors);
    }

    @Test
    public void testErrorMessageWhenCreateAnExistingUserAndEmptyEmail() {
        final String userName = "admin";
        final String userPassword = "adminPass";
        final List<String> expectedErrors = List.of(
                "User name is already taken",
                "Invalid e-mail address");

        List <String> actualErrors = new HomePage(getDriver())
                .clickGearManageJenkinsButton()
                .clickUserLink()
                .clickCreateUserButton()
                .sendUserName(userName)
                .sendPassword(userPassword)
                .sendConfirmPassword(userPassword)
                .clickCreateUserButtonNegative()
                .getAllErrors();

        Assert.assertEquals(actualErrors, expectedErrors);
    }
}
