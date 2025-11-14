package school.redrover;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;
import org.testng.annotations.Test;
import school.redrover.common.BaseTest;

public class CreateNewUserTest extends BaseTest {

    private static final String USERNAME = "john";

    @Test
    public void createNewUser() {


        getDriver().findElement(By.cssSelector("header#page-header a[href='/manage']")).click();
        getDriver().findElement(By.cssSelector("#page-body a[href='securityRealm/']")).click();
        getDriver().findElement(By.cssSelector("#page-body a[href='addUser']")).click();

        getDriver().findElement(By.cssSelector("[name='username']")).sendKeys(USERNAME);
        getDriver().findElement(By.cssSelector("[name='password1']")).sendKeys("yaE@jCz7JkYXS@@");
        getDriver().findElement(By.cssSelector("[name='password2']")).sendKeys("yaE@jCz7JkYXS@@");
        getDriver().findElement(By.cssSelector("[name='fullname']")).sendKeys("John Smit");
        getDriver().findElement(By.cssSelector("[name='email']")).sendKeys("johnsmit@gmail.com");

        getDriver().findElement(By.cssSelector("[name='Submit']")).click();

        Assert.assertEquals(getDriver().findElement(By.cssSelector("tbody a[href='user/john/'].jenkins-table__link")).getText(), USERNAME);
    }

    @Test(dependsOnMethods = "createNewUser")
    public void checkCreatedUser() {
        getDriver().findElement(By.id("root-action-ManageJenkinsAction")).click();
        getDriver().findElement(By.cssSelector("a[href='securityRealm/']")).click();
        Assert.assertTrue(getDriver().findElement(By.cssSelector("tbody a[href='user/%s/'].jenkins-table__link".formatted(USERNAME))).isDisplayed());
        getDriver().findElement(By.cssSelector("tbody a[href='user/%s/'].jenkins-button".formatted(USERNAME))).click();
        Assert.assertTrue(getDriver().findElement(By.cssSelector("#main-panel > div:nth-child(4)")).getText().contains(USERNAME));
    }

    @Test(dependsOnMethods = "createNewUser")
    public void findCreatedUser() {
        getDriver().findElement(By.id("root-action-SearchAction")).click();
        getDriver().findElement(By.id("command-bar")).sendKeys(USERNAME);
        getWait5().until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("#search-results [href='/user/john']")));
        getDriver().findElement(By.id("command-bar")).sendKeys(Keys.RETURN);
        Assert.assertTrue(getDriver().findElement(By.cssSelector("#main-panel > div:nth-child(4)")).getText().contains(USERNAME));
    }

    @Test(dependsOnMethods = "createNewUser")
    public void errorExistingUser() {

        getDriver().findElement(By.id("root-action-ManageJenkinsAction")).click();
        getDriver().findElement(By.cssSelector("a[href='securityRealm/']")).click();
        getDriver().findElement(By.cssSelector(".jenkins-app-bar__controls")).click();
        getDriver().findElement(By.cssSelector("[name='username']")).sendKeys(USERNAME);
        getDriver().findElement(By.cssSelector("[name='password1']")).sendKeys("yaE@jCz7JkYXS@@");
        getDriver().findElement(By.cssSelector("[name='password2']")).sendKeys("yaE@jCz7JkYXS@@");
        getDriver().findElement(By.cssSelector("[name='fullname']")).sendKeys("John Smit");
        getDriver().findElement(By.cssSelector("[name='email']")).sendKeys("johnsmit@gmail.com");
        getDriver().findElement(By.cssSelector("[name='Submit']")).click();

        Assert.assertEquals(getDriver().findElement(By.cssSelector("div.error.jenkins-\\!-margin-bottom-2")).getText(), "User name is already taken");
    }

}
