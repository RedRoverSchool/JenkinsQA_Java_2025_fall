package school.redrover.page;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import school.redrover.common.BasePage;

public class MultibranchPipelineProjectPage extends BasePage {

    public MultibranchPipelineProjectPage(WebDriver driver) {
        super(driver);
    }

    public MultibranchPipelineConfigurationPage clickConfigureLinkInSideMenu() {
        getWait5().until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("a[href='./configure']")))
                .click();

        return new MultibranchPipelineConfigurationPage(getDriver());
    }

    public String getDescription() {
        return getWait2().until(ExpectedConditions.visibilityOfElementLocated(By.id("view-message"))).getText();
    }

    public String getDisabledText() {
        return getWait5().until(ExpectedConditions.visibilityOfElementLocated(By.id("disabled-message"))).getText();
    }

    public MultibranchPipelineConfirmRenamePage clickRenameLinkInSideMenu() {
        getWait2().until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("a[href$='/confirm-rename']")))
                .click();

        return new MultibranchPipelineConfirmRenamePage(getDriver());
    }

    public String getHeading() {
        return getDriver().findElement(By.tagName("h1")).getText();
    }

    public MultibranchPipelineProjectPage clickAddDescriptionLink() {
        getWait2().until(ExpectedConditions.elementToBeClickable(By.id("description-link"))).click();

        return this;
    }

    public MultibranchPipelineProjectPage sendDescription(String description) {
        WebElement descriptionField = getDriver().findElement(By.name("description"));

        descriptionField.clear();
        descriptionField.sendKeys(description);

        return this;
    }

    public String getDescriptionFieldText() {
        return getDriver()
                .findElement(By.name("description"))
                .getShadowRoot()
                .findElement(By.cssSelector("div"))
                .getText();
    }

    public WebElement getAddDescriptionLink() {
        return getDriver().findElement(By.id("description-link"));
    }
}
