package school.redrover.page;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import school.redrover.common.BasePage;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class FreestyleProjectConfigurationPage extends BasePage {

    @FindBy(name = "description")
    private WebElement descriptionInput;

    @FindBy(xpath = "//label[text()='Discard old builds']")
    private WebElement oldBuildsCheck;

    @FindBy(name = "_.daysToKeepStr")
    private WebElement daysToKeepStrCheck;

    @FindBy(name = "_.numToKeepStr")
    private WebElement numToKeepStrCheck;

    @FindBy(id = "triggers")
    private WebElement triggersTitle;

    @FindBy(xpath = "//button[@data-section-id='triggers']")
    private WebElement triggersLinkSideMenu;

    @FindBy(xpath = "//div[@class='jenkins-section__description' and contains(text(), 'Set up automated actions')]")
    private WebElement triggersDescription;

    @FindBy(xpath = "//section[@class='jenkins-section'][2]//label[@class='attach-previous ']")
    private List<WebElement> checkboxTriggersLabels;

    @FindBy(xpath = "//div[@id='source-code-management']/following-sibling::div[contains(@class, 'jenkins-section__description')]")
    private WebElement sourceCodeManagementDescription;

    @FindBy(xpath = "//button[@data-section-id='environment']")
    private WebElement clickEnvironmentMenuOption;

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
        return getDriver().findElements(By.cssSelector("[name]"))
                .stream()
                .filter(element ->
                        Objects.equals(element.getAttribute("name"), "description") ||
                                Objects.equals(element.getAttribute("name"), "_.daysToKeepStr") ||
                                Objects.equals(element.getAttribute("name"), "_.numToKeepStr") ||
                                Objects.equals(element.getAttribute("name"), "_.url") ||
                                Objects.equals(element.getAttribute("name"), "authToken"))
                .map(element -> element.getAttribute("value"))
                .toList();
    }

    public String getSCMTitleText() {
        return getWait5().until(ExpectedConditions.visibilityOfElementLocated(By.id("source-code-management"))).getText();
    }

    public FreestyleProjectConfigurationPage clickSourceCodeManagementMenuOption() {
        getDriver().findElement(By.xpath("//button[@data-section-id='source-code-management']")).click();

        return this;
    }

    public FreestyleProjectConfigurationPage clickEnvironmentMenuOption() {
        clickEnvironmentMenuOption.click();

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

    public String getScmDescriptionText() {
       return sourceCodeManagementDescription.getText();
    }

    public String getSelectedRadioLabel() {
        WebElement selectedInput = getDriver().findElement(By.xpath("//input[@name='scm' and @checked='true']"));
        String inputId = selectedInput.getAttribute("id");
        WebElement linkedLabel = getDriver().findElement(By.xpath("//label[@for='%s']".formatted(inputId)));
        String labelText = linkedLabel.getText();

        return labelText;
    }

    public String getConfigUrl() {
        return getDriver().getCurrentUrl();
    }

    public FreestyleProjectConfigurationPage refreshPage() {
        getDriver().navigate().refresh();

        return this;
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

    public FreestyleProjectConfigurationPage clickTriggersLinkInSideMenu() {
        triggersLinkSideMenu.click();

        return this;
    }

    public FreestyleProjectConfigurationPage clickEnableDisableProject() {
        getWait5().until(ExpectedConditions
                .visibilityOfElementLocated(By.cssSelector("#toggle-switch-enable-disable-project"))).click();
        return this;
    }

    public String getTriggerTitleText() {
        return triggersTitle.getText();
    }

    public String getTriggersDescriptionText() {
        return triggersDescription.getText();
    }

    public String getBreadcrumbItem() {
        return getWait10().until(ExpectedConditions.visibilityOfElementLocated(By
                .xpath("//span[contains(text(),'Configuration')]"))).getText();
    }

    public List<String> getTriggerCheckboxLabels() {
        return checkboxTriggersLabels.stream()
                .limit(5)
                .map(WebElement::getText)
                .collect(Collectors.toList());
    }
}
