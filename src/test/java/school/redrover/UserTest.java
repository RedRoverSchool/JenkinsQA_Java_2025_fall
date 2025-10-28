package school.redrover;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.Test;
import school.redrover.common.BaseTest;

public class UserTest extends BaseTest {

    @Test
    public void testCreateUser() {
        String username = "John";
        String password = "123456";
        String fullName = "John Doe";
        String email = "john.doe@test.com";

        getDriver().findElement(By.id("root-action-ManageJenkinsAction")).click();
        getDriver().findElement(By.xpath("//dt[text()='Users']")).click();
        getDriver().findElement(By.xpath("//a[@href='addUser']")).click();

        getDriver().findElement(By.id("username")).sendKeys(username);
        getDriver().findElement(By.xpath("//input[@name='password1']")).sendKeys(password);
        getDriver().findElement(By.xpath("//input[@name='password2']")).sendKeys(password);
        getDriver().findElement(By.xpath("//input[@name='fullname']")).sendKeys(fullName);
        getDriver().findElement(By.xpath("//input[@name='email']")).sendKeys(email);
        getDriver().findElement(By.xpath("//button[@value='Create User']")).click();

        WebElement createdUser = getDriver().findElement(By.xpath("//a[@href='user/" + username.toLowerCase() + "/']"));
        Assert.assertEquals(createdUser.getText(), username);
    }
}
