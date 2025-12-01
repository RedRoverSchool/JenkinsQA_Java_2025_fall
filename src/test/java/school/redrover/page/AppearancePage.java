package school.redrover.page;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import school.redrover.common.BasePage;

public class AppearancePage extends BasePage {

    @FindBy(xpath = "//label[span[text() = 'Light']]")
    private WebElement lightTheme;

    @FindBy(xpath = "//label[span[text() = 'Dark (System)']]")
    private WebElement darkSystemTheme;

    @FindBy(xpath = "//label[span[text() = 'Dark']]")
    private WebElement darkTheme;

    @FindBy(xpath = "//label[span[text() = 'Dark']]")
    private WebElement checkBoxAllowDifferentTheme;

    @FindBy(css = "button.jenkins-button.apply-button")
    private WebElement applyButton;

    @FindBy(css = "button.jenkins-submit-button")
    private WebElement saveButton;

    public AppearancePage(WebDriver driver) {
        super(driver);
    }

    public AppearancePage clickLightTheme() {
        lightTheme.click();

        return this;
    }

    public AppearancePage clickDarkSystemTheme() {
        darkSystemTheme.click();

        return this;
    }

    public AppearancePage clickDarkTheme() {
        darkTheme.click();

        return this;
    }

    public AppearancePage clickDoNotAllowDifferentTheme() {
        checkBoxAllowDifferentTheme.click();

        return this;
    }

    public AppearancePage clickApplyButton() {
        applyButton.click();

        return this;
    }

    public String getPopUpApplyButtonText() {
        return getWait10().until(ExpectedConditions.visibilityOfElementLocated(By.xpath(
                "//*[@id='notification-bar']"))).getText();
    }

    public JenkinsManagementPage clickSaveButton() {
        saveButton.click();

        return new JenkinsManagementPage(getDriver());
    }

    public AppearancePage changeTheme(String theme) {
        getDriver().findElement(By.cssSelector("label:has(> div[data-theme='%s'])".formatted(theme))).click();

        return this;
    }

    public String getHTMLAttributeThemeText() {
        try {
            getWait10().until(driver -> {
                Object value = ((JavascriptExecutor) driver)
                        .executeScript("return document.documentElement.getAttribute('data-theme');");
                return value != null && !value.toString().isBlank();
            });

            Object result = ((JavascriptExecutor) getDriver())
                    .executeScript("return document.documentElement.getAttribute('data-theme');");
            return (result != null && !result.toString().isBlank()) ? result.toString() : "unknown";
        } catch (Exception e) {
            return "unknown";
        }
    }
}
