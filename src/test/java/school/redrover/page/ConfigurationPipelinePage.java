package school.redrover.page;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import school.redrover.common.BasePage;

public class ConfigurationPipelinePage extends BasePage {

    public ConfigurationPipelinePage(WebDriver driver) {
        super(driver);
    }

    public ConfigurationPipelinePage ScrollDownToAdvancedSection() {
        ((JavascriptExecutor) getDriver()).executeScript(
                "arguments[0].scrollIntoView(true);",
                getDriver().findElement(By.id("advanced")));

        return this;
    }

    public String getAdvancedTitleText() {
        return getWait5()
                .until(ExpectedConditions.visibilityOfElementLocated(By.id("advanced")))
                .getText();
    }
}
