package school.redrover.common;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import school.redrover.page.HomePage;
import school.redrover.page.ManageJenkinsPage;
import school.redrover.page.SearchModalPage;

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
        getWait10().until(ExpectedConditions.elementToBeClickable(getDriver().findElement(By.id("root-action-SearchAction")))).click();
        return new SearchModalPage(getDriver());
    }
}
