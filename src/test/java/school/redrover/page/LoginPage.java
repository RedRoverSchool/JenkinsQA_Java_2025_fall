package school.redrover.page;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import school.redrover.common.BasePage;

public class LoginPage extends BasePage {

    public LoginPage(WebDriver driver) {
        super(driver);
    }

    public String getTitle() {
        return getDriver().findElement(By.tagName("h1")).getText();
    }

    public String getUrlProfile() {
        getDriver().get(getDriver().getCurrentUrl() + "user/admin/");
        return getTitle();
    }

    public HomePage signIn(String userName, String userPassword) {
        getDriver().findElement(By.cssSelector("#j_username")).sendKeys(userName);
        getDriver().findElement(By.cssSelector("#j_password")).sendKeys(userPassword);
        getDriver().findElement(By.xpath("//button")).click();
        getWait2().until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//h1[.='Welcome to Jenkins!']")));

        return new HomePage(getDriver());
    }

}
