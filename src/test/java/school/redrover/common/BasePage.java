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

    public UserStatusPage clickUserPicButton() {
        WebElement userPicButton = getWait5().until(ExpectedConditions.elementToBeClickable(By.id("root-action-UserAction")));

        TestUtils.mouseEnterJS(getDriver(), userPicButton);
        TestUtils.clickJS(getDriver(), userPicButton);

        return new UserStatusPage(getDriver());
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
        getWait5().until(ExpectedConditions.visibilityOfElementLocated(By.className("jenkins-dropdown")));

        return new FooterDropdownPage(getDriver());
    }

    public String getRestApiLinkText() {

        WebElement footer = getDriver().findElement(By.tagName("footer"));
        return footer.findElement(By.linkText("REST API")).getText();
    }

    public RestApiPage clickRestApiLink() {
        getDriver().findElement(By.xpath("//a[@href='api/']")).click();

        return new RestApiPage(getDriver());
    }

    public String getCurrentUrl() {
        return getDriver().getCurrentUrl();
    }
}
