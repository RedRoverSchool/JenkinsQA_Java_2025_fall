package school.redrover.common;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import school.redrover.page.HomePage;

public abstract class BasePage extends BaseModel {

    public BasePage(WebDriver driver) {
        super(driver);
    }

    public HomePage gotoHomePage() {
        getDriver().findElement(By.cssSelector("span.jenkins-mobile-hide")).click();

        return new HomePage(getDriver());
    }
}
