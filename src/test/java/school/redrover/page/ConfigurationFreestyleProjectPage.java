package school.redrover.page;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import school.redrover.common.BasePage;
import java.util.ArrayList;
import java.util.List;

public class ConfigurationFreestyleProjectPage extends BasePage {

    public ConfigurationFreestyleProjectPage(WebDriver driver) {
        super(driver);
    }

    public ConfigurationFreestyleProjectPage setDescription(String description) {
        getDriver().findElement(By.name("description")).sendKeys(description);

        return this;
    }

    public ConfigurationFreestyleProjectPage setCheckBoxDiscardAndSetDaysNum(String daysToKeep, String maxOfBuilds) {
        getDriver().findElement(By.xpath("//label[text()='Discard old builds']")).click();

        getDriver().findElement(By.name("_.daysToKeepStr")).sendKeys(daysToKeep);
        getDriver().findElement(By.name("_.numToKeepStr")).sendKeys(maxOfBuilds);

        return this;
    }

    public ConfigurationFreestyleProjectPage setCheckBoxGitHubAndSendUrl(String url) {
        WebElement checkBox = getDriver().findElement(By.xpath("//label[text()='Git']"));

        JavascriptExecutor js = (JavascriptExecutor) getDriver();
        js.executeScript("arguments[0].scrollIntoView(true);", checkBox);
        checkBox.click();

        getDriver().findElement(By.name("_.url")).sendKeys(url);

        return this;
    }

    public ConfigurationFreestyleProjectPage setCheckBoxTriggerBuildsAndSendUrl(String url) {
        WebElement checkBox = getDriver().findElement(
                By.xpath("//label[text()='Trigger builds remotely (e.g., from scripts)']"));

        JavascriptExecutor js = (JavascriptExecutor) getDriver();
        js.executeScript("arguments[0].scrollIntoView(true);", checkBox);
        checkBox.click();

        getDriver().findElement(By.name("authToken")).sendKeys(url);

        return this;
    }

    public ConfigurationFreestyleProjectPage clickSave() {
        getDriver().findElement(By.name("Submit")).click();

        return this;
    }

    public WebElement getSaveButton() {
        return getWait2().until(ExpectedConditions.elementToBeClickable(By.name("Submit")));
    }

    public WebElement getApplyButton() {
        return getWait2().until(ExpectedConditions.elementToBeClickable(By.name("Apply")));
    }

    public List<String> getSomeSettingsToList(){
        List<String> settingsList = new ArrayList<>();

        settingsList.add(getDriver().findElement(By.name("description")).getText());
        settingsList.add(getDriver().findElement(By.name("_.daysToKeepStr")).getAttribute("value"));
        settingsList.add(getDriver().findElement(By.name("_.numToKeepStr")).getAttribute("value"));
        JavascriptExecutor js = (JavascriptExecutor) getDriver();
        js.executeScript("window.scrollBy(0, 800);");
        settingsList.add(getDriver().findElement(By.name("_.url")).getAttribute("value"));
        js.executeScript("window.scrollBy(0, 800);");
        settingsList.add(getDriver().findElement(By.name("authToken")).getAttribute("value"));

        return settingsList;
    }

    public WebElement verifySCMTitleIsVisible() {
        WebElement scmTitle = getDriver().findElement(By.id("source-code-management"));

        return getWait5().until(ExpectedConditions.visibilityOf(scmTitle));
    }

    public ConfigurationFreestyleProjectPage clickSourceCodeManagementMenuOption() {
        getDriver().findElement(By.xpath("//button[@data-section-id='source-code-management']")).click();

        return this;
    }

    public ConfigurationFreestyleProjectPage scrollToSourceCodeManagementWithJS() {
        WebElement scmTitle = getDriver().findElement(By.xpath("//div[@id='source-code-management']"));
        ((JavascriptExecutor) getDriver()).executeScript("arguments[0].scrollIntoView(true);", scmTitle);

        return this;
    }

    public WebElement getScmDescription() {
        return getDriver().findElement(By.xpath(
                "//div[normalize-space()='Connect and manage your code repository to automatically pull the latest code for your builds.']"));
    }

    public String getSelectedRadioLabel() {
        WebElement selectedInput = getDriver().findElement(By.xpath("//input[@name='scm' and @checked='true']"));
        String inputId = selectedInput.getAttribute("id");
        WebElement linkedLabel = getDriver().findElement(By.xpath("//label[@for='%s']".formatted(inputId)));
        String labelText = linkedLabel.getText();

        return labelText;
    }

    public boolean isGitOptionDisplayed() {
        return getDriver().findElement(By.xpath("//label[normalize-space(text())='Git']")).isDisplayed();
    }

    public String getGitTooltipText() {
        return getDriver().findElement(By.xpath("//a[@title='Help for feature: Git']")).getAttribute("tooltip");
    }
}
