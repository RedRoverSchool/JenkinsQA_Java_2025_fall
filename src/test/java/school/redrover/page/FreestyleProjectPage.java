package school.redrover.page;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import school.redrover.common.BasePage;

public class FreestyleProjectPage extends BasePage {

    public FreestyleProjectPage(WebDriver driver) {
        super(driver);
    }

    public FreestyleProjectConfigurationPage clickConfigure(String projectName) {
        getDriver().findElement(By.xpath("//a[@href='/job/%s/configure']".formatted(projectName))).click();

        return new FreestyleProjectConfigurationPage(getDriver());
    }

    public FreestyleProjectPage clickBuildNow() {
        getDriver().findElement(By.xpath("//div[@id='tasks']/div[4]/span/a")).click();

        return this;
    }


    public String getHeadingText() {
        return getWait5().until(ExpectedConditions.presenceOfElementLocated(By.
                tagName("h1"))).getText();
    }

    public String getDescription() {
        return getWait2().until(ExpectedConditions.visibilityOfElementLocated(
                By.id("description-content"))).getText();
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