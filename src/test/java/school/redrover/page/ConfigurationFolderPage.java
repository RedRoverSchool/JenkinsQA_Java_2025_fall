package school.redrover.page;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import school.redrover.FolderSettingsTest;
import school.redrover.common.BasePage;

public class ConfigurationFolderPage extends BasePage {

    public ConfigurationFolderPage(WebDriver driver) {
        super(driver);
    }

    public ConfigurationFolderPage setDisplayName(String name) {
        getDriver().findElement(By.name("_.displayNameOrNull")).sendKeys(name);

        return this;
    }

    public FolderPage clickSave() {
        getDriver().findElement(By.name("Submit")).click();

        return new FolderPage(getDriver());
    }
}
