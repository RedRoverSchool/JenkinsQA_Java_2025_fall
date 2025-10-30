package school.redrover;

import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.annotations.Test;
import school.redrover.common.BaseTest;


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
}
