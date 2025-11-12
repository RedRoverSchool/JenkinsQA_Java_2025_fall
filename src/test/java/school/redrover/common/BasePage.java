package school.redrover.common;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import school.redrover.page.HomePage;

public abstract class BasePage extends BaseModel {

    public BasePage(WebDriver driver) {
        super(driver);
    }

    public HomePage gotoHomePage() {
        getWait5().until(ExpectedConditions.elementToBeClickable(By.className("app-jenkins-logo"))).click();

        return new HomePage(getDriver());
    }
}
