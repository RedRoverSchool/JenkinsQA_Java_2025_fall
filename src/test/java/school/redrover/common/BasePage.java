package school.redrover.common;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import school.redrover.page.*;


public abstract class BasePage extends BaseModel {

    @FindBy(id = "root-action-ManageJenkinsAction")
    private WebElement manageJenkinsButton;

    @FindBy(id = "root-action-UserAction")
    private WebElement userAccountIcon;

    @FindBy(xpath = "//a[@href='api/']")
    private WebElement restApiLink;

    @FindBy(css = ".page-footer__links > button")
    private WebElement jenkinsVersion;

    @FindBy(css = ".jenkins-dropdown__item:last-child")
    private WebElement signOutButton;


    public BasePage(WebDriver driver) {
        super(driver);
        PageFactory.initElements(driver, this);
    }

    public HomePage gotoHomePage() {
        getWait5().until(ExpectedConditions.elementToBeClickable(By.className("app-jenkins-logo"))).click();

        return new HomePage(getDriver());
    }

    public JenkinsManagementPage clickManageJenkinsGear() {
        manageJenkinsButton.click();
        getWait5().until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//h1[contains(text(),'Manage Jenkins')]")));

        return new JenkinsManagementPage(getDriver());
    }

    public UserStatusPage clickUserAccountViaDropDownMenu(String userName) {
        WebElement userIcon = getWait10().until(ExpectedConditions.visibilityOfElementLocated(
                By.id("root-action-UserAction")));
        TestUtils.mouseEnterJS(getDriver(), userIcon);

        WebElement userInDropDown = getWait10().until(ExpectedConditions.visibilityOfElementLocated(By
                .xpath("//a[contains(@class, 'jenkins-dropdown__item') and contains(., '%s')]".formatted(userName))));
        TestUtils.clickJS(getDriver(), userInDropDown);

        return new UserStatusPage(getDriver());
    }

    public SearchModalPage clickSearchButton() {
        getWait5().until(ExpectedConditions.elementToBeClickable(getDriver().findElement(By.id("root-action-SearchAction")))).click();

        return new SearchModalPage(getDriver());
    }

    public LoginPage clickSignOut() {
        Actions actions = new Actions(getDriver());

        actions.moveToElement(userAccountIcon).perform();
        signOutButton.click();

        return new LoginPage(getDriver());
    }

    public String getJenkinsVersion() {
        return jenkinsVersion.getText();
    }

    public FooterDropdownPage clickJenkinsVersion() {
        getDriver().findElement(By.cssSelector(".page-footer__links > button")).click();
        getWait5().until(ExpectedConditions.visibilityOfElementLocated(By.className("jenkins-dropdown")));

        return new FooterDropdownPage(getDriver());
    }

    public String getRestApiLinkText() {
        WebElement footer = getDriver().findElement(By.tagName("footer"));

        return footer.findElement(By.linkText("REST API")).getText();
    }

    public RestApiPage clickRestApiLink() {
        restApiLink.click();
        getWait5().until(ExpectedConditions.presenceOfElementLocated(By.tagName("h1")));

        return new RestApiPage(getDriver());
    }

    public String getCurrentUrl() {

        return getDriver().getCurrentUrl();
    }

    public UserStatusPage clickUserAccountIcon() {
        userAccountIcon.click();
        getWait10().until(ExpectedConditions.presenceOfElementLocated(By.tagName("h1")));

        return new UserStatusPage(getDriver());
    }

    public String getUserNameFromDropDownMenu() {
        WebElement userIcon = getWait10().until(ExpectedConditions.visibilityOfElementLocated(By.id("root-action-UserAction")));
        TestUtils.mouseEnterJS(getDriver(), userIcon);

        WebElement userInDropDown = getWait10().until(ExpectedConditions.visibilityOfElementLocated(By
                .cssSelector(".jenkins-dropdown__item:first-child")));

        return TestUtils.getTextJS(getDriver(), userInDropDown);
    }

    public String getLogoText() {
        return getWait5().until(ExpectedConditions.
                visibilityOfElementLocated(By.className("app-jenkins-logo"))).getText();
    }
}
