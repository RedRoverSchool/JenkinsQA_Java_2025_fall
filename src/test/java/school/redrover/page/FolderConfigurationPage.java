package school.redrover.page;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import java.util.List;

public class FolderConfigurationPage extends BaseSideMenuItemPage {

    @FindBy(css = "button[data-section-id='health-metrics']")
    private WebElement healthMetricSidebarLink;

    @FindBy(css = "button.jenkins-button.advanced-button")
    private WebElement healthMetricButton;

    @FindBy(id = "health-metrics")
    private WebElement healthMetricsSection;

    @FindBy(css = "button.jenkins-button.hetero-list-add")
    private WebElement addMetricButton;

    @FindBy(xpath = "//button[normalize-space(text())='Child item with worst health']")
    private WebElement worstHealthButton;

    @FindBy(xpath = "//div[@class='repeated-chunk__header'][1]")
    private WebElement metricRowName;

    @FindBy(xpath = "//div[@descriptorid='com.cloudbees.hudson.plugins.folder.health.WorstChildHealthMetric']//div[@class='dd-handle']")
    private WebElement dragHandle;

    @FindBy(xpath = "//div[@descriptorid='com.cloudbees.hudson.plugins.folder.health.NamedChildHealthMetric']")
    private WebElement givenNameRow;

    @FindBy(xpath = ".//button[@title='Remove' and contains(@class, 'repeatable-delete')]")
    private WebElement deleteMetric;

    @FindBy(xpath = ".//div[@class='tbody dropdownList-container']")
    private List <WebElement> metricList;

    @FindBy(xpath = "//a[@tooltip='Help for feature: Child Name']")
    private WebElement childNameTooltip;

    @FindBy(css = "a[helpurl='/descriptor/com.cloudbees.hudson.plugins.folder.health.NamedChildHealthMetric/help/childName']")
    private WebElement childNameHelp;

    @FindBy(xpath = ".//div[contains(text(), 'Controls the child')]")
    private WebElement childNameTooltipContent;

    @FindBy(xpath = "//a[@tooltip='Help']")
    private WebElement recursiveTooltip;

    @FindBy(css = "a[helpurl='/descriptor/com.cloudbees.hudson.plugins.folder.health.WorstChildHealthMetric/help/recursive']")
    private WebElement recursiveHelp;

    @FindBy(xpath = ".//div[contains(text(), 'Controls whether items')]")
    private WebElement recursiveTooltipContent;

    @FindBy(name ="_.displayNameOrNull")
    private WebElement displayName;

    @FindBy(name ="_.description")
    private WebElement description;

    @FindBy(name = "Submit")
    private WebElement submitButton;

    public FolderConfigurationPage(WebDriver driver) {
        super(driver);
    }

    @Override
    protected void waitUntilPageLoad() {
        getWait10().until(ExpectedConditions.visibilityOf(submitButton));
    }

    public FolderConfigurationPage setDisplayName(String name) {
        displayName.sendKeys(name);

        return this;
    }

    public FolderConfigurationPage setDescription(String text) {
        description.sendKeys(text);

        return this;
    }

    public FolderPage clickSave() {
        WebElement submitButton = getWait5().until(
                ExpectedConditions.elementToBeClickable(By.name("Submit")));
        submitButton.click();
        getWait5().until(ExpectedConditions.not(
                ExpectedConditions.urlContains("configure")));
        return new FolderPage(getDriver());
    }

    public String getHealthMetricsSidebarLink() {
        return healthMetricSidebarLink.getText().trim();}

    public String getHealthMetricsButton() {
        return healthMetricButton.getText().trim();}

    public FolderConfigurationPage clickHealthMetricsSidebarLink() {
        healthMetricSidebarLink.click();

        return this;
    }

    public String getSectionName() {
        return healthMetricsSection.getText().trim();}

    public FolderConfigurationPage clickHealthMetricsButton() {
        healthMetricButton.click();

        return this;
    }

    public FolderConfigurationPage clickAddMetricButton() {
        ((JavascriptExecutor) getDriver()).executeScript(
                "arguments[0].scrollIntoView(true);", addMetricButton);
        addMetricButton.click();

        getWait2().until(ExpectedConditions.visibilityOfElementLocated(By
                .xpath("//input[@class='jenkins-dropdown__filter-input']")));

        return this;
    }

    public List<String> getAllMetricTypeNames() {
        By metricTypesList = By.xpath("//div[@class='jenkins-dropdown jenkins-dropdown--compact']//button");

        clickAddMetricButton();
        getWait2().until(ExpectedConditions.presenceOfAllElementsLocatedBy(metricTypesList));

        return getDriver().findElements(metricTypesList)
                .stream()
                .map(WebElement::getText)
                .toList();
    }

    public FolderConfigurationPage clickWorstHealthButton() {
       By worstHealthMetricRow = By.xpath("//div[@descriptorid='com.cloudbees.hudson.plugins.folder.health.WorstChildHealthMetric']");

        worstHealthButton.click();
        getWait2().until(ExpectedConditions.visibilityOfElementLocated(worstHealthMetricRow));

        return this;
    }

    public String getMetricRowName() {
        return metricRowName.getText().trim();
    }

    public FolderConfigurationPage clickGivenNameButton() {
        By givenNameMetricRow = By.xpath("//div[@descriptorid='com.cloudbees.hudson.plugins.folder.health.NamedChildHealthMetric']");

        getDriver().findElement(By.xpath("//button[@class='jenkins-dropdown__item '][1]")).click();
        getWait2().until(ExpectedConditions.visibilityOfElementLocated(givenNameMetricRow));

        return this;
    }

    public FolderConfigurationPage dragWorstHealthRowToTop() {
        new Actions(getDriver())
                .clickAndHold(dragHandle)
                .moveToElement(givenNameRow)
                .moveByOffset(0, -10)
                .release()
                .perform();

        return this;
    }

    public FolderConfigurationPage deleteMetric() {
        deleteMetric.click();

        return this;
    }

    public List<String> getMetricList() {
        return metricList
                .stream()
                .map(WebElement::getText)
                .toList();
    }

    public FolderConfigurationPage hoverChildNameTooltip() {
        new Actions(getDriver()).moveToElement(childNameTooltip).perform();

        return this;
    }

    public String getChildNameHelpText() {
        return childNameHelp.getAttribute("tooltip");
    }

    public FolderConfigurationPage clickChildNameTooltip() {
        childNameTooltip.click();

        return this;
    }

    public String getChildNameTooltipText() {
       return childNameTooltipContent.getText().trim();
    }

    public FolderConfigurationPage hoverRecursiveTooltip() {
        new Actions(getDriver()).moveToElement(recursiveTooltip).perform();

        return this;
    }

    public String getTooltipTitle() {
        return recursiveHelp.getAttribute("tooltip");
    }

    public FolderConfigurationPage clickRecursiveTooltip() {
        recursiveTooltip.click();

        return this;
    }

    public String getRecursiveTooltipText() {
        return recursiveTooltipContent.getText().trim();
    }

    public String getBreadcrumbItem() {
        return getWait10().until(ExpectedConditions.visibilityOfElementLocated(By
                .xpath("//span[contains(text(),'Configuration')]"))).getText();
    }
}
