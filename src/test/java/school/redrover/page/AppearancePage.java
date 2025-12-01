package school.redrover.page;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
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
        getDriver().findElement(By.xpath("//label[contains(., 'Do not allow users to select a different theme')]")).click();

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


    public AppearancePage changeTheme(String theme) {
        getDriver().findElement(By.cssSelector("label:has(> div[data-theme='%s'])".formatted(theme))).click();

        return this;
    }

    public AppearancePage clickAllowDifferentTheme() {
        if (!getDriver().findElement(By.cssSelector("input[name='_.disableUserThemes']")).isSelected()) {
            getDriver().findElement(
                    By.xpath("//label[contains(., 'Do not allow users to select a different theme')]")
            ).click();
        }
        return this;
    }

    public String getHTMLAttributeThemeText() {
        try {
            return (String) ((JavascriptExecutor) getDriver())
                    .executeScript("return document.documentElement.getAttribute('data-theme') || 'unknown'");
        } catch (Exception e) {
            return "unknown";
        }
    }
}
