package school.redrover;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
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

    @Test
    public void testAddDescriptionOnUserPage() {

        final String username = "Max";
        final String password = "ab999";
        final String fullName = "Max Sav";
        final String email = "max.sav@test.com";
        final String addText = "Lorem ipsum dolor sit amet.";

        getDriver().findElement(By.id("root-action-ManageJenkinsAction")).click();
        getDriver().findElement(By.xpath("//dt[text()='Users']")).click();
        getDriver().findElement(By.xpath("//a[@href='addUser']")).click();

        getDriver().findElement(By.id("username")).sendKeys(username);
        getDriver().findElement(By.xpath("//input[@name='password1']")).sendKeys(password);
        getDriver().findElement(By.xpath("//input[@name='password2']")).sendKeys(password);
        getDriver().findElement(By.xpath("//input[@name='fullname']")).sendKeys(fullName);
        getDriver().findElement(By.xpath("//input[@name='email']")).sendKeys(email);
        getDriver().findElement(By.xpath("//button[@value='Create User']")).click();

        Actions actions = new Actions(getDriver());
        actions.moveToElement(getDriver().findElement(By.id("root-action-UserAction"))).perform();
        getWait10().until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("[href ='/logout']"))).click();

        getDriver().findElement(By.id("j_username")).sendKeys("Max");
        getDriver().findElement(By.id("j_password")).sendKeys("ab999");
        getDriver().findElement(By.name("Submit")).click();

        getDriver().findElement(By.id("root-action-UserAction")).click();

        getDriver().findElement(By.id("description-link")).click();

        getDriver().findElement(By.name("description")).sendKeys(addText);
        getDriver().findElement(By.name("Submit")).click();
        String descriptionText =
                getWait2().until(ExpectedConditions.visibilityOfElementLocated(By.id("description-content"))).getText();

        Assert.assertEquals(descriptionText, addText);
    }
}
