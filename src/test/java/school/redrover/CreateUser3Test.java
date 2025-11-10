package school.redrover;

import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.annotations.Test;
import school.redrover.common.BaseTest;

public class CreateUser3Test extends BaseTest {
    @Test
    public void testSuccessfulUserCreation() {
        final String username = "new_test";
        final String password = "test";
        final String email = "test@test.ru";

        getDriver().findElement(By.id("root-action-ManageJenkinsAction")).click();
        getDriver().findElement(By.xpath("//a[@href='securityRealm/']")).click();
        getDriver().findElement(By.xpath("//a[@href='addUser']")).click();
        getDriver().findElement(By.id("username")).sendKeys(username);
        getDriver().findElement(By.name("password1")).sendKeys(password);
        getDriver().findElement(By.name("password2")).sendKeys(password);
        getDriver().findElement(By.name("email")).sendKeys(email);
        getDriver().findElement(By.name("Submit")).click();

        getDriver().findElement(By.xpath("//a[contains(@href, 'securityRealm')]")).click();
        boolean userExists = getDriver().findElement(By.xpath("//table//a[text()='" + username + "']")).isDisplayed();
        Assert.assertTrue(userExists, "User was not found in the list");
    }
}
