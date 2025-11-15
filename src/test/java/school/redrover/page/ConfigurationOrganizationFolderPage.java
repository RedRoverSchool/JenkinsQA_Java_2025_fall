package school.redrover.page;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import school.redrover.common.BasePage;

public class ConfigurationOrganizationFolderPage extends BasePage {

    public ConfigurationOrganizationFolderPage(WebDriver driver) {
        super(driver);
    }

    public FolderPage clickSave() {
        WebElement button = getDriver().findElement(By.name("Submit"));
        button.click();
        getWait2().until(ExpectedConditions.invisibilityOf(button));

        return new FolderPage(getDriver());
    }
}
