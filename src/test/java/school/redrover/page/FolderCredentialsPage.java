package school.redrover.page;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class FolderCredentialsPage extends BaseSideMenuItemPage {

    @FindBy(xpath = "//h1[contains(text(), 'Credentials')]")
    private WebElement credentialsTitle;

    public FolderCredentialsPage(WebDriver driver) {
        super(driver);
    }

    @Override
    protected void waitUntilPageLoad() {
        getWait10().until(ExpectedConditions.visibilityOf(credentialsTitle));
    }
}
