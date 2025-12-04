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

    @FindBy(xpath = "//a[contains(., 'Account')]")
    private WebElement accountMenuItem;

    @FindBy(xpath = "//a[@href='addUser']")
    private WebElement createUserButton;

    @FindBy(xpath = "(//button[@class='jenkins-menu-dropdown-chevron'])[2]")
    private WebElement chevronButton;

    /***
     * WebElements from different Pages to wait before Page return
     */

    @FindBy(xpath = "//h1[text()='Create User']")
    private WebElement userCreatingPageHeader;

    @FindBy(xpath = "//h1[text()='Account']")
    private WebElement userAccountPageHeader;

    @FindBy(id = "description-link")
    private WebElement userStatusPageEditDescriptionButton;

    public UsersPage(WebDriver driver) {
        super(driver);
    }

    public UserCreatingPage clickCreateUserButton() {
        createUserButton.click();
        getWait5().until(ExpectedConditions.visibilityOf(userCreatingPageHeader));
        
        return new UserCreatingPage(getDriver());
    }

    public String getUserName(String userName) {

        return getDriver().findElement(By.xpath("//td[text()='%s']".formatted(userName))).getText();
    }

    public UserAccountPage clickAccountMenuItem(String userName) {
        new Actions(getDriver())
                .moveToElement(getWait10().until(ExpectedConditions.elementToBeClickable(By.xpath("//a[text()='%s']".formatted(userName)))))
                .pause(Duration.ofSeconds(1))
                .perform();

        chevronButton.sendKeys(Keys.ENTER);
        getWait5().until(ExpectedConditions.elementToBeClickable(accountMenuItem)).click();

        getWait5().until(ExpectedConditions.visibilityOf(userAccountPageHeader));

        return new UserAccountPage(getDriver());
    }

    public UserStatusPage clickUserLink(String userName) {
        getDriver().findElement(By.xpath("//a[text()='%s']".formatted(userName))).click();

        getWait5().until(ExpectedConditions.visibilityOf(userStatusPageEditDescriptionButton));

        return new UserStatusPage(getDriver());
    }
}
