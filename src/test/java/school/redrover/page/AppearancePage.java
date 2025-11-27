package school.redrover.page;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import school.redrover.common.BasePage;

public class AppearancePage extends BasePage {

    public AppearancePage(WebDriver driver) {
        super(driver);
    }

    public AppearancePage clickLightTheme() {
        getDriver().findElement(By.xpath("//label[span[text() = 'Light']]")).click();

        return this;
    }

    public AppearancePage clickDarkSystemTheme() {
        getDriver().findElement(By.xpath("//label[span[text() = 'Dark (System)']]")).click();

        return this;
    }

    public AppearancePage clickDarkTheme() {
        getDriver().findElement(By.xpath("//label[span[text() = 'Dark']]")).click();

        return this;
    }

    public AppearancePage clickDoNotAllowDifferentTheme() {
        getDriver().findElement(By.xpath("//*[@id='main-panel']/form/div[1]/section[1]/div[4]/div[1]/span/label")).click();

        return this;
    }

    public AppearancePage clickApplyButton() {
        getDriver().findElement(By.cssSelector("button.jenkins-button.apply-button")).click();

        return this;
    }

    public String getPopUpApplyButtonText() {
        return getWait10().until(ExpectedConditions.visibilityOfElementLocated(By.xpath(
                "//*[@id='notification-bar']"))).getText();
    }

    public JenkinsManagementPage clickSaveButton() {
        getDriver().findElement(By.cssSelector("button.jenkins-submit-button")).click();

        return new JenkinsManagementPage(getDriver());
    }
}
