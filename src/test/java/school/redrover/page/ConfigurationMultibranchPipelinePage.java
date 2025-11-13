package school.redrover.page;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import school.redrover.common.BasePage;

public class ConfigurationMultibranchPipelinePage extends BasePage {

    public ConfigurationMultibranchPipelinePage(WebDriver driver) {
        super(driver);
    }

    public MultibranchPipelineProjectPage submitForm() {
        getDriver().findElement(By.tagName("form")).submit();

        return new MultibranchPipelineProjectPage(getDriver());
    }

    public ConfigurationMultibranchPipelinePage clickToggle() {
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
}
