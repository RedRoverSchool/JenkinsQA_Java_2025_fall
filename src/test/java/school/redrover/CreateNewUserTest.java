package school.redrover;

import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.annotations.Test;
import school.redrover.common.BaseTest;

public class CreateNewUserTest extends BaseTest {

    @Test
    public void createNewUser() {

        getDriver().findElement(By.cssSelector("header#page-header a[href='/manage']")).click();
        getDriver().findElement(By.cssSelector("#page-body a[href='securityRealm/']")).click();
        getDriver().findElement(By.cssSelector("#page-body a[href='addUser']")).click();

        getDriver().findElement(By.cssSelector("[name='username']")).sendKeys("John");
        getDriver().findElement(By.cssSelector("[name='password1']")).sendKeys("yaE@jCz7JkYXS@@");
        getDriver().findElement(By.cssSelector("[name='password2']")).sendKeys("yaE@jCz7JkYXS@@");
        getDriver().findElement(By.cssSelector("[name='fullname']")).sendKeys("John Smit");
        getDriver().findElement(By.cssSelector("[name='email']")).sendKeys("johnsmit@gmail.com");

        getDriver().findElement(By.cssSelector("[name='Submit']")).click();

        Assert.assertEquals(getDriver().findElement(By.cssSelector("tbody a[href='user/john/']")).getText(), "John");
    }

}
