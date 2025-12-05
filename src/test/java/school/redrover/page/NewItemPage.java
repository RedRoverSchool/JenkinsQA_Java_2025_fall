package school.redrover.page;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import school.redrover.common.TestUtils;

import java.util.Objects;

public class NewItemPage extends BaseSideMenuItemPage {

    @FindBy(id = "name")
    private WebElement nameField;

    @FindBy(xpath = "//span[text()='Multibranch Pipeline']")
    private WebElement multibranchPipelineOption;

    @FindBy(className = "hudson_model_FreeStyleProject")
    private WebElement freestyleProjectOption;

    @FindBy(id = "ok-button")
    private WebElement okButton;

    @FindBy(className = "hudson_matrix_MatrixProject")
    private WebElement multiConfigurationProject;

    @FindBy(css = "[class$='WorkflowJob']")
    private WebElement pipelineType;

    @FindBy(xpath = "//*[contains(@class, 'WorkflowJob')]")
    private WebElement pipelineTypeCheck;

    public NewItemPage(WebDriver driver) {
        super(driver);
    }

    @Override
    protected void waitUntilPageLoad() {
        getWait10().until(ExpectedConditions.visibilityOf(nameField));
    }

    public NewItemPage sendName(String name) {
        nameField.sendKeys(name);

        return this;
    }

    public NewItemPage clearSendName() {
        nameField.clear();

        return this;
    }

    public NewItemPage selectFolder() {
        getDriver().findElement(By.xpath("//*[@id='j-add-item-type-nested-projects']/ul/li[1]")).click();

        return this;
    }

    public FolderConfigurationPage selectFolderAndSubmit() {
        getDriver().findElement(By.xpath("//*[@id='j-add-item-type-nested-projects']/ul/li[1]")).click();

        getWait2().until(ExpectedConditions.elementToBeClickable(By.id("ok-button"))).click();
        getWait2().until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//span[text() = 'General']")));

        return new FolderConfigurationPage(getDriver());

    }

    public NewItemPage selectMultibranchPipeline() {
        multibranchPipelineOption.click();

        return this;
    }

    public MultibranchPipelineConfigurationPage selectMultibranchPipelineAndSubmit() {
        TestUtils.clickJS(getDriver(), multibranchPipelineOption);

        getWait2().until(ExpectedConditions.elementToBeClickable(okButton)).click();
        getWait2().until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//span[text() = 'General']")));

        return new MultibranchPipelineConfigurationPage(getDriver());
    }

    public PipelineConfigurationPage selectPipelineAndSubmit() {
        getDriver().findElement(By.xpath("//span[text()='Pipeline']")).click();

        getWait5().until(ExpectedConditions.elementToBeClickable(By.id("ok-button"))).click();
        getWait5().until(ExpectedConditions.visibilityOfElementLocated(By.id("general")));

        return new PipelineConfigurationPage(getDriver());
    }

    public String getDuplicateOrUnsafeCharacterErrorMessage() {
        WebElement errorMessage = getWait10().until(
                ExpectedConditions.visibilityOfElementLocated(By.id("itemname-invalid")));

        return errorMessage.getText();
    }

    public FreestyleProjectConfigurationPage selectFreestyleProjectAndSubmit() {
        freestyleProjectOption.click();

        getWait2().until(ExpectedConditions.elementToBeClickable(okButton)).click();
        getWait2().until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//h2[@id = 'general']")));

        return new FreestyleProjectConfigurationPage(getDriver());
    }

    public NewItemPage sendNameToCopyFromAndSubmit(String name) {
        getDriver().findElement(By.id("from")).sendKeys(name);

        getDriver().findElement(By.id("ok-button")).click();

        return this;
    }


    public MultibranchPipelineConfigurationPage selectMultiConfigurationAndSubmit() {
        TestUtils.clickJS(getDriver(), By.xpath("//span[text()='Multi-configuration project']"));

        getWait2().until(ExpectedConditions.elementToBeClickable(By.id("ok-button"))).click();
        getWait2().until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//span[contains(text(), 'General')]")));

        return new MultibranchPipelineConfigurationPage(getDriver());
    }

    public OrganizationFolderConfigurationPage selectOrganizationFolderAndSubmit() {
        TestUtils.clickJS(getDriver(), By.xpath("//span[text()='Organization Folder']"));

        getWait2().until(ExpectedConditions.elementToBeClickable(By.id("ok-button"))).click();
        getWait5().until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//span[contains(text(), 'General')]")));

        return new OrganizationFolderConfigurationPage(getDriver());
    }

    public HomePage selectItemTypeAndSubmitAndGoHome(String itemType) {
        switch (itemType) {
            case "Folder":
                selectFolderAndSubmit().gotoHomePage();
                break;
            case "Freestyle project":
                selectFreestyleProjectAndSubmit().gotoHomePage();
                break;
            case "Pipeline":
                selectPipelineAndSubmit().gotoHomePage();
                break;
            case "Multi-configuration project":
                selectMultiConfigurationAndSubmit().gotoHomePage();
                break;
            case "Multibranch Pipeline":
                selectMultibranchPipelineAndSubmit().gotoHomePage();
                break;
            case "Organization Folder":
                selectOrganizationFolderAndSubmit().gotoHomePage();
                break;
            default:
                throw new IllegalArgumentException("Unknown item type: " + itemType);
        }
        return new HomePage(getDriver());
    }

    public NewItemPage selectPipeline() {
        pipelineType.click();

        return this;
    }

    public boolean isPipelineSelected() {

        return "true".equals(pipelineTypeCheck.getAttribute("aria-checked"));
    }

    public boolean isPipelineHighlighted() {

        return Objects.requireNonNull(pipelineTypeCheck.getAttribute("class")).contains("active");
    }

    public boolean isOkButtonEnabled() {

        return okButton.isEnabled();
    }

    public String getTextHintFromCopyField() {

        return getDriver().findElement(By.xpath("//p[@class='jenkins-form-label']")).getText();
    }

    public NewItemPage findCopyFromField() {
        getDriver().findElement(By.id("from"));

        return this;
    }

    public String getNameDataValid() {

        return getDriver().findElement(By.id("name")).getAttribute("data-valid");
    }

    public MultiConfigurationProjectConfigurationPage selectMultiConfigurationProjectAndSubmit() {
        multiConfigurationProject.click();
        getWait2().until(ExpectedConditions.elementToBeClickable(okButton)).click();

        getWait5().until(ExpectedConditions.visibilityOfElementLocated(By.id("general")));

        return new MultiConfigurationProjectConfigurationPage(getDriver());
    }

    public NewItemPage clickOkButton() {
        getDriver().findElement(By.id("ok-button")).click();

        return this;
    }

    public String getErrorDisplayedForEmptyItemName() {
        return getDriver().findElement(By.id("itemname-required")).getText();
    }
}
