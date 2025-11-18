package school.redrover.page;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import school.redrover.common.BasePage;

public class MultibranchPipelineConfirmRenamePage extends BasePage {

    public MultibranchPipelineConfirmRenamePage(WebDriver driver) {
        super(driver);
    }

    public MultibranchPipelineConfirmRenamePage renameJob(String jobName) {
        WebElement newNameField = getWait2().until(ExpectedConditions.visibilityOfElementLocated(By.name("newName")));

        newNameField.clear();
        newNameField.sendKeys(jobName);

        return this;
    }

    public <T extends BasePage> T submitForm(T page) {
        getDriver().findElement(By.tagName("form")).submit();

        return page;
    }

    public MultibranchPipelineJobPage renameMultibranchPipeline(String jobName) {
        getDriver().findElement(By.cssSelector("[href$='confirm-rename']")).click();

        WebElement renameField = getDriver().findElement(By.name("newName"));
        renameField.clear();
        renameField.sendKeys(jobName + Keys.ENTER);

        getWait5().until(ExpectedConditions.not(ExpectedConditions.urlContains("confirm-rename")));

        return new MultibranchPipelineJobPage(getDriver());
    }
}
