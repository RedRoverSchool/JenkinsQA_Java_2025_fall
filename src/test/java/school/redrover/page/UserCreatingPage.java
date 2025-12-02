package school.redrover.page;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import school.redrover.common.BasePage;

import java.util.List;


public class UserCreatingPage extends BasePage {

    public UserCreatingPage(WebDriver driver) {
        super(driver);
    }

    public UserCreatingPage sendUserName(String userName) {
        getDriver().findElement(By.id("username")).sendKeys(userName);

        return this;
    }

    public UserCreatingPage sendPassword(String password) {
        getDriver().findElement(By.name("password1")).sendKeys(password);
        return this;
    }

    public UserCreatingPage sendConfirmPassword(String password) {
        getDriver().findElement(By.name("password2")).sendKeys(password);
        return this;
    }

    public UserCreatingPage sendEmail(String email) {
        getDriver().findElement(By.name("email")).sendKeys(email);
        return this;
    }

    public UsersPage clickCreateAndGoToUsersPage() {
        getDriver().findElement(By.name("Submit")).click();
        getWait5().until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//h1[contains(.,'Users')]")));

        return new UsersPage(getDriver());
    }

    public UserCreatingPage clickCreateAndKeepUserCreatingPage() {
        getDriver().findElement(By.name("Submit")).click();
        getWait5().until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//h1[text()='Create User']")));

        return this;
    }

    public List<String> getAllErrors() {

        return getDriver()
                .findElements(By.xpath("//*[@class='error jenkins-!-margin-bottom-2']"))
                .stream()
                .map(WebElement::getText)
                .toList();
    }
}
