package school.redrover.page;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
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

    public PipelinePage clickAddDescriptionButton() {
        getDriver().findElement(By.id("description-link")).click();
        return this;
    }

    public PipelinePage addDescriptionAndSave(String description) {
        getDriver().findElement(By.name("description")).sendKeys(description);
        getDriver().findElement(By.name("Submit")).click();
        return this;
    }

    public String getDescription() {
        getWait5().until(ExpectedConditions.elementToBeClickable(By.id("description-link")));
        return getWait5()
                .until(ExpectedConditions.visibilityOfElementLocated(By.id("description-content")))
                .getText();
    }
}