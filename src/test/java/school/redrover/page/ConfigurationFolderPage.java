package school.redrover.page;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import school.redrover.common.BasePage;

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

    public WebElement findHealthMetricsLink() {

        return getDriver()
                .findElement(By.xpath("//button[normalize-space(text())='Health metrics']"));
    }
}
