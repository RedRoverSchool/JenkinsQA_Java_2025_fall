package school.redrover.page;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import school.redrover.common.BasePage;

public class ConfigurationPipelinePage extends BasePage {

    public ConfigurationPipelinePage(WebDriver driver) {
        super(driver);
    }

    public ConfigurationPipelinePage clickAdvancedLinkInSideMenu() {
        getWait5().until(ExpectedConditions.visibilityOfElementLocated(By
                        .xpath(".//button[@data-section-id='advanced']")))
                .click();

        return this;
    }

    public ConfigurationPipelinePage scrollDownToAdvancedSection() {
        ((JavascriptExecutor) getDriver()).executeScript(
                "arguments[0].scrollIntoView(true);",
                getDriver().findElement(By.id("advanced")));

        return this;
    }

    public String getAdvancedTitleText() {
        return getWait5()
                .until(ExpectedConditions.visibilityOfElementLocated(By.id("advanced")))
                .getText();
    }

    public ConfigurationPipelinePage clickAdvancedButton() {
        clickAdvancedLinkInSideMenu();
        ((JavascriptExecutor) getDriver()).executeScript("arguments[0].scrollIntoView({block: 'center'});",
                getWait10().until(ExpectedConditions.visibilityOfElementLocated(By.id("footer"))));

        WebElement advancedButton = getWait10().until(ExpectedConditions.elementToBeClickable(By
                .xpath(".//div[@id='advanced']/parent::section/descendant::button[contains(text(),'Advanced')]")));
        new Actions(getDriver()).moveToElement(advancedButton).click().perform();

        return this;
    }

    public String getQuietPeriodLabelText() {
        WebElement actualQuietPeriodLabel = getWait10().until(ExpectedConditions.visibilityOfElementLocated(By
                .xpath(".//label[text()='Quiet period']")));
        new Actions(getDriver()).moveToElement(actualQuietPeriodLabel).perform();

        return actualQuietPeriodLabel.getText();
    }

    public Boolean quietPeriodCheckboxIsSelected() {
        return getDriver().findElement(By.name("hasCustomQuietPeriod")).isSelected();
    }

    public String getDisplayNameLabelText() {
        WebElement displayNameLabel = getWait10().until(ExpectedConditions.visibilityOfElementLocated(By.
                xpath(".//div[text()='Display Name']")));
        new Actions(getDriver()).moveToElement(displayNameLabel).perform();

        return displayNameLabel.getText().split("\\n")[0];
    }

    public Boolean displayNameInputIsEmpty() {
        return getDriver().findElement(By.name("_.displayNameOrNull"))
                .getAttribute("value").isEmpty();
    }
}
