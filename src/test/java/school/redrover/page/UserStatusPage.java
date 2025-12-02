package school.redrover.page;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import school.redrover.common.BasePage;


public class UserStatusPage extends BasePage {

    @FindBy(xpath = "//div//h1")
    private WebElement userName;

    @FindBy(xpath = "//div[@id='main-panel']/descendant::div[contains(text(),'User ID:')]")
    private WebElement userId;

    @FindBy(id = "description-link")
    private WebElement editDescriptionButton;

    @FindBy(name = "description")
    private WebElement descriptionTextBox;

    @FindBy(name = "Submit")
    private WebElement saveButton;

    @FindBy(id = "description-content")
    private WebElement description;
  
    @FindBy(xpath = "//div[@id='main-panel']/descendant::div[contains(text(),'User ID:')]")
    private WebElement userIDElement;

    @FindBy(id = "description-link")
    private WebElement descriptionLink;

    @FindBy(name = "description")
    private WebElement descriptionField;

    @FindBy(name = "Submit")
    private WebElement submitButton;

    @FindBy(id = "description-content")
    private WebElement descriptionContent;

    public UserStatusPage(WebDriver driver) {
        super(driver);
    }

    public String getUserName() {

        return getWait5().until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div//h1"))).getText();

    }

    public String getUserNameInBreadcrumbs(String userName) {

        return getWait10().until(ExpectedConditions.visibilityOfElementLocated(By
                .xpath("//a[@href='/user/%s/']".formatted(userName))))
                .getText();
    }

    public String getUserNameAdminInBreadcrumbs() {

        return getWait10().until(ExpectedConditions.visibilityOfElementLocated(By
                        .xpath("//a[@href='/user/admin/']")))
                .getText();
    }

    public String getUserID() {

        return userId.getText().substring(17);
    }

    public UserStatusPage clickEditDescription() {
        editDescriptionButton.click();
        getWait5().until(ExpectedConditions.elementToBeClickable(descriptionTextBox));

        return this;
    }

    public UserStatusPage sendDescriptionAndSave(String text) {
        descriptionTextBox.clear();
        descriptionTextBox.sendKeys(text);
        saveButton.click();

        getWait5().until(ExpectedConditions.visibilityOf(description));
        return userIDElement.getText().substring(17);
    }

    public UserStatusPage editDescription(String text) {
        descriptionLink.click();
        descriptionField.sendKeys(text);
        submitButton.click();

        return this;
    }

    public String getDescriptionText() {
        return getWait5().until(ExpectedConditions.visibilityOf(descriptionContent))
                .getText();
    }
}
