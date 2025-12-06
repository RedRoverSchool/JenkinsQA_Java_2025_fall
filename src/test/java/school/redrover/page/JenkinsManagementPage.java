package school.redrover.page;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import school.redrover.common.BasePage;

import java.util.List;


public class JenkinsManagementPage extends BasePage {

    @FindBy(id = "settings-search-bar")
    private WebElement sendTitle;

    @FindBy(xpath = "//a[@href = 'appearance']")
    private WebElement appearanceLink;

    @FindBy(xpath = "//a[@href='securityRealm/']")
    private WebElement usersLink;

    @FindBy(xpath = "//a[@href = 'credentials']")
    private WebElement credentialsLink;


    private final By searchResults = By.cssSelector(".jenkins-dropdown__item:nth-of-type(1)");

    public JenkinsManagementPage(WebDriver driver) {
        super(driver);
    }

    public UsersPage clickUserButton() {
        usersLink.click();
        getWait5().until(ExpectedConditions.elementToBeClickable(By.xpath("//a[@href='addUser']")));

        return new UsersPage(getDriver());
    }

    public CredentialsPage clickCredentialsLink() {
        credentialsLink.click();

        return new CredentialsPage(getDriver());
    }

    public SystemConfigurationPage clickConfigurationSystem() {
        getDriver().findElement(By.xpath("//a[@href='configure']")).click();

        return new SystemConfigurationPage(getDriver());
    }

    public String getHeadingText() {
        return getWait5().until(ExpectedConditions.presenceOfElementLocated(By.
                tagName("h1"))).getText().trim();
    }

    public String getHTMLAttributeThemeText() {
        try {
            getWait10().until(driver -> {
                Object value = ((JavascriptExecutor) driver)
                        .executeScript("return document.documentElement.getAttribute('data-theme');");
                return value != null && !value.toString().isBlank();
            });
            Object result = ((JavascriptExecutor) getDriver())
                    .executeScript("return document.documentElement.getAttribute('data-theme');");
            return (result != null && !result.toString().isBlank()) ? result.toString() : "unknown";
        } catch (Exception e) {
            return "unknown";
        }
    }

    public JenkinsManagementPage sendTitle(String settingTitle) {
        sendTitle.sendKeys(settingTitle);

        return this;
    }

    public SystemConfigurationPage clickSearchResult() {
        getWait5().until(ExpectedConditions.visibilityOfElementLocated(By.className("jenkins-dropdown__item")));

        new Actions(getDriver())
                .moveToElement(getDriver().findElement(searchResults), 0, 0)
                .click()
                .perform();

        getWait5().until(ExpectedConditions.presenceOfElementLocated(By.tagName("h1")));
        return new SystemConfigurationPage(getDriver());
    }

    public List<String> getSearchResults() {
        List<WebElement> searchResultElements = getWait5().until(ExpectedConditions.visibilityOfAllElementsLocatedBy(
                By.className("jenkins-dropdown__item")));

        return searchResultElements
                .stream()
                .map(WebElement::getText)
                .toList();
    }

    public String getSearchResultName() {
        return getWait5().until(ExpectedConditions.visibilityOfElementLocated(
                By.className("jenkins-dropdown__item"))).getText();
    }

    public AppearancePage clickAppearanceLink() {
        appearanceLink.click();
        return new AppearancePage(getDriver());
    }

    public NodesPage clickNodeConfigurationSystem() {
        getDriver().findElement(By.xpath("//a[@href='computer']")).click();

        return new NodesPage(getDriver());
    }
}
