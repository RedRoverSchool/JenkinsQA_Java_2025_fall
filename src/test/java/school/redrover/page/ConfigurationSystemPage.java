package school.redrover.page;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import school.redrover.common.BasePage;

public class ConfigurationSystemPage extends BasePage {

    public ConfigurationSystemPage(WebDriver driver) {
        super(driver);
    }

    public ConfigurationSystemPage setSystemMessage(String message) {
        WebElement input = getDriver().findElement(By.name("system_message"));
        input.sendKeys(message);

        return this;
    }

    public HomePage clickSave() {
        WebElement saveButton = getDriver().findElement(By.name("Submit"));
        saveButton.click();

        getWait5().until(ExpectedConditions.invisibilityOf(saveButton));
        return new HomePage(getDriver());
    }

    public String getPreviewSystemMessage() {
        getDriver().findElement(By.className("textarea-show-preview")).click();

        return getWait5().until(ExpectedConditions.visibilityOfElementLocated(
                By.className("textarea-preview"))).getText();
    }
}
