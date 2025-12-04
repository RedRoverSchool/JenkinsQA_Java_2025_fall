package school.redrover.page;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import school.redrover.common.BasePage;

import java.util.List;


public class UserCreatingPage extends BasePage {

    @FindBy(id = "username")
    private WebElement userNameField;

    @FindBy(name = "password1")
    private WebElement passwordField;

    @FindBy(name = "password2")
    private WebElement confirmPasswordField;

    @FindBy(name = "email")
    private WebElement emailField;

    @FindBy(name = "Submit")
    private WebElement createButton;

    @FindBy(xpath = "//*[@class='error jenkins-!-margin-bottom-2']")
    private List<WebElement> errorsList;

    /***
     * WebElements from different Pages to wait before Page return
     */

    @FindBy(xpath = "//h1[contains(text(),'Users')]")
    private WebElement usersPageHeader;

    @FindBy(className = "error")
    private WebElement userCreatingPageError;

    public UserCreatingPage(WebDriver driver) {
        super(driver);
    }

    public UserCreatingPage sendUserName(String userName) {
        userNameField.sendKeys(userName);

        return this;
    }

    public UserCreatingPage sendPassword(String password) {
        passwordField.sendKeys(password);

        return this;
    }

    public UserCreatingPage sendConfirmPassword(String password) {
        confirmPasswordField.sendKeys(password);

        return this;
    }

    public UserCreatingPage sendEmail(String email) {
        emailField.sendKeys(email);

        return this;
    }

    public UsersPage clickCreateAndGoToUsersPage() {
        createButton.click();

        getWait5().until(ExpectedConditions.visibilityOf(usersPageHeader));

        return new UsersPage(getDriver());
    }

    public UserCreatingPage clickCreateAndKeepUserCreatingPage() {
        createButton.click();
        getWait5().until(ExpectedConditions.visibilityOf(userCreatingPageError));

        return this;
    }

    public List<String> getAllErrors() {

        return errorsList
                .stream()
                .map(WebElement::getText)
                .toList();
    }
}
