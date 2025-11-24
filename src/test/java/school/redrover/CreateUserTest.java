package school.redrover;

import org.testng.Assert;
import org.testng.annotations.Test;
import school.redrover.common.BaseTest;
import school.redrover.page.*;

import java.util.List;


public class CreateUserTest extends BaseTest {
    final String userName = "testUserLogin";
    final String userPassword = "testUserPassword";
    final String userEmail = "testUser@jenkins.com";

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
                .clickUserButton()
                .clickCreateUserButton()
                .clickCreateUserButton(new CreateUserPage(getDriver()))
                .getAllErrors();

        Assert.assertEquals(actualErrors, expectedErrors);
    }

    @Test(dependsOnMethods = "testCheckingEmptyInput")
    public void testUsernameInvalidCharacters() {
        final String userName = "!$@(!%%!@_)__>><";

        final List<String> expectedErrors = List.of(
                "User name must only contain alphanumeric characters, underscore and dash");

        List <String> actualErrors = new HomePage(getDriver())
                .clickGearManageJenkinsButton()
                .clickUserButton()
                .clickCreateUserButton()
                .sendUserName(userName)
                .sendPassword(userPassword)
                .sendConfirmPassword(userPassword)
                .sendEmail(userEmail)
                .clickCreateUserButton(new CreateUserPage(getDriver()))
                .getAllErrors();

        Assert.assertEquals(actualErrors, expectedErrors);
    }

    @Test(dependsOnMethods = "testUsernameInvalidCharacters")
    public void testErrorMessageWhenCreateAnExistingUserAndEmptyEmail() {
        final String userName = "admin";

        final List<String> expectedErrors = List.of(
                "User name is already taken",
                "Invalid e-mail address");

        List <String> actualErrors = new HomePage(getDriver())
                .clickGearManageJenkinsButton()
                .clickUserButton()
                .clickCreateUserButton()
                .sendUserName(userName)
                .sendPassword(userPassword)
                .sendConfirmPassword(userPassword)
                .clickCreateUserButton(new CreateUserPage(getDriver()))
                .getAllErrors();

        Assert.assertEquals(actualErrors, expectedErrors);
    }

    @Test(dependsOnMethods = "testErrorMessageWhenCreateAnExistingUserAndEmptyEmail")
    public void testUnmatchedPasswords() {
        final String userUnmatchedPassword = "testNotUserPassword";

        final List<String> expectedErrors = List.of(
                "Password didn't match",
                "Password didn't match");

        List <String> actualErrors = new HomePage(getDriver())
                .clickGearManageJenkinsButton()
                .clickUserButton()
                .clickCreateUserButton()
                .sendUserName(userName)
                .sendPassword(userPassword)
                .sendConfirmPassword(userUnmatchedPassword)
                .sendEmail(userEmail)
                .clickCreateUserButton(new CreateUserPage(getDriver()))
                .getAllErrors();

        Assert.assertEquals(actualErrors, expectedErrors);
    }

    @Test(dependsOnMethods = "testUnmatchedPasswords")
    public void testCreateUser() {

        String actualUserName = new HomePage(getDriver())
                .clickGearManageJenkinsButton()
                .clickUserButton()
                .clickCreateUserButton()
                .sendUserName(userName)
                .sendPassword(userPassword)
                .sendConfirmPassword(userPassword)
                .sendEmail(userEmail)
                .clickCreateUserButton(new ManageUsersPage(getDriver()))
                .getUserName(userName);

        Assert.assertEquals(actualUserName, userName);
    }


    @Test(dependsOnMethods = "testCreateUser")
    public void searchUser() {
        String findUser = new HomePage(getDriver())
                .clickSearchButton()
                .searchForUser(userName)
                .getUserID();

        Assert.assertEquals(findUser, userName);
    }

    @Test(dependsOnMethods = "searchUser")
    public void testChangeUserName() {
        final String expFullUserName = "User Full Name";

        String actFullUserName = new HomePage(getDriver())
                .clickGearManageJenkinsButton()
                .clickUserButton()
                .clickAccountMenuItem(userName)
                .sendFullName(expFullUserName)
                .clickSave(new UserStatusPage(getDriver()))
                .getUserName();

        Assert.assertEquals(actFullUserName, expFullUserName);
    }
}
