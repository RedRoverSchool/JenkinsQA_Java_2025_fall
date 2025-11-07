package school.redrover;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import school.redrover.common.BaseTest;

import java.time.Duration;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public class CreateUser2Test extends BaseTest {

    WebDriverWait wait;

    @BeforeMethod
    private void preconditions() {
        wait = new WebDriverWait(getDriver(), Duration.ofSeconds(10));

        getDriver().findElement(By.id("root-action-ManageJenkinsAction")).click();
        getDriver().findElement(By.cssSelector("a[href='securityRealm/']")).click();
        getDriver().findElement(By.linkText("Create User")).click();
    }

    @Test
    public void testSuccessfulUserCreation() {
        final String username = "testUser";
        final String password = "Password1!";

        fillAndSubmitCreateUserForm(username, password);

        List<String> usernamesInTable = getDriver()
                .findElements(By.className("jenkins-table__link")).stream()
                .map(WebElement::getText).toList();

        Assert.assertListContains(
                usernamesInTable,
                n -> n.equals(username),
                "User <%s> are not created".formatted(username));
    }

    @Test
    public void testUsernameInvalidCharacters() {
        final String randomInvalidChar = String.valueOf(getRandomInvalidCharForUsernameInput());

        getDriver().findElement(By.id("username")).sendKeys(randomInvalidChar);
        getDriver().findElement(By.name("Submit")).click();

        WebElement usernameFieldErrorMessage = wait.until(ExpectedConditions.visibilityOfElementLocated(By
                .xpath("//input[@id='username']/parent::div/parent::div/following::div")));
        Assert.assertEquals(
                usernameFieldErrorMessage.getText(),
                "User name must only contain alphanumeric characters, underscore and dash",
                "Test failed for input: [%s]".formatted(randomInvalidChar));
    }

    @Test
    public void testEmptyPassword() {
        getDriver().findElement(By.name("Submit")).click();

        WebElement passwordFieldErrorMessage = wait.until(ExpectedConditions.visibilityOfElementLocated(By
                .xpath("//input[@name='password1']/parent::div/parent::div/following::div")));
        Assert.assertEquals(
                passwordFieldErrorMessage.getText(),
                "Password is required");
    }

    @Test
    public void testPasswordsUnmatch() {
        getDriver().findElement(By.name("password1")).sendKeys("password");
        getDriver().findElement(By.name("Submit")).click();

        List<WebElement> createUserFormFieldErrors = getDriver().findElements(By.className("error"));
        WebElement passwordFieldErrorMessage = createUserFormFieldErrors.get(1);
        WebElement passwordConfirmFieldErrorMessage = createUserFormFieldErrors.get(2);

        Assert.assertEquals(passwordFieldErrorMessage.getText(), "Password didn't match");
        Assert.assertEquals(passwordConfirmFieldErrorMessage.getText(), "Password didn't match");
    }

    @Test
    public void testEmptyEmail() {
        getDriver().findElement(By.name("Submit")).click();

        List<WebElement> createUserFormFieldErrors = getDriver().findElements(By.className("error"));

        WebElement emailFieldErrorMessage = createUserFormFieldErrors.get(4);
        Assert.assertEquals(emailFieldErrorMessage.getText(), "Invalid e-mail address");
    }

    @Test
    public void testEmptyFullName() {
        getDriver().findElement(By.name("Submit")).click();

        WebElement fullnameFieldErrorMessage = wait.until(ExpectedConditions.visibilityOfElementLocated(By
                .xpath("//input[@name='fullname']/parent::div/parent::div/following::div")));
        Assert.assertEquals(
                fullnameFieldErrorMessage.getText(),
                "\"\" is prohibited as a full name for security reasons.");
    }

    @Test
    public void testFindUser() throws Exception {
        final String username = "testUser";
        final String password = "Pa$$w0rd1!";
        final String userFullName = "%s %s".formatted(username, username);

        fillAndSubmitCreateUserForm(username, password);

        Thread.sleep(100);
        getDriver().findElement(By.id("root-action-SearchAction")).click();
        getDriver().findElement(By.id("command-bar")).sendKeys(username);
        getDriver().findElement(By.linkText(userFullName)).click();

        WebElement userIdInfo = getDriver().findElement(By
                .xpath("//div[@id='description']/following-sibling::div"));
        WebElement userFullNameHeader = getDriver().findElement(By.className("jenkins-build-caption"));

        Assert.assertEquals(userIdInfo.getText(), "Jenkins User ID: %s".formatted(username));
        Assert.assertEquals(userFullNameHeader.getText(), userFullName);
    }

    private void fillAndSubmitCreateUserForm(String username, String password) {
        getDriver().findElement(By.id("username")).sendKeys(username);
        getDriver().findElement(By.name("password1")).sendKeys(password);
        getDriver().findElement(By.name("password2")).sendKeys(password);
        getDriver().findElement(By.name("fullname")).sendKeys("%s %s".formatted(username, username));
        getDriver().findElement(By.name("email")).sendKeys(username.toLowerCase() + "@email.com");
        getDriver().findElement(By.name("Submit")).click();
    }

    private char getRandomInvalidCharForUsernameInput() {
        char c;
        do {
            c = (char) ThreadLocalRandom.current().nextInt(Character.MIN_VALUE, Character.MAX_VALUE);
        } while (
            // Valid Username field chars: [A-Za-z0-9_-]
                c >= 'A' && c <= 'Z' ||
                        c >= 'a' && c <= 'z' ||
                        c >= '0' && c <= '9' ||
                        c == '_' ||
                        c == '-');
        return c;
    }
}
