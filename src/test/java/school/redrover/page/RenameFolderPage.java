package school.redrover.page;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import school.redrover.common.BasePage;

public class RenameFolderPage extends BasePage {

    public RenameFolderPage(WebDriver driver) {
        super(driver);
    }

    public RenameFolderPage sendNewName (String name) {
        getWait2().until(ExpectedConditions.visibilityOfElementLocated(By.name("newName"))).sendKeys(name);

        return this;
    }

    public RenameFolderPage renameButtonClick () {
        getDriver().findElement(By.name("Submit")).click();

        return this;
    }

    public RenameFolderPage clearName () {
        getWait2().until(ExpectedConditions.visibilityOfElementLocated(By.name("newName"))).clear();

        return this;
    }
}
