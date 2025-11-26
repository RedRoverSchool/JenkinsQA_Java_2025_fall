package school.redrover.page;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
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

    public AppearancePage clickSaveButton() {
        getDriver().findElement(By.cssSelector("button.jenkins-button.apply-button")).click();

        return this;
    }

    public String getPopUpSaveButtonText() {
        return getWait10().until(ExpectedConditions.visibilityOfElementLocated(By.xpath(
                "//*[@id='notification-bar']"))).getText();
    }

    public AppearancePage disableUserThemes() {
        getDriver().findElement(By.xpath("//*[@id=\"main-panel\"]/form/div[1]/section[1]/div[4]/div[1]/span/label")).click();
        return this;
    }

    public AppearancePage pipelineStagesandGraph() {
        getDriver().findElement(By.xpath("//*[@id=\"main-panel\"]/form/div[1]/section[4]/div[2]/div[1]/span/label")).click();
        return this;
    }

    public AppearancePage changePrismSyntaxHighlightingThemeDefault() {
        Select ch = new Select(getWait5().until(ExpectedConditions.elementToBeClickable(By.name("_.theme"))));
        ch.selectByValue("PRISM");
        return this;
    }

    public AppearancePage changePrismSyntaxHighlightingThemeCoy() {
        Select ch = new Select(getWait5().until(ExpectedConditions.elementToBeClickable(By.name("_.theme"))));
        ch.selectByValue("COY");
        return this;
    }

    public AppearancePage changePrismSyntaxHighlightingDark() {
        Select ch = new Select(getWait5().until(ExpectedConditions.elementToBeClickable(By.name("_.theme"))));
        ch.selectByValue("DARK");
        return this;
    }
}
