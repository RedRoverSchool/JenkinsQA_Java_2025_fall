package school.redrover.page;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import school.redrover.common.BasePage;

public class CreateUserPage extends BasePage {

    public CreateUserPage(WebDriver driver) {
        super(driver);
    }

    public CreateUserPage sendUserName(String userName) {
        getDriver().findElement(By.id("username")).sendKeys(userName);

        return this;
    }

    public CreateUserPage sendPassword(String password) {
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

    public ManageUsersPage clickCreateUserButton() {
        getDriver().findElement(By.name("Submit")).click();

        return new ManageUsersPage(getDriver());
    }
}
