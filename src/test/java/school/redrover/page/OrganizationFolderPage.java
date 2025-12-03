package school.redrover.page;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import school.redrover.common.BasePage;

public class OrganizationFolderPage extends BaseProjectPage {

    @FindBy(tagName = "h1")
    private WebElement headingText;

    public OrganizationFolderPage(WebDriver driver) {
        super(driver);
    }

    @Override
    public String getHeadingText() {
        return headingText.getText();
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

        return new OrganizationFolderConfigurationPage(getDriver());
    }
}