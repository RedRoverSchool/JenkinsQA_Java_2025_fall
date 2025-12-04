package school.redrover.page;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import school.redrover.common.BasePage;


public class UserAccountPage extends BasePage {

    @FindBy(name = "_.fullName")
    private WebElement fullNameField;

    @FindBy(xpath = "//input[@name='email.address']")
    private WebElement emailField;
    
    @FindBy(xpath = "//button[@name='Apply']")
    private WebElement applyButton;

    @FindBy(name = "Submit")
    private WebElement saveButton;

    /***
     * WebElements from different Pages to wait before Page return
     */

    @FindBy(id = "description-link")
    private WebElement userStatusPageEditDescriptionButton;


    public UserAccountPage(WebDriver driver) {
        super(driver);
    }

    public UserAccountPage sendFullName(String fullName) {
        fullNameField.clear();
        fullNameField.sendKeys(fullName);

        return this;
    }

    public UserStatusPage clickSave() {
        saveButton.click();

        getWait5().until(ExpectedConditions.visibilityOf(userStatusPageEditDescriptionButton));

        return new UserStatusPage(getDriver());
    }

    public UserAccountPage sendEmail(String email) {
        emailField.clear();
        emailField.sendKeys(email); 

        return this;
    }

    public UserAccountPage editEmail(String email) {
        emailField.clear();
        emailField.sendKeys(email);
        applyButton.click();

        return this;
    }

    public String getEmailText() {
        return emailField.getAttribute("value");
    }
}
