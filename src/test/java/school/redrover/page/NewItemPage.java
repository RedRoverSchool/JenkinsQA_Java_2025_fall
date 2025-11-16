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

    public NewItemPage selectFolder() {
        getDriver().findElement(By.xpath("//*[@id='j-add-item-type-nested-projects']/ul/li[1]")).click();

        return this;
    }

    public ConfigurationFolderPage selectFolderAndSubmit() {
        getDriver().findElement(By.xpath("//*[@id='j-add-item-type-nested-projects']/ul/li[1]")).click();

        getWait2().until(ExpectedConditions.elementToBeClickable(By.id("ok-button"))).click();
        getWait2().until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//span[text() = 'General']")));

        return new ConfigurationFolderPage(getDriver());

    }

    public MultibranchPipelineConfigPage selectMultibranchPipelineAndSubmit() {
        getDriver().findElement(By.cssSelector("[class$='MultiBranchProject']")).click();

        getWait5().until(ExpectedConditions.elementToBeClickable(By.id("ok-button"))).click();
        getWait2().until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//span[text() = 'General']")));

        return new MultibranchPipelineConfigPage(getDriver());
    }

    public MultibranchPipelineConfigPage selectMultibranchPipelineWithJsAndSubmit() {
        TestUtils.clickJS(getDriver(), By.cssSelector("[class$='MultiBranchProject']"));

        getWait2().until(ExpectedConditions.elementToBeClickable(By.id("ok-button"))).click();
        getWait2().until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//span[text() = 'General']")));

        return new MultibranchPipelineConfigPage(getDriver());
    }

    public ConfigurationPipelinePage selectPipelineAndSubmit() {
        getDriver().findElement(By.xpath("//span[text()='Pipeline']")).click();

        getWait5().until(ExpectedConditions.elementToBeClickable(By.id("ok-button"))).click();
        getWait5().until(ExpectedConditions.visibilityOfElementLocated(By.id("general")));

        return new ConfigurationPipelinePage(getDriver());
    }

    public String getDuplicateErrorMessage() {
        WebElement errorMessage = getWait10().until(
                ExpectedConditions.visibilityOfElementLocated(By.id("itemname-invalid")));
        return errorMessage.getText();
    }

    public ConfigurationFreestyleProjectPage selectFreestyleProjectAndSubmit() {
        getDriver().findElement(By.className("hudson_model_FreeStyleProject")).click();
        getDriver().findElement(By.id("ok-button")).click();

        getWait2().until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//h2[@id = 'general']")));

        return new ConfigurationFreestyleProjectPage(getDriver());
    }

    public HomePage sendNewNameAndOriginalNameAndSubmit (String newItemName, String originalItemName) {
        this.sendName(newItemName);
        getDriver().findElement(By.id("from")).sendKeys(originalItemName);

        getDriver().findElement(By.id("ok-button")).click();

        return new HomePage(getDriver());
    }

    public MultibranchPipelineConfigPage selectMultiConfigurationAndSubmit() {
        TestUtils.clickJS(getDriver(), By.xpath("//span[text()='Multi-configuration project']"));

        getWait2().until(ExpectedConditions.elementToBeClickable(By.id("ok-button"))).click();
        getWait2().until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//span[contains(text(), 'General')]")));

        return new MultibranchPipelineConfigPage(getDriver());
    }

    public ConfigurationOrganizationFolderPage selectOrganizationFolderAndSubmit() {
        TestUtils.clickJS(getDriver(), By.xpath("//span[text()='Organization Folder']"));

        getWait2().until(ExpectedConditions.elementToBeClickable(By.id("ok-button"))).click();
        getWait2().until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//span[contains(text(), 'General')]")));

        return new ConfigurationOrganizationFolderPage(getDriver());
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
                selectMultibranchPipelineWithJsAndSubmit().gotoHomePage();
                break;
            case "Organization Folder":
                selectOrganizationFolderAndSubmit().gotoHomePage();
                break;
            default:
                throw new IllegalArgumentException("Unknown item type: " + itemType);
        }
        return new HomePage(getDriver());
    }
}
