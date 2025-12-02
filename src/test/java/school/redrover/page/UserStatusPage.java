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

    public String getUserNameInBreadcrumbs(String userName) {

        return getWait10().until(ExpectedConditions.visibilityOfElementLocated(By
                .xpath("//a[@href='/user/%s/']".formatted(userName))))
                .getText();
    }

    public String getUserNameAdminInBreadcrumbs() {

        return getWait10().until(ExpectedConditions.visibilityOfElementLocated(By
                        .xpath("//a[@href='/user/admin/']")))
                .getText();
    }

    public String getUserID() {
        return getDriver().findElement(By
                        .xpath("//div[@id='main-panel']/descendant::div[contains(text(),'User ID:')]"))
                .getText().substring(17);
    }

    public UserStatusPage editDescription(String text) {
        getDriver().findElement(By.id("description-link")).click();
        getDriver().findElement(By.name("description")).sendKeys(text);
        getDriver().findElement(By.name("Submit")).click();

        return this;
    }

    public String getDescriptionText() {
        return getWait2().until(ExpectedConditions.visibilityOfElementLocated(By.id("description-content"))).getText();
    }
}
