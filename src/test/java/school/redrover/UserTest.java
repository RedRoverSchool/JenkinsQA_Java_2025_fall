package school.redrover;

import org.testng.Assert;
import org.testng.annotations.Test;
import school.redrover.common.BaseTest;
import school.redrover.page.HomePage;
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
                .clickCreateAndKeepUserCreatingPage()
                .getAllErrors();

        Assert.assertEquals(actualErrors, expectedErrors);
    }

    @Test
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
                .clickCreateAndKeepUserCreatingPage()
                .getAllErrors();

        Assert.assertEquals(actualErrors, expectedErrors);
    }

    @Test
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
                .clickCreateAndKeepUserCreatingPage()
                .getAllErrors();

        Assert.assertEquals(actualErrors, expectedErrors);
    }

    @Test
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
                .clickCreateAndKeepUserCreatingPage()
                .getAllErrors();

        Assert.assertEquals(actualErrors, expectedErrors);
    }

    @Test
    public void testCreateUser() {

        String actualUserName = new HomePage(getDriver())
                .clickGearManageJenkinsButton()
                .clickUserButton()
                .clickCreateUserButton()
                .sendUserName(USER_NAME)
                .sendPassword(USER_PASSWORD)
                .sendConfirmPassword(USER_PASSWORD)
                .sendEmail(USER_EMAIL)
                .clickCreateAndGoToUsersPage()
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
    public void testAddDescriptionOnUserPage() {
        final String description = "Lorem ipsum dolor sit amet.";

        String actualDescriptionText = new HomePage(getDriver())
                .clickGearManageJenkinsButton()
                .clickUserButton()
                .clickSignOut()
                .signIn(USER_NAME, USER_PASSWORD)
                .clickUserAccountIcon()
                .editDescription(description)
                .getDescriptionText();

        Assert.assertEquals(actualDescriptionText, description);
    }

   @Test(dependsOnMethods = "searchUser")
    public void testChangeEmailOnUserPage() {
        final String email = "gkgk@kgk.kgk";

        String actualEmailText = new HomePage(getDriver())
                .clickGearManageJenkinsButton()
                .clickUserButton()
                .clickAccountMenuItem(USER_NAME)
                .editEmail(email)
                .getEmailText();

        Assert.assertEquals(actualEmailText, email);
    }

    @Test(dependsOnMethods = "testChangeEmailOnUserPage")
    public void testChangeUserName() {
        final String expFullUserName = "User Full Name";

        String actFullUserName = new HomePage(getDriver())
                .clickGearManageJenkinsButton()
                .clickUserButton()
                .clickAccountMenuItem(USER_NAME)
                .sendFullName(expFullUserName)
                .clickSave()
                .getUserName();

        Assert.assertEquals(actFullUserName, expFullUserName);
    }

    @Test(dependsOnMethods = "testChangeUserName")
    private void createUser() {
        new HomePage(getDriver())
                .clickGearManageJenkinsButton()
                .clickUserButton()
                .clickCreateUserButton()
                .sendUserName(USER_NAME)
                .sendPassword(USER_PASSWORD)
                .sendConfirmPassword(USER_PASSWORD)
                .sendEmail(USER_EMAIL)
                .clickCreateAndGoToUsersPage();
    }
}
