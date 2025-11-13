package school.redrover.page;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import school.redrover.common.BasePage;


public class FolderPage extends BasePage {

    public FolderPage(WebDriver driver) {
        super(driver);
    }

    public ConfigurationFolderPage clickConfigure() {
        getDriver().findElement(By.xpath("//span[text()='Configure']/..")).click();

        return new ConfigurationFolderPage(getDriver());
    }

    public FolderInfo getInfo() {

        String displayName = getWait5().until(ExpectedConditions.visibilityOfElementLocated(By.tagName("h1"))).getText();
        String description = getDriver().findElement(By.id("view-message")).getText();

        return new FolderInfo(displayName, description);
    }

    public static class FolderInfo {
        private final String displayName;
        private final String description;

        public FolderInfo(String displayName, String description) {
            this.displayName = displayName;
            this.description = description;
        }

        public String getDisplayName() {
            return displayName;
        }

        public String getDescription() {
            return description;
        }
    }
}
