package school.redrover.page;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import school.redrover.common.BasePage;
import java.util.List;

public class FolderConfigurationPage extends BasePage {

    public FolderConfigurationPage(WebDriver driver) {
        super(driver);
    }

    public FolderConfigurationPage setDisplayName(String name) {
        getDriver().findElement(By.name("_.displayNameOrNull")).sendKeys(name);

        return this;
    }

    public FolderConfigurationPage setDescription(String text) {
        getDriver().findElement(By.name("_.description")).sendKeys(text);

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
        String linkText = getDriver().findElement(By.cssSelector("button[data-section-id='health-metrics']"))
                .getText()
                .trim();

        return linkText;
    }

    public String getHealthMetricsButton() {
        String buttonText = getDriver().findElement(By.cssSelector("button.jenkins-button.advanced-button")).getText().trim();

        return buttonText;
    }

    public FolderConfigurationPage clickHealthMetricsSidebarLink() {
        getDriver().findElement(By.cssSelector("button[data-section-id='health-metrics']")).click();

        return this;
    }

    public String getSectionName() {
        String sectionName = getDriver().findElement(By.id("health-metrics")).getText().trim();

        return sectionName;
    }

    public FolderConfigurationPage clickHealthMetricsButton() {
        getDriver().findElement(By.cssSelector("button.jenkins-button.advanced-button")).click();

        return this;
    }

    public FolderConfigurationPage clickAddMetricButton() {
        getDriver().findElement(By.cssSelector("button.jenkins-button.hetero-list-add")).click();
        getWait2().until(ExpectedConditions
                .visibilityOfElementLocated(By.xpath("//input[@class='jenkins-dropdown__filter-input']")));

        return this;
    }

    public List<String> getAllMetricTypeNames() {
        By metricTypesList = By.xpath("//div[@class='jenkins-dropdown jenkins-dropdown--compact']//button");

        clickAddMetricButton();
        getWait2().until(ExpectedConditions.presenceOfAllElementsLocatedBy(metricTypesList));

        List<String> metricTypeNames = getDriver().findElements(metricTypesList)
                .stream()
                .map(WebElement::getText)
                .toList();

        return metricTypeNames;
    }

    public FolderConfigurationPage clickWorstHealthButton() {
        By worstHealthMetricRow = By.xpath("//div[@descriptorid='com.cloudbees.hudson.plugins.folder.health.WorstChildHealthMetric']");

        getDriver().findElement(By.xpath("//button[normalize-space(text())='Child item with worst health']")).click();
        getWait2().until(ExpectedConditions.visibilityOfElementLocated(worstHealthMetricRow));

        return this;
    }

    public String getMetricRowName() {
        String headerText = getDriver().findElement(By.xpath("//div[@class='repeated-chunk__header'][1]")).getText().trim();

        return headerText;
    }

    public FolderConfigurationPage clickGivenNameButton() {
        By givenNameMetricRow = By.xpath("//div[@descriptorid='com.cloudbees.hudson.plugins.folder.health.NamedChildHealthMetric']");

        getDriver().findElement(By.xpath("//button[@class='jenkins-dropdown__item '][1]")).click();
        getWait2().until(ExpectedConditions.visibilityOfElementLocated(givenNameMetricRow));

        return this;
    }

    public FolderConfigurationPage dragWorstHealthRowToTop() {
        WebElement worstHealthRow = getDriver()
                .findElement(By.xpath("//div[@descriptorid='com.cloudbees.hudson.plugins.folder.health.WorstChildHealthMetric']"));
        WebElement dragHandle = worstHealthRow
                .findElement(By.xpath(".//div[@class='dd-handle']"));
        WebElement givenNameRow = getDriver()
                .findElement(By.xpath("//div[@descriptorid='com.cloudbees.hudson.plugins.folder.health.NamedChildHealthMetric']"));

        new Actions(getDriver())
                .clickAndHold(dragHandle)
                .moveToElement(givenNameRow)
                .moveByOffset(0, -10)
                .release()
                .perform();

        return this;
    }

    public FolderConfigurationPage deleteMetric() {
        getDriver().findElement(By.xpath(".//button[@title='Remove' and contains(@class, 'repeatable-delete')]")).click();

        return this;
    }

    public List<String> getMetricList() {
        return getDriver().findElements(By.xpath(".//div[@class='tbody dropdownList-container']"))
                .stream()
                .map(WebElement::getText)
                .toList();
    }

    public FolderConfigurationPage hoverChildNameTooltip() {
        WebElement elementToHover = getDriver().findElement(By.xpath("//a[@tooltip='Help for feature: Child Name']"));

        new Actions(getDriver())
                .moveToElement(elementToHover)
                .perform();

        return this;
    }

    public String getChildNameHelpText() {
        return getDriver().findElement(By
                .cssSelector("a[helpurl='/descriptor/com.cloudbees.hudson.plugins.folder.health.NamedChildHealthMetric/help/childName']"))
                .getAttribute("tooltip");
    }

    public FolderConfigurationPage clickChildNameTooltip() {
        getDriver().findElement(By.xpath("//a[@tooltip='Help for feature: Child Name']")).click();

        return this;
    }

    public String getChildNameTooltipText() {
       return getDriver().findElement(By.xpath(".//div[contains(text(), 'Controls the child')]"))
                .getText()
                .trim();
    }

    public FolderConfigurationPage hoverRecursiveTooltip() {
        WebElement elementToHover = getDriver().findElement(By.xpath("//a[@tooltip='Help']"));

        new Actions(getDriver())
                .moveToElement(elementToHover)
                .perform();

        return this;
    }

    public String getTooltipTitle() {
        return getDriver().findElement(By
                .cssSelector("a[helpurl='/descriptor/com.cloudbees.hudson.plugins.folder.health.WorstChildHealthMetric/help/recursive']"))
                .getAttribute("tooltip");
    }
}
