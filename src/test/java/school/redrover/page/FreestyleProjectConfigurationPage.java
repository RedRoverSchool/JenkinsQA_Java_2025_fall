package school.redrover.page;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import school.redrover.common.BasePage;

import javax.swing.*;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

public class FreestyleProjectConfigurationPage extends BasePage {

    @FindBy(name = "description")
    private WebElement descriptionInput;

    @FindBy(xpath = "//label[text()='Discard old builds']")
    private WebElement oldBuildsCheck;

    @FindBy(name = "_.daysToKeepStr")
    private WebElement daysToKeepStrCheck;

    @FindBy(name = "_.numToKeepStr")
    private WebElement numToKeepStrCheck;

    public FreestyleProjectConfigurationPage(WebDriver driver) {
        super(driver);
    }

    public FreestyleProjectConfigurationPage setDescription(String description) {
        descriptionInput.sendKeys(description);

        return this;
    }

    public FreestyleProjectConfigurationPage setCheckBoxDiscardAndSetDaysNum(String daysToKeep, String maxOfBuilds) {
        oldBuildsCheck.click();

        daysToKeepStrCheck.sendKeys(daysToKeep);
        numToKeepStrCheck.sendKeys(maxOfBuilds);

        return this;
    }

    public FreestyleProjectConfigurationPage setCheckBoxGitHubAndSendUrl(String url) {
        WebElement checkBox = getDriver().findElement(By.xpath("//label[text()='Git']"));

        JavascriptExecutor js = (JavascriptExecutor) getDriver();
        js.executeScript("arguments[0].scrollIntoView(true);", checkBox);
        checkBox.click();

        getDriver().findElement(By.name("_.url")).sendKeys(url);

        return this;
    }

    public FreestyleProjectConfigurationPage setCheckBoxTriggerBuildsAndSendUrl(String url) {
        WebElement checkBox = getDriver().findElement(
                By.xpath("//label[text()='Trigger builds remotely (e.g., from scripts)']"));

        JavascriptExecutor js = (JavascriptExecutor) getDriver();
        js.executeScript("arguments[0].scrollIntoView(true);", checkBox);
        checkBox.click();

        getDriver().findElement(By.name("authToken")).sendKeys(url);

        return this;
    }

    public FreestyleProjectPage clickSave() {
        getDriver().findElement(By.name("Submit")).click();

        getWait5().until(ExpectedConditions.presenceOfElementLocated(By.tagName("h1")));
        return new FreestyleProjectPage(getDriver());
    }

    public boolean isSaveButtonDisplayed() {
        return getWait2().until(ExpectedConditions.elementToBeClickable(By.name("Submit"))).isDisplayed();
    }

    public List<String> getSettingsToList() {
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

    public String getSCMTitleText() {
        return getWait5().until(ExpectedConditions.visibilityOfElementLocated(By.id("source-code-management"))).getText();
    }

    public FreestyleProjectConfigurationPage clickSourceCodeManagementMenuOption() {
        getDriver().findElement(By.xpath("//button[@data-section-id='source-code-management']")).click();

        return this;
    }

    public FreestyleProjectConfigurationPage clickBuildStepMenuOption() {

        WebElement addBuildStep = getWait2().until(
                ExpectedConditions.visibilityOfElementLocated(By.xpath("//button[contains(text(),'Add build')]"))
        );

        ((JavascriptExecutor) getDriver()).executeScript("arguments[0].scrollIntoView({block: 'center'});", addBuildStep);
        addBuildStep.click();

        getWait2().until(ExpectedConditions.visibilityOfElementLocated(
                By.xpath("//button[normalize-space()='Execute Windows batch command']")));

        return this;
    }

    public FreestyleProjectConfigurationPage scrollToSourceCodeManagementWithJS() {
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

    public WebElement clickFilterBuildStep() {
        clickBuildStepMenuOption();
        return getDriver().findElement(By.xpath("//input[@type='search' and @placeholder='Filter']"));
    }

    public WebElement verifySentNameIsInFilter(String buildStep) {
        return new WebDriverWait(getDriver(), Duration.ofSeconds(5))
                .until(ExpectedConditions.visibilityOfElementLocated
                        (By.xpath("//button[contains(@style,'inline-flex') and normalize-space()='%s']".formatted(buildStep))));
    }

    public FreestyleProjectConfigurationPage typeIntoFilterBuildStep(String text) {
        WebElement filter = getDriver().findElement(By.xpath("//input[@type='search' and @placeholder='Filter']"));
        filter.clear();
        filter.sendKeys(text);
        return this;
    }

    public FreestyleProjectConfigurationPage clickTriggerLinkInSideMenu() {
        getDriver().findElement(By.xpath("//button[@data-section-id='triggers']")).click();

        return this;
    }

    public String getTriggerTitleText () {
        return getWait5().until(ExpectedConditions.presenceOfElementLocated(By.id("triggers")))
                .getText();
    }
}
