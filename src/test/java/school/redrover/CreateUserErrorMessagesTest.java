package school.redrover;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.Test;
import school.redrover.common.BaseTest;

import java.time.Duration;
import java.util.concurrent.ThreadLocalRandom;

public class CreateUserErrorMessagesTest extends BaseTest {

    @Test
    public void testInvalidCharacters() {
        final String randomInvalidChar = String.valueOf(getRandomInvalidCharForUsernameInput());

        WebDriverWait wait = new WebDriverWait(getDriver(), Duration.ofSeconds(10));

        getDriver().findElement(By.id("root-action-ManageJenkinsAction")).click();
        getDriver().findElement(By.cssSelector("a[href='securityRealm/']")).click();
        getDriver().findElement(By.linkText("Create User")).click();

        getDriver().findElement(By.id("username")).sendKeys(randomInvalidChar);
        getDriver().findElement(By.name("Submit")).click();

        WebElement usernameFieldErrorMessage = wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.xpath("//input[@id='username']/parent::div/parent::div/following::div")));
        Assert.assertEquals(
                usernameFieldErrorMessage.getText(),
                "User name must only contain alphanumeric characters, underscore and dash",
                "Test failed for input: [%s]".formatted(randomInvalidChar));
    }

    private char getRandomInvalidCharForUsernameInput() {
        char c;
        do {
            c = (char) ThreadLocalRandom.current().nextInt(Character.MIN_VALUE, Character.MAX_VALUE);
        } while (c >= 'A' && c <= 'Z' ||
                c >= 'a' && c <= 'z' ||
                c >= '0' && c <= '9' ||
                c == '_' ||
                c == '-');
        return c;
    }
}
