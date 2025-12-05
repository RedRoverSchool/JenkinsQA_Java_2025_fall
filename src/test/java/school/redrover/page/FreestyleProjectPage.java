package school.redrover.page;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class FreestyleProjectPage extends BaseProjectPage {

    @FindBy(xpath = "//a[contains(@href, '/configure')]")
    private WebElement configureMenuItem;

    @FindBy(xpath = "//span[text()='Delete Project']/ancestor::a")
    private WebElement deleteMenuItem;

    @FindBy(id = "description-content")
    private WebElement descriptionText;

    @FindBy(name = "Submit")
    private WebElement submitButton;

    public FreestyleProjectPage(WebDriver driver) {
        super(driver);
    }

    @Override
    protected void waitUntilPageLoad() {
        getWait10().until(ExpectedConditions.visibilityOf(deleteMenuItem));
    }

    public FreestyleProjectConfigurationPage clickConfigureLinkInSideMenu() {
        configureMenuItem.click();

        getWait10().until(ExpectedConditions.visibilityOf(submitButton));
        return new FreestyleProjectConfigurationPage(getDriver());
    }

    public FreestyleProjectPage clickBuildNow() {
        getDriver().findElement(By.xpath("//div[@id='tasks']/div[4]/span/a")).click();

        return this;
    }

    public String getDescription() {
        return descriptionText.getText();
    }

    public String getNotificationBuildScheduled() {
        return getWait10().until(ExpectedConditions.visibilityOfElementLocated(
                By.xpath("//div[@id='notification-bar']"))).getText();
    }

    public String getDisableProjectMessage() {
        return getWait5().until(ExpectedConditions
                .visibilityOfElementLocated(By.cssSelector(".warning"))).getText().split("\\R")[0];
    }

}