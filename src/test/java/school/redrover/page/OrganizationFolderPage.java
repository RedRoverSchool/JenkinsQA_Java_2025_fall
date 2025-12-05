package school.redrover.page;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class OrganizationFolderPage extends BaseProjectPage {

    @FindBy(xpath = "//span[text()='Delete Organization Folder']/ancestor::a")
    private WebElement deleteMenuItem;

    @FindBy(name = "Submit")
    private WebElement submitButton;

    public OrganizationFolderPage(WebDriver driver) {
        super(driver);
    }

    @Override
    protected void waitUntilPageLoad() {
        getWait10().until(ExpectedConditions.visibilityOf(deleteMenuItem));
    }

    public String getDisplayNameOrganizationFolder() {
        return getWait2().until(ExpectedConditions.visibilityOfElementLocated(
                By.xpath("//h1"))).getText();
    }

    public String getDescriptionOrganizationFolder() {
        return getWait2().until(ExpectedConditions.visibilityOfElementLocated(
                By.id("view-message"))).getText();
    }

    public OrganizationFolderPage clickDelete() {
         getWait2().until(ExpectedConditions.elementToBeClickable(By.className("icon-edit-delete"))).click();

         return this;
    }

    public HomePage clickYesConfirmationDelete() {
        getWait2().until(ExpectedConditions.elementToBeClickable(By.cssSelector("button[data-id='ok']"))).click();

        return new HomePage(getDriver());
    }

    public OrganizationFolderConfigurationPage clickConfigureLinkInSideMenu() {
        getWait5().until(ExpectedConditions.visibilityOfElementLocated(By
                .xpath("//a[contains(@href, '/configure')]"))).click();

        getWait10().until(ExpectedConditions.visibilityOf(submitButton));
        return new OrganizationFolderConfigurationPage(getDriver());
    }
}