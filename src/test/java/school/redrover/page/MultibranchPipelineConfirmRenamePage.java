package school.redrover.page;

import org.openqa.selenium.By;
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
}
