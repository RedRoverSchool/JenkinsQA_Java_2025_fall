package school.redrover.page;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import school.redrover.common.BasePage;


public class FolderRenamingPage extends BasePage {

    public FolderRenamingPage(WebDriver driver) {
        super(driver);
    }

    public FolderRenamingPage sendNewName (String name) {
        getWait2().until(ExpectedConditions.visibilityOfElementLocated(By.name("newName"))).sendKeys(name);

        return this;
    }

    public FolderPage renameButtonClick () {
        getDriver().findElement(By.name("Submit")).click();

        return new FolderPage(getDriver());
    }

    public FolderRenamingPage clearName () {
        getWait2().until(ExpectedConditions.visibilityOfElementLocated(By.name("newName"))).clear();

        return this;

    }
}
