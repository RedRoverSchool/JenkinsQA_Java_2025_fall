package school.redrover.page;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import school.redrover.common.BasePage;

public class FreestyleProjectPage extends BasePage {

    @FindBy(xpath = "//a[contains(@href, '/configure')]")
    private WebElement configureMenuItem;

    @FindBy(tagName = "h1")
    private WebElement headingText;

    @FindBy(id = "description-content")
    private WebElement descriptionText;

    public FreestyleProjectPage(WebDriver driver) {
        super(driver);
    }

    public FreestyleProjectConfigurationPage clickConfigureLinkInSideMenu() {
        configureMenuItem.click();

        getWait2().until(ExpectedConditions.presenceOfElementLocated(By.name("Submit")));
        return new FreestyleProjectConfigurationPage(getDriver());
    }

    public FreestyleProjectPage clickBuildNow() {
        getDriver().findElement(By.xpath("//div[@id='tasks']/div[4]/span/a")).click();

        return this;
    }

    public String getHeadingText() {
        return headingText.getText();
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