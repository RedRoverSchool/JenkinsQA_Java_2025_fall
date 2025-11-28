package school.redrover.page;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import school.redrover.common.BasePage;

public class MultiConfigurationProjectPage extends BasePage {

    @FindBy(name = "Submit")
    private WebElement submitButton;

    @FindBy(css = "a[href$='/confirm-rename']")
    private WebElement sidebarRenameLink;

    @FindBy(xpath = "//*[contains(@class, 'hoverable-children-model-link')]")
    private WebElement hoverElement;

    @FindBy(css = "[href$='confirm-rename']")
    private WebElement dropdownMenuRenameLink;

    @FindBy(name = "newName")
    private WebElement nameField;

    @FindBy(tagName = "h1")
    private WebElement pageHeading;

    public MultiConfigurationProjectPage(WebDriver driver) {
        super(driver);
    }

    public MultiConfigurationProjectPage clickConfigureLinkInSideMenu() {
        getWait5().until(ExpectedConditions.visibilityOfElementLocated(By
                        .xpath("//a[contains(@href, '/configure')]"))).click();

        return new MultiConfigurationProjectPage(getDriver());
    }

    public MultiConfigurationProjectPage clickSubmit() {
        submitButton.click();
        getWait5().until(ExpectedConditions.visibilityOfAllElementsLocatedBy(
                By.className("permalinks-header")));

        return this;
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

    public String getHeading() {
        return pageHeading.getText();
    }

    public String getBreadcrumbItem() {
        return getWait10().until(ExpectedConditions.visibilityOfElementLocated(By
                .xpath("//span[contains(text(),'Configuration')]"))).getText();
    }
}
