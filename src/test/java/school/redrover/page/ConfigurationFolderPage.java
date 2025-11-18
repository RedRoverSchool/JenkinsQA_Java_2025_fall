package school.redrover.page;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import school.redrover.common.BasePage;
import java.util.List;

public class ConfigurationFolderPage extends BasePage {

    public ConfigurationFolderPage(WebDriver driver) {
        super(driver);
    }

    public ConfigurationFolderPage setDisplayName(String name) {
        getDriver().findElement(By.name("_.displayNameOrNull")).sendKeys(name);

        return this;
    }

    public ConfigurationFolderPage setDescription(String text) {
        getDriver().findElement(By.name("_.description")).sendKeys(text);

        return this;
    }

    public FolderPage clickSave() {
        WebElement button = getDriver().findElement(By.name("Submit"));
        button.click();
        getWait2().until(ExpectedConditions.invisibilityOf(button));

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

    public ConfigurationFolderPage clickHealthMetricsSidebarLink() {
        getDriver().findElement(By.cssSelector("button[data-section-id='health-metrics']")).click();

        return this;
    }

    public String getSectionName() {
        String sectionName = getDriver().findElement(By.id("health-metrics")).getText().trim();

        return sectionName;
    }

    public ConfigurationFolderPage clickHealthMetricsButton() {
        getDriver().findElement(By.cssSelector("button.jenkins-button.advanced-button")).click();

        return this;
    }

    public ConfigurationFolderPage clickAddMetricButton() {
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
}
