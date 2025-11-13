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
        getWait2().until(ExpectedConditions.visibilityOf(getDriver().findElement(By.id("name")))).sendKeys(name);

        return this;
    }

    public ConfigurationFolderPage selectFolderAndSubmit() {
        getDriver().findElement(By.xpath("//*[@id='j-add-item-type-nested-projects']/ul/li[1]")).click();

        getWait2().until(ExpectedConditions.elementToBeClickable(By.id("ok-button"))).click();
        getWait2().until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//span[text() = 'General']")));

        return new ConfigurationFolderPage(getDriver());

    }

    public ConfigurationMultibranchPipelinePage selectMultibranchPipelineAndSubmit() {
        TestUtils.clickJS(getDriver(), By.cssSelector("[class$='MultiBranchProject']"));

        getWait5().until(ExpectedConditions.elementToBeClickable(By.id("ok-button"))).click();
        getWait2().until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//span[text() = 'General']")));

        return new ConfigurationMultibranchPipelinePage(getDriver());
    }

    public ConfigurationPipelinePage selectPipelineAndSubmit() {
        getDriver().findElement(By.xpath("//span[text()='Pipeline']")).click();

        getWait5().until(ExpectedConditions.elementToBeClickable(By.id("ok-button"))).click();
        getWait5().until(ExpectedConditions.visibilityOfElementLocated(By.id("general")));

        return new ConfigurationPipelinePage(getDriver());
    }

    public String getDuplicateErrorMessage() {
        WebElement errorMessage = getWait5().until(
                ExpectedConditions.visibilityOfElementLocated(By.id("itemname-invalid")));
        return errorMessage.getText();
    }
}
