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

    public ConfigurationSystemPage setSystemMessage(String systemMessage) {
        WebElement systemMessageTextArea = getDriver().findElement(By.name("system_message"));
        systemMessageTextArea.sendKeys(systemMessage);

        return this;
    }

    public ConfigurationSystemPage clearSystemMessage() {
        WebElement systemMessageTextArea = getDriver().findElement(By.name("system_message"));
        systemMessageTextArea.clear();

        return this;
    }

    public String getPreviewSystemMessage() {
        getDriver().findElement(By.className("textarea-show-preview")).click();

        return getWait2().until(ExpectedConditions.visibilityOfElementLocated(
                By.className("textarea-preview"))).getText();
    }

    public ConfigurationSystemPage setNumberOfExecutors(String numberOfExecutors) {
        WebElement systemMessageTextArea = getDriver().findElement(By.name("_.numExecutors"));
        systemMessageTextArea.clear();
        systemMessageTextArea.sendKeys(numberOfExecutors);

        return this;
    }

    public void clickSave() {
        WebElement saveButton = getDriver().findElement(By.name("Submit"));
        saveButton.click();
    }

}
