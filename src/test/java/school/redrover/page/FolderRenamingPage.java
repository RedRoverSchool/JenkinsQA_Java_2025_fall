package school.redrover.page;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class FolderRenamingPage extends BaseSideMenuItemPage {

    @FindBy(tagName = "h1")
    private WebElement headingText;

    @FindBy(name = "newName")
    private WebElement newNameField;

    @FindBy(name = "Submit")
    private WebElement renameButton;

    public FolderRenamingPage(WebDriver driver) {
        super(driver);
    }

    @Override
    protected void waitUntilPageLoad() {
        getWait10().until(ExpectedConditions.visibilityOf(renameButton));
    }

    @Override
    public String getHeadingText() {
        return headingText.getText();
    }

    public FolderRenamingPage sendNewName (String name) {
        getWait2().until(ExpectedConditions.visibilityOf(newNameField)).sendKeys(name);

        return this;
    }

    public FolderPage renameButtonClick () {
        renameButton.click();

        return new FolderPage(getDriver());
    }

    public FolderRenamingPage clearName () {
        getWait2().until(ExpectedConditions.visibilityOf(newNameField)).clear();

        return this;
    }
}
