package school.redrover.page;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import school.redrover.common.BasePage;


public class UserAccountPage extends BasePage {

    private static final By FULL_NAME_FIELD = By.name("_.fullName");

    public UserAccountPage(WebDriver driver) {
        super(driver);
    }

    public String getUserName() {
        return getWait10()
                .until(ExpectedConditions.visibilityOfElementLocated(By
                        .xpath("//div[@id='main-panel']/descendant::h1")))
                .getText();
    }

    public String getUserID() {
        return getDriver().findElement(By
                        .xpath("//div[@id='main-panel']/descendant::div[contains(text(),'User ID:')]"))
                .getText().substring(17);
    }

    public String getUserNameInBreadcrumbBar(String userName) {
        return getWait10().until(ExpectedConditions.visibilityOfElementLocated(By
                .xpath(".//a[@href=contains(text(),'%s')]".formatted(userName))))
                .getText();
    }

    public UserAccountPage sendFullName(String fullName) {
        WebElement fullNameField = getWait5().until(ExpectedConditions.visibilityOfElementLocated(FULL_NAME_FIELD));
        fullNameField.clear();
        fullNameField.sendKeys(fullName);

        return this;
    }

    public <P extends BasePage> P clickSave(P returnedPage) {
        getWait5().until(ExpectedConditions.visibilityOfElementLocated(By.name("Submit"))).click();

        return returnedPage;
    }
}
