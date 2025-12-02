package school.redrover.page;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import school.redrover.common.BasePage;

public class MultibranchPipelineProjectPage extends BasePage {

    @FindBy(id = "view-message")
    private WebElement description;

    @FindBy(css = "a[href$='/confirm-rename']")
    private WebElement sidebarRenameLink;

    @FindBy(tagName = "h1")
    private WebElement pageHeading;

    @FindBy(id = "description-link")
    private WebElement addDescriptionLink;

    @FindBy(name = "description")
    private WebElement descriptionField;

    @FindBy(id = "disabled-message")
    private WebElement disabledMessage;

    public MultibranchPipelineProjectPage(WebDriver driver) {
        super(driver);
    }

    public MultibranchPipelineConfigurationPage clickConfigureLinkInSideMenu() {
        getWait5().until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("a[href='./configure']")))
                .click();

        return new MultibranchPipelineConfigurationPage(getDriver());
    }

    public String getDescription() {
        return description.getText();
    }

    public String getDisabledText() {
        return disabledMessage.getText();
    }

    public MultibranchPipelineConfirmRenamePage clickRenameLinkInSideMenu() {
        sidebarRenameLink.click();

        return new MultibranchPipelineConfirmRenamePage(getDriver());
    }

    public String getHeading() {
        return pageHeading.getText();
    }

    public MultibranchPipelineProjectPage clickAddDescriptionLink() {
        getWait2().until(ExpectedConditions.elementToBeClickable(addDescriptionLink)).click();

        return this;
    }

    public MultibranchPipelineProjectPage sendDescription(String description) {
        descriptionField.clear();
        descriptionField.sendKeys(description);

        return this;
    }

    public String getDescriptionFieldText() {
        return descriptionField
                .getShadowRoot()
                .findElement(By.cssSelector("div"))
                .getText();
    }

    public boolean isAddDescriptionLinkEnabled() {
        return addDescriptionLink.isEnabled();
    }
}
