package school.redrover.page;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import school.redrover.common.BasePage;

public class MultibranchPipelineConfirmRenamePage extends BasePage {

    @FindBy(name = "newName")
    private WebElement renameField;

    public MultibranchPipelineConfirmRenamePage(WebDriver driver) {
        super(driver);
    }

    public MultibranchPipelineConfirmRenamePage renameJob(String jobName) {
        renameField.clear();
        renameField.sendKeys(jobName);

        return this;
    }

    public ErrorPage submitForm() {
        getDriver().findElement(By.tagName("form")).submit();

        return new ErrorPage(getDriver());
    }

    public MultibranchPipelineProjectPage renameMultibranchPipeline(String jobName) {
        renameField.clear();
        renameField.sendKeys(jobName + Keys.ENTER);

        getWait5().until(ExpectedConditions.not(ExpectedConditions.urlContains("confirm-rename")));

        return new MultibranchPipelineProjectPage(getDriver());
    }
}
