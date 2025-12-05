package school.redrover.page;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class MultiConfigurationProjectPage extends BaseProjectPage {

    @FindBy(name = "Submit")
    private WebElement submitButton;

    @FindBy(id = "description-link")
    private WebElement editDescriptionLink;

    @FindBy(name = "description")
    private WebElement descriptionField;

    @FindBy(xpath = "//div[@id='description-content']")
    private WebElement projectDescription;

    @FindBy(css = "a[href$='/confirm-rename']")
    private WebElement sidebarRenameLink;

    @FindBy(xpath = "//span[text()='Delete Multi-configuration project']/ancestor::a")
    private WebElement deleteMenuItem;

    @FindBy(xpath = "//*[contains(@class, 'hoverable-children-model-link')]")
    private WebElement hoverElement;

    @FindBy(css = "[href$='confirm-rename']")
    private WebElement dropdownMenuRenameLink;

    @FindBy(name = "newName")
    private WebElement nameField;

    public MultiConfigurationProjectPage(WebDriver driver) {
        super(driver);
    }

    @Override
    protected void waitUntilPageLoad() {
        getWait10().until(ExpectedConditions.visibilityOf(deleteMenuItem));
    }

    public MultiConfigurationProjectConfigurationPage clickConfigureLinkInSideMenu() {
        getWait5().until(ExpectedConditions.visibilityOfElementLocated(By
                .xpath("//a[contains(@href, '/configure')]"))).click();

        getWait10().until(ExpectedConditions.visibilityOf(submitButton));
        return new MultiConfigurationProjectConfigurationPage(getDriver());
    }

    public MultiConfigurationProjectPage clearDescriptionField() {
        editDescriptionLink.click();
        descriptionField.clear();
        return this;
    }

    public MultiConfigurationProjectPage sendDescription(String description) {
        descriptionField.sendKeys(description);
        submitButton.click();

        return this;
    }

    public String getDescription() {
        getWait5().until(ExpectedConditions.visibilityOfElementLocated(
                        By.xpath("//div[@id='description-content']")));
        return projectDescription.getText();
    }

    public MultiConfigurationProjectPage clickRenameLinkInSideMenu() {
        sidebarRenameLink.click();
        return this;
    }

    public MultiConfigurationProjectPage clickRenameViaDashboardDropDownMenu() {
        Actions actions = new Actions(getDriver());
        actions.moveToElement(hoverElement, 10, 10).perform();
        dropdownMenuRenameLink.click();

        return this;
    }

    public MultiConfigurationProjectPage clearNameField() {
        nameField.clear();
        return this;
    }

    public MultiConfigurationProjectPage setNewProjectName(String jobName) {
        nameField.sendKeys(jobName + Keys.ENTER);

        getWait5().until(ExpectedConditions.not(ExpectedConditions.urlContains("confirm-rename")));
        return this;
    }

    public String getBreadcrumbItem() {
        return getWait10().until(ExpectedConditions.visibilityOfElementLocated(By
                .xpath("//span[contains(text(),'Configuration')]"))).getText();
    }
}
