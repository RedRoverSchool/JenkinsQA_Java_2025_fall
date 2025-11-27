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

    public MultiConfigurationProjectPage clickConfigureLinkInSideMenu() {
        getWait5().until(ExpectedConditions.visibilityOfElementLocated(By
                        .xpath("//a[contains(@href, '/configure')]"))).click();

        return new MultiConfigurationProjectPage(getDriver());
    }

    public MultiConfigurationProjectPage clickSubmit() {
        getDriver().findElement(By.name("Submit")).click();
        getWait5().until(ExpectedConditions.visibilityOfAllElementsLocatedBy(
                By.className("permalinks-header")));

        return this;
    }

    public MultiConfigurationProjectPage clickRenameViaDashboardDropDownMenu() {
        Actions actions = new Actions(getDriver());

        WebElement hoverElement = getWait10().until(
                ExpectedConditions.presenceOfElementLocated(
                        By.xpath("//*[contains(@class, 'hoverable-children-model-link')]")));
        actions.moveToElement(hoverElement, 10, 10).perform();

        getWait10().until(ExpectedConditions.elementToBeClickable(By.cssSelector("[href$='confirm-rename']"))).click();

        return this;
    }

    public MultiConfigurationProjectPage clearNameField() {
        getDriver().findElement(By.cssSelector("[href$='confirm-rename']")).click();

        WebElement renameField = getDriver().findElement(By.name("newName"));
        renameField.clear();
        return this;
    }

    public MultiConfigurationProjectPage setNewProjectName(String jobName) {
        getDriver().findElement(By.name("newName")).sendKeys(jobName + Keys.ENTER);

        getWait5().until(ExpectedConditions.not(ExpectedConditions.urlContains("confirm-rename")));
        return this;
    }

    public String getHeading() {
        return getDriver().findElement(By.tagName("h1")).getText();
    }

    public String getBreadcrumbItem() {
        return getWait10().until(ExpectedConditions.visibilityOfElementLocated(By
                .xpath("//span[contains(text(),'Configuration')]"))).getText();
    }
}
