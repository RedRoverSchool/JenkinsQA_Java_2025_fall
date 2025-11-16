package school.redrover.page;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import school.redrover.common.BasePage;
import java.time.Duration;

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
        return getDriver()
                .findElement(By.cssSelector("button[data-section-id='health-metrics']"))
                .getText();
    }

    public String getHealthMetricsButton() {
        return getDriver()
                .findElement(By.cssSelector("button.jenkins-button.advanced-button"))
                .getText();
    }

    public ConfigurationFolderPage clickHealthMetricsSidebarLink() {
        getDriver().findElement(By.cssSelector("button[data-section-id='health-metrics']")).click();

        return this;
    }

    public String getSectionName() {
        return getDriver().findElement(By.id("health-metrics")).getText();
    }

    public ConfigurationFolderPage clickHealthMetricsButton() {
        getDriver().findElement(By.cssSelector("button.jenkins-button.advanced-button")).click();

        return this;
    }

    public ConfigurationFolderPage clickAddMetricButton() {
        getDriver().findElement(By.cssSelector("button.jenkins-button.hetero-list-add")).click();
        WebDriverWait wait = new WebDriverWait(getDriver(), Duration.ofSeconds(2));
        wait.until(ExpectedConditions
                .visibilityOfElementLocated(By.xpath("//input[@class='jenkins-dropdown__filter-input']")));

        return this;
    }

    public String getMetricType1() {
        return getDriver()
                .findElement(By.xpath("//button[normalize-space(text())='Child item with the given name']"))
                .getText();
    }

    public String getMetricType2() {
        return getDriver()
                .findElement(By.xpath("//button[normalize-space(text())='Child item with worst health']"))
                .getText();
    }
}
