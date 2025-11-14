package school.redrover.page;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import school.redrover.common.BasePage;

import java.util.List;

public class CreateUserPage extends BasePage {

    public CreateUserPage(WebDriver driver) {
        super(driver);
    }

    public CreateUserPage sendUserName(String userName) {
        getDriver().findElement(By.id("username")).sendKeys(userName);

        return this;
    }

    public CreateUserPage sendUserName(CharSequence userName) {
        getDriver().findElement(By.id("username")).sendKeys(userName);

        return this;
    }

    public CreateUserPage sendPassword(String password) {
        getDriver().findElement(By.name("password1")).sendKeys(password);
        return this;
    }

    public CreateUserPage sendPassword(CharSequence password) {
        getDriver().findElement(By.name("password1")).sendKeys(password);
        return this;
    }

    public CreateUserPage sendConfirmPassword(String password) {
        getDriver().findElement(By.name("password2")).sendKeys(password);
        return this;
    }

    public CreateUserPage sendEmail(String email) {
        getDriver().findElement(By.name("email")).sendKeys(email);
        return this;
    }

    public CreateUserPage sendEmail(CharSequence email) {
        getDriver().findElement(By.name("email")).sendKeys(email);
        return this;
    }

    public void clickSubmitButton() {
        getDriver().findElement(By.name("Submit")).click();
    }

    public ManageUsersPage clickCreateUserButton() {
        clickSubmitButton();

        return new ManageUsersPage(getDriver());
    }

    public CreateUserPage clickCreateUserButtonNegative() {
        clickSubmitButton();

        return this;
    }

    public String getUserNameField() {
        return getDriver().findElement(By.xpath("//input[@name='username']")).getAttribute("value");
    }

    public String getFullUserNameField() {
        return getDriver().findElement(By.xpath("//input[@name='fullname']")).getAttribute("value");
    }

    public List<String> getAllErrors() {

        return getDriver()
                .findElements(By.xpath("//*[@class='error jenkins-!-margin-bottom-2']"))
                .stream()
                .map(WebElement::getText)
                .toList();
    }
}
