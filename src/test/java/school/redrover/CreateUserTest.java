package school.redrover;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
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
                .clickCreateUserButton()
                .getAllErrors();

        Assert.assertEquals(expectedErrors, actualErrors);
    }

    @Test
    public void testErrorMessageWhenCreateAnExistingUserAndEmptyEmail() {
        final String userName = "admin";
        final String userPassword = "adminPass";
        final List<String> expectedErrors = List.of(
                "User name is already taken",
                "Invalid e-mail address");

        getDriver().findElement(By.id("root-action-ManageJenkinsAction")).click();
        getDriver().findElement(By.xpath("//a[@href='securityRealm/']")).click();
        getDriver().findElement(By.xpath("//a[@href='addUser']")).click();
        getDriver().findElement(By.id("username")).sendKeys(userName);
        getDriver().findElement(By.name("password1")).sendKeys(userPassword);
        getDriver().findElement(By.name("password2")).sendKeys(userPassword);
        getDriver().findElement(By.name("Submit")).click();

        List<String> actualErrors = getWait2().until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By
                .xpath("//*[@class='error jenkins-!-margin-bottom-2']")))
                .stream()
                .map(WebElement::getText)
                .toList();

        Assert.assertEquals(actualErrors, expectedErrors);
    }
}
