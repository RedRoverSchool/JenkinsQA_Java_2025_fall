package school.redrover.page;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import school.redrover.common.BasePage;

import java.util.List;

public class ManageUsersPage extends BasePage {

    public ManageUsersPage(WebDriver driver) {
        super(driver);
    }

    public CreateUserPage clickCreateUserButton() {
        getDriver().findElement(By.xpath("//a[@href='addUser']")).click();

        return new CreateUserPage(getDriver());
    }

    public String getCreatedUserName(String userName) {

        return getDriver().findElement(By.xpath("//td[text()='%s']".formatted(userName))).getText();
    }

    public String checkCreatedUser() {
        return getDriver().findElement(By.cssSelector("div.error.jenkins-\\!-margin-bottom-2")).getText();
    }
}
