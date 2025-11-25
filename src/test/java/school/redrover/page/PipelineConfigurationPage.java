package school.redrover.page;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import school.redrover.common.BasePage;

import java.util.List;

public class PipelineConfigurationPage extends BasePage {

    public PipelineConfigurationPage(WebDriver driver) {
        super(driver);
    }

    public PipelinePage clickSaveButton() {
        getWait10().until(ExpectedConditions.elementToBeClickable(By.name("Submit"))).click();

        return new PipelinePage(getDriver());
    }

    public PipelineConfigurationPage clickAdvancedLinkInSideMenu() {
        getWait5().until(ExpectedConditions.visibilityOfElementLocated(By
                .xpath(".//button[@data-section-id='advanced']"))).click();

        return this;
    }

    public PipelineConfigurationPage scrollDownToAdvancedSection() {
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

    public PipelineConfigurationPage clickAdvancedButton() {
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

    public PipelineConfigurationPage clickQuitePeriod() {
        new Actions(getDriver())
                .moveToElement(getWait10().until(ExpectedConditions.visibilityOfElementLocated(By
                        .xpath(".//label[text()='Quiet period']"))))
                .click()
                .perform();

        return this;
    }

    public String getDisplayNameLabelText() {
        WebElement displayNameLabel = getWait10().until(ExpectedConditions.visibilityOfElementLocated(By.
                xpath(".//div[text()='Display Name']")));
        new Actions(getDriver()).moveToElement(displayNameLabel).perform();

        return displayNameLabel.getText().split("\\n")[0];
    }

    public WebElement displayNameInput() {
        return getWait10().until(ExpectedConditions.visibilityOfElementLocated(By
                .name("_.displayNameOrNull")));
    }

    public boolean displayNameValueIsEmpty() {
        return getWait10().until(ExpectedConditions.visibilityOfElementLocated(By
                .name("_.displayNameOrNull"))).getAttribute("value").isEmpty();
    }

    public PipelineConfigurationPage setDisplayName(String displayName) {
        new Actions(getDriver()).moveToElement(displayNameInput()).perform();
        displayNameInput().sendKeys(displayName);

        return this;
    }

    public String getNumberOfSecondsLabelText() {
        return getWait10().until(ExpectedConditions.visibilityOfElementLocated(By
                .xpath(".//div[text()='Number of seconds']"))).getText();
    }

    public WebElement getNumberOfSecondsInput() {
        return getWait10().until(ExpectedConditions.visibilityOfElementLocated(By.name("quiet_period")));
    }

    public WebElement getHelpElement() {
        return getDriver().findElement(By
                .xpath(".//div[@id='advanced']/parent::section/descendant::div[@class = 'help']"));
    }

    public List<String> getTooltipList() {
        return getTooltipListWeb()
                .stream()
                .map(webElement -> webElement.getAttribute("title"))
                .toList();
    }

    public List<WebElement> getTooltipListWeb() {
        return getDriver().findElements(By
                .xpath(".//div[@id='advanced']/parent::section/descendant::a[@tooltip]"));
    }

    public String getToggleCheckedLabelText() {
        getWait5().until(ExpectedConditions.visibilityOfElementLocated(By
                .id("toggle-switch-enable-disable-project")));
        return getWait5().until(ExpectedConditions.visibilityOfElementLocated(By
                        .className("jenkins-toggle-switch__label__checked-title")))
                .getText();
    }

    public String getToggleUncheckedLabelText() {
        getWait5().until(ExpectedConditions.visibilityOfElementLocated(By
                .id("toggle-switch-enable-disable-project")));
        return getWait5().until(ExpectedConditions.visibilityOfElementLocated(By
                        .className("jenkins-toggle-switch__label__unchecked-title")))
                .getText();
    }

    public PipelineConfigurationPage clickToggle() {
        getWait5().until(ExpectedConditions.visibilityOfElementLocated(By
                        .id("toggle-switch-enable-disable-project")))
                .click();
        return this;
    }

    public WebElement getPageHeader() {
        return getDriver().findElement(By.xpath("//span[text()='Configuration']"));
    }

    public WebElement[] selectAllTriggers() {
        JavascriptExecutor js = (JavascriptExecutor) getDriver();

       final WebElement trigger1 = getWait10().until(ExpectedConditions.presenceOfElementLocated(By.xpath("//*[@id='main-panel']/form/div[1]/section[1]/section/div[4]/div[1]/div/span")));
       final WebElement trigger2 = getWait10().until(ExpectedConditions.presenceOfElementLocated(By.xpath("//*[@id='main-panel']/form/div[1]/section[1]/section/div[5]/div[1]/div/span")));
       final WebElement trigger3 = getWait10().until(ExpectedConditions.presenceOfElementLocated(By.xpath("//*[@id='main-panel']/form/div[1]/section[1]/section/div[6]/div[1]/div/span")));
       final WebElement trigger4 = getWait10().until(ExpectedConditions.presenceOfElementLocated(By.xpath("//*[@id='main-panel']/form/div[1]/section[1]/section/div[7]/div[1]/div/span")));
       final WebElement trigger5 = getWait10().until(ExpectedConditions.presenceOfElementLocated(By.xpath("//*[@id='main-panel']/form/div[1]/div[5]/div[1]/div/span")));

       final WebElement[] triggers = {trigger1, trigger2, trigger3, trigger4, trigger5};

        for (WebElement trigger : triggers) {
            js.executeScript("arguments[0].scrollIntoView({block: 'center'});", trigger);
            getWait10().until(ExpectedConditions.elementToBeClickable(trigger));
            trigger.click();

        }
        return triggers;
    }
}