package school.redrover.page;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import school.redrover.common.BasePage;
import java.time.Duration;


public class UsersPage extends BasePage {

    private static final By ACCOUNT_MENU_ITEM = By.xpath("//a[contains(., 'Account')]");

    @FindBy(xpath = "//a[@href='addUser']")
    private WebElement createUserButton;

    public UsersPage(WebDriver driver) {
        super(driver);
    }

    public UserCreatingPage clickCreateUserButton() {
        createUserButton.click();

        return new UserCreatingPage(getDriver());
    }

    public String getUserName(String userName) {

        return getDriver().findElement(By.xpath("//td[text()='%s']".formatted(userName))).getText();
    }

    public UserAccountPage clickAccountMenuItem(String userName) {

        return clickPopUpMenuItem(userName, ACCOUNT_MENU_ITEM, new UserAccountPage(getDriver()));
    }

    private <P extends BasePage> P clickPopUpMenuItem(String userName, By menuItemLocator, P returnedPage) {
        new Actions(getDriver())
                .moveToElement(getWait10().until(ExpectedConditions.elementToBeClickable(By.xpath("//a[text()='%s']".formatted(userName)))))
                .pause(Duration.ofSeconds(2))
                .perform();

        getWait10().until(ExpectedConditions.visibilityOfElementLocated(By.xpath("(//button[@class='jenkins-menu-dropdown-chevron'])[2]")))
                .sendKeys(Keys.ENTER);
        getWait10().until(ExpectedConditions.elementToBeClickable(menuItemLocator))
                .click();

        return returnedPage;
    }
}
