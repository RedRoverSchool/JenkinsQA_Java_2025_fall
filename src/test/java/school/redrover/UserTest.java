package school.redrover;

import org.testng.Assert;
import org.testng.annotations.Test;
import school.redrover.common.BaseTest;
import school.redrover.page.UserCreatingPage;
import school.redrover.page.HomePage;
import school.redrover.page.UsersPage;
import school.redrover.page.UserStatusPage;

import java.util.List;


public class UserTest extends BaseTest {

    private final static String USER_NAME = "testUserLogin";
    private final static String USER_PASSWORD = "testUserPassword";
    private final static String USER_EMAIL = "testUser@jenkins.com";

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
                .clickCreateUserButton(new UserCreatingPage(getDriver()))
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
                .sendPassword(USER_PASSWORD)
                .sendConfirmPassword(USER_PASSWORD)
                .sendEmail(USER_EMAIL)
                .clickCreateUserButton(new UserCreatingPage(getDriver()))
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
                .sendPassword(USER_PASSWORD)
                .sendConfirmPassword(USER_PASSWORD)
                .clickCreateUserButton(new UserCreatingPage(getDriver()))
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
                .sendUserName(USER_NAME)
                .sendPassword(USER_PASSWORD)
                .sendConfirmPassword(userUnmatchedPassword)
                .sendEmail(USER_EMAIL)
                .clickCreateUserButton(new UserCreatingPage(getDriver()))
                .getAllErrors();

        Assert.assertEquals(actualErrors, expectedErrors);
    }

    @Test(dependsOnMethods = "testUnmatchedPasswords")
    public void testCreateUser() {

        String actualUserName = new HomePage(getDriver())
                .clickGearManageJenkinsButton()
                .clickUserButton()
                .clickCreateUserButton()
                .sendUserName(USER_NAME)
                .sendPassword(USER_PASSWORD)
                .sendConfirmPassword(USER_PASSWORD)
                .sendEmail(USER_EMAIL)
                .clickCreateUserButton(new UsersPage(getDriver()))
                .getUserName(USER_NAME);

        Assert.assertEquals(actualUserName, USER_NAME);
    }


    @Test(dependsOnMethods = "testCreateUser")
    public void searchUser() {
        String findUser = new HomePage(getDriver())
                .clickSearchButton()
                .searchForUser(USER_NAME)
                .getUserID();

        Assert.assertEquals(findUser, USER_NAME);
    }

    @Test(dependsOnMethods = "searchUser")
    public void testChangeUserName() {
        final String expFullUserName = "User Full Name";

        String actFullUserName = new HomePage(getDriver())
                .clickGearManageJenkinsButton()
                .clickUserButton()
                .clickAccountMenuItem(USER_NAME)
                .sendFullName(expFullUserName)
                .clickSave(new UserStatusPage(getDriver()))
                .getUserName();

        Assert.assertEquals(actFullUserName, expFullUserName);
    }

    @Test(dependsOnMethods = "testCreateUser")
    public void testAddDescriptionOnUserPage() {
        final String description = "Lorem ipsum dolor sit amet.";

        String actualDescriptionText = new HomePage(getDriver())
                .clickGearManageJenkinsButton()
                .clickUserButton()
                .clickSignOut()
                .signIn(USER_NAME, USER_PASSWORD)
                .clickUserAccount()
                .editDescription(description)
                .getDescriptionText();

        Assert.assertEquals(actualDescriptionText, description);
    }
}
