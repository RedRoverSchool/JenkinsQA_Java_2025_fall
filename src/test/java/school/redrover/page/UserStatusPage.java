package school.redrover.page;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import school.redrover.common.BasePage;


public class UserStatusPage extends BasePage {

    public UserStatusPage(WebDriver driver) {
        super(driver);
    }

    public String getUserName() {

        return getWait5().until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div//h1")))
                .getText();
    }

    public String getUserNameInBreadcrumbsBar(String userName) {

        return getWait10().until(ExpectedConditions.visibilityOfElementLocated(By
                .xpath("//a[@href='/user/%s/']".formatted(userName))))
                .getText();
    }
}
