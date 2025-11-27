package school.redrover.page;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import school.redrover.common.BasePage;
import school.redrover.common.TestUtils;

public class NewItemPage extends BasePage {

    public NewItemPage(WebDriver driver) {
        super(driver);
    }

    public NewItemPage sendName(String name) {
        getWait2().until(ExpectedConditions.visibilityOfElementLocated(By.id("name"))).sendKeys(name);

        return this;
    }

    public NewItemPage clearSendName() {
        getWait2().until(ExpectedConditions.visibilityOfElementLocated(By.id("name"))).clear();

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
        getDriver().findElement(By.cssSelector("[class$='MultiBranchProject']")).click();

        return this;
    }

    public MultibranchPipelineConfigurationPage selectMultibranchPipelineAndSubmit() {
        TestUtils.clickJS(getDriver(), By.xpath("//span[text()='Multibranch Pipeline']"));

        getWait5().until(ExpectedConditions.elementToBeClickable(By.id("ok-button"))).click();
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
        getDriver().findElement(By.className("hudson_model_FreeStyleProject")).click();

        getWait2().until(ExpectedConditions.elementToBeClickable(By.id("ok-button"))).click();
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
        getWait2().until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//span[contains(text(), 'General')]")));

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
        getDriver().findElement(By.cssSelector("[class$='WorkflowJob']")).click();

        return this;
    }

    public boolean isPipelineSelected() {
        WebElement pipelineType = getDriver().findElement(By.xpath("//*[contains(@class, 'WorkflowJob')]"));

        return "true".equals(pipelineType.getAttribute("aria-checked"));
    }

    public boolean isPipelineHighlighted() {
        WebElement pipelineType = getDriver().findElement(By.xpath("//*[contains(@class, 'WorkflowJob')]"));

        return pipelineType.getAttribute("class").contains("active");
    }

    public boolean isOkButtonEnabled() {

        return getDriver().findElement(By.id("ok-button")).isEnabled();
    }

    public String getTextHintFromCopyField() {

        return getDriver().findElement(By.xpath("//p[@class='jenkins-form-label']")).getText();
    }

    public NewItemPage findCopyFromField() {
        getDriver().findElement(By.id("from"));

        return this;
    }

    public String getHeadingText() {

        return getDriver().findElement(By.tagName("h1")).getText();
    }

    public String getNameDataValid() {

        return getDriver().findElement(By.id("name")).getAttribute("data-valid");
    }

    public RestApiPage clickRestApiLink() {
        getWait5().until(ExpectedConditions.elementToBeClickable(By.xpath("//a[@href='api/']"))).click();

        return new RestApiPage(getDriver());
    }
    public MultiConfigurationProjectPage selectMultiConfigurationProjectAndSubmit() {
        getDriver().findElement(By.cssSelector("[class='hudson_matrix_MatrixProject']")).click();
        getWait5().until(ExpectedConditions.elementToBeClickable(By.id("ok-button"))).click();

        return new MultiConfigurationProjectPage(getDriver());
    }
    public NewItemPage clickOkButton() {
        getDriver().findElement(By.id("ok-button")).click();

        return this;
    }
    public String getErrorDisplayedForEmptyItemName() {
        return getDriver().findElement(By.id("itemname-required")).getText();
    }
}
