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
        return getWait5()
                .until(ExpectedConditions.visibilityOfElementLocated(By
                        .xpath("//div[@id='main-panel']/descendant::h1")))
                .getText();
    }

    public String getUserID() {
        return getDriver().findElement(By
                        .xpath("//div[@id='main-panel']/descendant::div[contains(text(),'User ID:')]"))
                .getText().substring(17);
    }
}
