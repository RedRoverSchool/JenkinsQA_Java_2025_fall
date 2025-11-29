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

    @FindBy(id = "description-link")
    private WebElement descriptionButton;

    @FindBy(xpath = "//a[@href = 'editDescription']")
    private WebElement editDescriptionButton;

    @FindBy(name = "description")
    private WebElement descriptionTextarea;

    @FindBy(name = "Submit")
    private WebElement descriptionSubmitButton;

    @FindBy(id = "description-content")
    private WebElement descriptionContent;

    @FindBy(xpath = "//a[@data-build-success='Build scheduled']")
    private WebElement buildNow;

    @FindBy(className = "confirmation-link")
    private WebElement deletePipeline;

    @FindBy(xpath = "//a[@href='/job/PipelineName/pipeline-syntax']")
    private WebElement pipelineSyntax;

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
        descriptionTextarea.sendKeys(description);
        descriptionSubmitButton.click();
        getWait5().until(ExpectedConditions.elementToBeClickable(By.id("description-link")));

        return this;
    }

    public String getDescription() {
        return descriptionContent.getText();
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
        descriptionButton.click();
        return this;
    }

    public PipelinePage clickEditDescriptionButton() {
        editDescriptionButton.click();
        return this;
    }

    public PipelinePage clickBuildNow() {
        buildNow.click();
        return this;
    }

    public PipelinePage clickDeletePipeline() {
        deletePipeline.click();
        return this;
    }

    public PipelineSyntaxPage clickPipelineSyntax() {
        pipelineSyntax.click();
        return new PipelineSyntaxPage(getDriver());
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