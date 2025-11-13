package school.redrover.page;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
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

    public ConfigurationPipelinePage toggleEnableSwitch() {
        WebElement enableDisableToggle = getWait5().until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//span[@id='toggle-switch-enable-disable-project']")));
        enableDisableToggle.click();

        return new ConfigurationPipelinePage(getDriver());
    }

    public PipelinePage clickSubmitButton() {
        getDriver().findElement(By.name("Submit")).click();

        return new PipelinePage(getDriver());
    }
}
