package school.redrover.page;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import school.redrover.common.BasePage;

public class MultibranchPipelineConfigPage extends BasePage {

    public MultibranchPipelineConfigPage(WebDriver driver) {
        super(driver);
    }

    public MultibranchPipelineConfigPage sendDisplayName(String name) {
        getDriver().findElement(By.xpath("//input[@name='_.displayNameOrNull']")).sendKeys(name);

        return this;
    }

    public MultibranchPipelineJobPage clickSaveButton() {
        getDriver().findElement(By.name("Submit")).click();
        getWait2().until(ExpectedConditions.visibilityOfAllElementsLocatedBy(
                By.cssSelector(".empty-state-section h2")));

        return new MultibranchPipelineJobPage(getDriver());
    }

    public MultibranchPipelineConfigPage clickToggle() {
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
        WebElement toggleElement = getWait5()
                .until(ExpectedConditions.visibilityOfElementLocated(By.id("toggle-switch-enable-disable-project")));

        new Actions(getDriver()).moveToElement(toggleElement).perform();

        return getWait5().until(ExpectedConditions.visibilityOfElementLocated(By.className("tippy-content")))
                .getText();
    }

    public MultibranchPipelineConfigPage enterDescription(String description) {
        getDriver().findElement(By.name("_.description")).sendKeys(description);

        return this;
    }

    public MultibranchPipelineConfigPage updateJobDescription(String description) {
        getDriver().findElement(By.name("_.description")).clear();

        return enterDescription(description);
    }

    public String getJobDescriptionPreviewText() {
        getDriver().findElement(By.className("textarea-show-preview")).click();

        return getWait5().until(ExpectedConditions.visibilityOfElementLocated(By.className("textarea-preview")))
                .getText();
    }
}
