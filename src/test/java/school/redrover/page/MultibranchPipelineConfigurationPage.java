package school.redrover.page;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import school.redrover.common.BasePage;

public class MultibranchPipelineConfigurationPage extends BasePage {

    public MultibranchPipelineConfigurationPage(WebDriver driver) {
        super(driver);
    }

    public MultibranchPipelineConfigurationPage sendDisplayName(String name) {
        getDriver().findElement(By.xpath("//input[@name='_.displayNameOrNull']")).sendKeys(name);

        return this;
    }

    public MultibranchPipelineProjectPage clickSaveButton() {
        getDriver().findElement(By.name("Submit")).click();
        getWait5().until(ExpectedConditions.visibilityOfAllElementsLocatedBy(
                By.cssSelector(".empty-state-section h2")));

        return new MultibranchPipelineProjectPage(getDriver());
    }

    public MultibranchPipelineConfigurationPage clickApply() {
        getWait2().until(ExpectedConditions.visibilityOfElementLocated(By.name("Apply"))).click();

        return this;
    }

    public String getSavedMessage() {
        return getWait5().until(ExpectedConditions.
                visibilityOfElementLocated(By.xpath("//span[text() = 'Saved']"))).getText();
    }

    public MultibranchPipelineConfigurationPage clickToggle() {
        getWait2().until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("[data-title='Disabled']"))).click();

        return this;
    }

    public String getToggleState() {
        try {
            return getWait5().until(ExpectedConditions.visibilityOfElementLocated(By.className("jenkins-toggle-switch__label__unchecked-title")))
                    .getText();
        } catch (Exception ignore) {
        }

        return "Enabled";
    }

    public String getToggleTooltipTextOnHover() {
        WebElement toggleElement = getDriver().findElement(By.id("toggle-switch-enable-disable-project"));

        new Actions(getDriver()).moveToElement(toggleElement).perform();

        return getWait5().until(ExpectedConditions.visibilityOfElementLocated(By.className("tippy-content")))
                .getText();
    }

    public MultibranchPipelineConfigurationPage sendDescription(String description) {
        WebElement descriptionField = getDriver().findElement(By.name("_.description"));

        descriptionField.clear();
        descriptionField.sendKeys(description);

        return this;
    }

    public String getJobDescriptionPreviewText() {
        getDriver().findElement(By.className("textarea-show-preview")).click();

        return getWait5().until(ExpectedConditions.visibilityOfElementLocated(By.className("textarea-preview")))
                .getText();
    }
}
