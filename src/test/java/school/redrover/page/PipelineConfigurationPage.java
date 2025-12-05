package school.redrover.page;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;

import java.util.List;

public class PipelineConfigurationPage extends BaseSideMenuItemPage {

    @FindBy(id = "advanced")
    private WebElement advancedTitle;

    @FindBy(name = "Submit")
    private WebElement submitButton;

    @FindBy(xpath = "//button[text() = 'Apply']")
    private WebElement applyButton;

    @FindBy(xpath = "//button[@data-section-id='advanced']")
    private WebElement advancedMenuItem;

    @FindBy(xpath = "//div[@id='advanced']/parent::section/descendant::a[@tooltip]")
    private List<WebElement> tooltipList;

    @FindBy(xpath = "//div[@id='advanced']/parent::section/descendant::div[@class = 'help']")
    private WebElement helpElement;

    @FindBy(xpath = "//label[text()='Quiet period']")
    private WebElement quietPeriodLabel;

    @FindBy(name = "hasCustomQuietPeriod")
    private WebElement quietPeriodCheckbox;

    @FindBy(xpath = "//div[text()='Display Name']")
    private WebElement displayNameLabel;

    @FindBy(name = "_.displayNameOrNull")
    private WebElement displayNameInput;

    @FindBy(xpath = "//div[text()='Number of seconds']")
    private WebElement numberOfSecondsLabel;

    @FindBy(name = "quiet_period")
    private WebElement numberOfSecondsInput;

    @FindBy(xpath = "//button[@data-section-id = 'triggers']")
    private WebElement triggersSectionButton;

    @FindBy(xpath = "//label[contains(text(), 'Build periodically')]")
    private WebElement buildPeriodicallyLabel;

    @FindBy(xpath = "//textarea[@name = '_.spec']")
    private WebElement scheduleTextarea;

    @FindBy(xpath = "//div[contains(text(), 'Would last have run at') and contains(text(), 'would next run at')]")
    private WebElement textAreaValidationMessage;

    @FindBy(xpath = "//div[contains(text(), 'Schedule')]/following-sibling::div" + "//div[@class = 'error']")
    private WebElement textErrorMessage;

    public PipelineConfigurationPage(WebDriver driver) {
        super(driver);
    }

    @Override
    protected void waitUntilPageLoad() {
        getWait5().until(ExpectedConditions.visibilityOf(submitButton));
    }

    public PipelinePage clickSubmitButton() {
        submitButton.click();

        getWait5().until(ExpectedConditions.presenceOfElementLocated(By.tagName("h1")));
        return new PipelinePage(getDriver());
    }

    public PipelineConfigurationPage clickApplyButton() {
        applyButton.click();

        return this;
    }

    public PipelineConfigurationPage clickAdvancedLinkInSideMenu() {
        advancedMenuItem.click();

        return this;
    }

    public PipelineConfigurationPage scrollDownToAdvancedSection() {
        ((JavascriptExecutor) getDriver()).executeScript(
                "arguments[0].scrollIntoView(true);",advancedTitle);

        return this;
    }

    public String getAdvancedTitleText() {
        return advancedTitle.getText();
    }

    public PipelineConfigurationPage clickAdvancedButton() {
        clickAdvancedLinkInSideMenu();
        ((JavascriptExecutor) getDriver()).executeScript("arguments[0].scrollIntoView({block: 'center'});",
                getWait10().until(ExpectedConditions.visibilityOfElementLocated(By.id("footer"))));

        WebElement advancedButton = getWait10().until(ExpectedConditions.elementToBeClickable(By
                .xpath(".//div[@id='advanced']/parent::section/descendant::button[contains(text(),'Advanced')]")));
        new Actions(getDriver()).moveToElement(advancedButton).click().perform();

        getWait10().until(ExpectedConditions.visibilityOfElementLocated(By
                .xpath(".//label[text()='Quiet period']")));

        return this;
    }

    public String getQuietPeriodLabelText() {
        new Actions(getDriver()).moveToElement(quietPeriodLabel).perform();

        return quietPeriodLabel.getText();
    }

    public boolean quietPeriodCheckboxIsSelected() {
        return quietPeriodCheckbox.isSelected();
    }

    public PipelineConfigurationPage clickQuitePeriod() {
        new Actions(getDriver()).moveToElement(quietPeriodLabel).click().perform();

        getWait10().until(ExpectedConditions.visibilityOfElementLocated(By.name("quiet_period")));

        return this;
    }

    public String getDisplayNameLabelText() {
        new Actions(getDriver()).moveToElement(displayNameLabel).perform();

        return displayNameLabel.getText().split("\\n")[0];
    }

    public boolean displayNameValueIsEmpty() {
        return displayNameInput.getAttribute("value").isEmpty();
    }

