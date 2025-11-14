package school.redrover.page;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import school.redrover.common.BasePage;

public class PipelinePage extends BasePage {

    public PipelinePage(WebDriver driver) {
        super(driver);
    }

    public ConfigurationPipelinePage clickConfigureInSideMenu(String newPipelineName) {
        getWait5().until(ExpectedConditions.visibilityOfElementLocated(By
                        .xpath(".//a[@href='/job/%s/configure']".formatted(newPipelineName))))
                .click();

        return new ConfigurationPipelinePage(getDriver());
    }

    public String getDisplayNameInStatus() {
        return getWait10().until(ExpectedConditions.visibilityOfElementLocated(By
                .tagName("h1"))).getText();
    }

    public String getDisplayNameInBreadcrumbBar(String displayName) {
        return getWait10().until(ExpectedConditions.visibilityOfElementLocated(By
                .xpath(".//a[text()='%s']".formatted(displayName)))).getText();
    }

    public ConfigurationPipelinePage getConfigurationPipelinePage() {
        WebElement configureOptionMenu = getDriver().findElement(By.xpath("//a[contains(@href, '/configure')]"));
        configureOptionMenu.click();

        return new ConfigurationPipelinePage(getDriver());
    }

    public String getMessageText() {
        return getDriver().findElement(By.id("enable-project")).getText();
    }
}