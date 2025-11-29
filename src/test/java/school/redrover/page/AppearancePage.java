package school.redrover.page;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
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

    public AppearancePage clickAppearanceDropdownItem(String userThemes) {
        getWait10().until(webDriver -> ((JavascriptExecutor) webDriver)
                .executeScript("return document.readyState").equals("complete"));

        WebElement themeSelectElement = getWait10().until(ExpectedConditions.elementToBeClickable(By.xpath("//*[@name='_.theme']")));

        Actions actions = new Actions(getDriver());
        actions.moveToElement(themeSelectElement).click().perform();

        Select themeSelect = new Select(themeSelectElement);
        themeSelect.selectByValue(userThemes);

        return this;
    }
}
