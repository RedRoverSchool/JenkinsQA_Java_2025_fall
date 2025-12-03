package school.redrover.page;

import org.openqa.selenium.By;
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

        getWait5().until(ExpectedConditions.visibilityOfElementLocated(By.id("description-link")));

        return new UserStatusPage(getDriver());
    }

    public UserAccountPage sendEmail(String email) {
        emailField.clear();
        emailField.sendKeys(email); 
        return this;  
    }
   
    public <P extends BasePage> P clickSave(P returnedPage) {
        getWait5().until(ExpectedConditions.visibilityOf(saveButton)).click();
        return returnedPage;
    }

    public UserAccountPage editEmail(String EMAIL) {
        emailField.clear();
        emailField.sendKeys(EMAIL);
        applyButton.click();

        return this;
    }

    public String getEmailText() {
       return emailField.getAttribute("value");
    }
}
