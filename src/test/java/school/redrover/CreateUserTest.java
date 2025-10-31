package school.redrover;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.Test;
import school.redrover.common.BaseTest;

import java.time.Duration;
import java.util.List;


public class CreateUserTest extends BaseTest {

    @Test
    public void testCreateUserPos() {
        final String userName = "testUserLogin";
        final String userPassword = "testUserPassword";
        final String userEmail = "testUser@jenkins.com";

        getDriver().findElement(By.id("root-action-ManageJenkinsAction")).click();
        getDriver().findElement(By.xpath("//a[@href='securityRealm/']")).click();
        getDriver().findElement(By.xpath("//a[@href='addUser']")).click();
        getDriver().findElement(By.id("username")).sendKeys(userName);
        getDriver().findElement(By.name("password1")).sendKeys(userPassword);
        getDriver().findElement(By.name("password2")).sendKeys(userPassword);
        getDriver().findElement(By.name("email")).sendKeys(userEmail);
        getDriver().findElement(By.name("Submit")).click();

        Assert.assertEquals(getDriver().findElement(By.xpath("//td[text()='%s']".formatted(userName))).getText(),
                userName);
    }

    @Test
    public void testCheckingEmptyInput() {
        getDriver().manage().timeouts().implicitlyWait(Duration.ofSeconds(2));

        final List<String> expectedErrors = List.of(
                "\"\" is prohibited as a username for security reasons.",
                "Password is required",
                "Password is required",
                "\"\" is prohibited as a full name for security reasons.",
                "Invalid e-mail address"
        );

        getDriver().findElement(By.id("root-action-ManageJenkinsAction")).click();
        getDriver().findElement(By.xpath("//dt[text()='Users']")).click();
        getDriver().findElement(By.className("jenkins-button--primary")).click();
        getDriver().findElement(By.name("Submit")).click();

        List<String> actualErrors = getDriver()
                .findElements(By.xpath("//*[@class='error jenkins-!-margin-bottom-2']"))
                .stream()
                .map(WebElement::getText)
                .toList();

        Assert.assertEquals(expectedErrors, actualErrors);
    }
}
