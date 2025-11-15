package school.redrover.page;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import school.redrover.common.BasePage;


public class UserAccountPage extends BasePage {

    public UserAccountPage(WebDriver driver) {
        super(driver);
    }

    public String getUserName() {

        return getWait2().until(ExpectedConditions.visibilityOfElementLocated(By.tagName("h1")))
                .getText();
    }
}
