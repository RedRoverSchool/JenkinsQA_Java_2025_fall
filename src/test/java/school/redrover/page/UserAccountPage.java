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

    @FindBy(xpath = "//*[@id='main-panel']//section[4]/div[4]/div[1]/input")
    private WebElement emailField;

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

    public String getEmailText() {

        return emailField.getAttribute("value");
    }
}