    public PipelineConfigurationPage sendDisplayName(String displayName) {
        new Actions(getDriver()).moveToElement(displayNameInput).perform();
        displayNameInput.sendKeys(displayName);

        return this;
    }

    public String getNumberOfSecondsLabelText() {
        return numberOfSecondsLabel.getText();
    }

    public boolean isNumberOfSecondsInputDisplayed() {
        return numberOfSecondsInput.isDisplayed();
    }

    public List<String> getTooltipList() {
        return tooltipList
                .stream()
                .map(webElement -> webElement.getAttribute("title"))
                .toList();
    }

    public boolean isHelpElementDisplayed() {
        boolean isHelpElementDisplayed = false;

        Assert.assertNotEquals(tooltipList.size(), 0);
        for (WebElement webElement : tooltipList) {
            new Actions(getDriver()).moveToElement(webElement).click().perform();
            isHelpElementDisplayed = helpElement.isDisplayed();
        }

        return isHelpElementDisplayed;
    }

    public String getToggleCheckedLabelText() {
        getWait5().until(ExpectedConditions.visibilityOfElementLocated(By
                .id("toggle-switch-enable-disable-project")));
        return getWait5().until(ExpectedConditions.visibilityOfElementLocated(By
                        .className("jenkins-toggle-switch__label__checked-title")))
                .getText();
    }

    public String getToggleUncheckedLabelText() {
        getWait5().until(ExpectedConditions.visibilityOfElementLocated(By
                .id("toggle-switch-enable-disable-project")));
        return getWait5().until(ExpectedConditions.visibilityOfElementLocated(By
                        .className("jenkins-toggle-switch__label__unchecked-title")))
                .getText();
    }

    public PipelineConfigurationPage clickToggle() {
        getWait5().until(ExpectedConditions.visibilityOfElementLocated(By
                        .id("toggle-switch-enable-disable-project")))
                .click();

        return this;
    }

    public String getBreadcrumbItem() {
        return getWait10().until(ExpectedConditions.visibilityOfElementLocated(By
                .xpath("//span[contains(text(),'Configuration')]"))).getText();
    }

    public PipelineConfigurationPage clickTriggersSectionButton() {
        triggersSectionButton.click();

        getWait5().until(ExpectedConditions.attributeContains(
                triggersSectionButton, "class", "task-link--active"));

        return this;
    }

    public PipelineConfigurationPage selectBuildPeriodicallyCheckbox() {
        buildPeriodicallyLabel.click();

        return this;
    }

    public PipelineConfigurationPage sendScheduleText(String validTimePeriod) {
        scheduleTextarea.sendKeys(validTimePeriod);

        getDriver().findElement(By.tagName("body")).click();
        return this;
    }

    public String getNotificationSaveMessage() {
        return getWait10().until(ExpectedConditions.visibilityOfElementLocated(By
                .xpath("//span[text() = 'Saved']"))).getText();
    }

    public String getTextAreaValidationMessage() {
        return textAreaValidationMessage.getText();
    }

    public String getTextErrorMessage() {
        return textErrorMessage.getText();
    }

    public String getErrorDescriptionModalWindow() {
        WebElement errorDescriptionModalWindow = getDriver().findElement(By.cssSelector("#error-description > h2"));
        getWait5().until(ExpectedConditions.visibilityOf(errorDescriptionModalWindow));

        return errorDescriptionModalWindow.getText();
    }

    public WebElement[] selectAllTriggers() {
        JavascriptExecutor js = (JavascriptExecutor) getDriver();

       WebElement trigger1 = getWait10().until(ExpectedConditions.presenceOfElementLocated(By.xpath("//input/following-sibling::label[text()='Build after other projects are built']")));
       WebElement trigger2 = getWait10().until(ExpectedConditions.presenceOfElementLocated(By.xpath("//input/following-sibling::label[text()='Build periodically']")));
       WebElement trigger3 = getWait10().until(ExpectedConditions.presenceOfElementLocated(By.xpath("//input/following-sibling::label[text()='GitHub hook trigger for GITScm polling']")));
       WebElement trigger4 = getWait10().until(ExpectedConditions.presenceOfElementLocated(By.xpath("//input/following-sibling::label[text()='Poll SCM']")));
       WebElement trigger5 = getWait10().until(ExpectedConditions.presenceOfElementLocated(By.xpath("//input/following-sibling::label[text()='Trigger builds remotely (e.g., from scripts)']")));

       WebElement[] triggers = {trigger1, trigger2, trigger3, trigger4, trigger5};

        for (WebElement trigger : triggers) {
            js.executeScript("arguments[0].scrollIntoView({block: 'center'});", trigger);
            getWait10().until(ExpectedConditions.elementToBeClickable(trigger));
            trigger.click();
        }
        return triggers;
    }
}