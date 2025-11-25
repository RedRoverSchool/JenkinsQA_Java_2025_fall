package school.redrover.page;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import school.redrover.common.BasePage;

public class MultiConfigurationProjectPage extends BasePage {

    public MultiConfigurationProjectPage(WebDriver driver) {
        super(driver);
    }

    public MultiConfigurationProjectPage clickSubmit() {
        getDriver().findElement(By.name("Submit")).click();

        return new MultiConfigurationProjectPage(getDriver());
    }

    public MultiConfigurationProjectPage clickRenameViaDashboardDropDownMenu() {
        Actions actions = new Actions(getDriver());

        WebElement hoverElement = getWait10().until(
                ExpectedConditions.presenceOfElementLocated(
                        By.xpath("//*[contains(@class, 'hoverable-children-model-link')]")));
        actions.moveToElement(hoverElement, 10, 10).perform();

        getWait10().until(ExpectedConditions.elementToBeClickable(By.cssSelector("[href$='confirm-rename']"))).click();

        return new MultiConfigurationProjectPage(getDriver());
    }

    public MultiConfigurationProjectPage renameProject(String jobName) {
        getDriver().findElement(By.cssSelector("[href$='confirm-rename']")).click();

        WebElement renameField = getDriver().findElement(By.name("newName"));
        renameField.clear();
        renameField.sendKeys(jobName + Keys.ENTER);

        getWait5().until(ExpectedConditions.not(ExpectedConditions.urlContains("confirm-rename")));

        return new MultiConfigurationProjectPage(getDriver());
    }

    public String getHeading() {
        return getDriver().findElement(By.tagName("h1")).getText();
    }
}
