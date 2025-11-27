package school.redrover.page;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import school.redrover.common.BasePage;

public class PipelinePage extends BasePage {

    @FindBy(xpath = "//a[contains(@href, '/configure')]")
    private WebElement configureMenuItem;

    public PipelinePage(WebDriver driver) {
        super(driver);
    }

    public PipelineConfigurationPage clickConfigureLinkInSideMenu() {
        configureMenuItem.click();

        getWait10().until(ExpectedConditions.elementToBeClickable(By.name("Submit")));
        return new PipelineConfigurationPage(getDriver());
    }

    public String getDisplayNameInStatus() {
        return getWait10().until(ExpectedConditions.visibilityOfElementLocated(By
                .tagName("h1"))).getText();
    }

    public String getDisplayNameInBreadcrumbBar(String displayName) {
        return getWait10().until(ExpectedConditions.visibilityOfElementLocated(By
                .xpath(".//a[text()='%s']".formatted(displayName)))).getText();
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

    public PipelinePage clearDescription() {
        getDriver().findElement(By.name("description")).clear();
        return this;
    }

    public String getWarningMessage() {
        return getWait5().until(ExpectedConditions.visibilityOfElementLocated(By.id("enable-project")))
                .getText();
    }

    public PipelinePage clickAddDescriptionButton() {
        getDriver().findElement(By.id("description-link")).click();
        return this;
    }

    public PipelinePage clickEditDescriptionButton() {
        getWait5()
                .until(ExpectedConditions.elementToBeClickable(By.xpath("//a[@href = 'editDescription']")))
                .click();
        return this;
    }

    public PipelinePage clickBuildNow() {
        getDriver().findElement(By.xpath("//a[@data-build-success='Build scheduled']"))
                .click();

        return this;
    }

    public PipelinePage clickDeletePipeline() {
        getDriver().findElement(By.className("confirmation-link"))
                .click();

        return this;
    }

    public PipelineHistoryPage clickBuildHistory() {
        getWait10()
                .until(ExpectedConditions.elementToBeClickable(By.id("jenkins-build-history")))
                .click();

        return new PipelineHistoryPage(getDriver());
    }

    public HomePage confirmDeleteAtJobPage() {
        getDriver().findElement(By.cssSelector("[data-id='ok']"))
                .click();

        return new HomePage(getDriver());
    }

    public PipelinePage cancelDelete() {
        WebElement yesButton = getWait2().until(
                ExpectedConditions.elementToBeClickable(
                        By.xpath("//dialog[@open]//button[@data-id='cancel']"))
        );
        yesButton.click();
        getWait5().until(ExpectedConditions.stalenessOf(yesButton));

        return this;
    }
}