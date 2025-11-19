package school.redrover.page;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import school.redrover.common.BasePage;


public class ManageUsersPage extends BasePage {

    private static final By ACCOUNT_MENU_ITEM = By.xpath("//a[contains(., 'Account')]");

    public ManageUsersPage(WebDriver driver) {
        super(driver);
    }

    public CreateUserPage clickCreateUserButton() {
        getDriver().findElement(By.xpath("//a[@href='addUser']")).click();

        return new CreateUserPage(getDriver());
    }

    public String getUserName(String userName) {

        return getDriver().findElement(By.xpath("//td[text()='%s']".formatted(userName))).getText();
    }

    public String checkCreatedUser() {
        return getDriver().findElement(By.cssSelector("div.error.jenkins-\\!-margin-bottom-2")).getText();
    }

    public UserAccountPage clickAccountMenuItem(String userName) {

        return clickPopUpMenuItem(userName, ACCOUNT_MENU_ITEM, new UserAccountPage(getDriver()));
    }

    private <P extends BasePage> P clickPopUpMenuItem(String userName, By menuItemLocator, P returnedPage) {
        new Actions(getDriver())
                .moveToElement(getWait10().until(ExpectedConditions.elementToBeClickable(By.xpath("//a[text()='%s']".formatted(userName)))))
                .pause(5000)
                .perform();

        getWait10().until(ExpectedConditions.elementToBeClickable(By.xpath("(//button[@class='jenkins-menu-dropdown-chevron'])[2]")))
                .click();
        getWait10().until(ExpectedConditions.elementToBeClickable(menuItemLocator))
                .click();

        return returnedPage;
    }
}
