package school.redrover.common;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import school.redrover.page.*;

public abstract class BasePage extends BaseModel {

    public BasePage(WebDriver driver) {
        super(driver);
    }

    public HomePage gotoHomePage() {
        getWait5().until(ExpectedConditions.elementToBeClickable(By.className("app-jenkins-logo"))).click();

        return new HomePage(getDriver());
    }

    public ManageJenkinsPage clickGearManageJenkinsButton() {
        getDriver().findElement(By.id("root-action-ManageJenkinsAction")).click();

        return new ManageJenkinsPage(getDriver());
    }

    public SearchModalPage clickSearchButton() {
        getWait5().until(ExpectedConditions.elementToBeClickable(getDriver().findElement(By.id("root-action-SearchAction")))).click();
        return new SearchModalPage(getDriver());
    }

    public LoginPage clickSignOut() {
        Actions actions = new Actions(getDriver());

        actions.moveToElement(getDriver().findElement(By.id("root-action-UserAction"))).perform();
        getDriver().findElement(By.cssSelector(".jenkins-dropdown__item:last-child")).click();

        return new LoginPage(getDriver());
    }

    public FooterDropdownPage clickJenkinsVersion() {
        getDriver().findElement(By.cssSelector(".page-footer__links > button")).click();
        getWait2().until(ExpectedConditions.visibilityOfElementLocated(By.className("jenkins-dropdown")));

        return new FooterDropdownPage(getDriver());
    }

    public UserAccountPage clickUserAccountButton() {
//        getWait5().until(ExpectedConditions.elementToBeClickable(By.id("root-action-UserAction"))).click();

        WebElement accountButton = getWait5().until(ExpectedConditions.elementToBeClickable(By.id("root-action-UserAction")));
        new Actions(getDriver()).moveToElement(accountButton, 2, 0).click().perform();

//        new Actions(getDriver())
//                .moveToElement(getWait5().until(ExpectedConditions.elementToBeClickable(By.id("root-action-UserAction"))))
//                .click()
//                .perform();

//        TestUtils.clickJS(getDriver(), By.id("root-action-UserAction"));

        getWait10().until(ExpectedConditions.urlContains("/user/")); //for debug

        return new UserAccountPage(getDriver());
    }
}
