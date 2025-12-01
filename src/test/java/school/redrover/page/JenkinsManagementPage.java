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

    private final By searchResults = By.cssSelector(".jenkins-dropdown__item:nth-of-type(1)");

    public JenkinsManagementPage(WebDriver driver) {
        super(driver);
    }

    public UsersPage clickUserButton() {
        getDriver().findElement(By.xpath("//a[@href='securityRealm/']")).click();

        return new UsersPage(getDriver());
    }

    public CredentialsPage clickCredentialsLink() {
        getDriver().findElement(By.xpath("//a[@href = 'credentials']")).click();

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
            return (String) ((JavascriptExecutor) getDriver())
                    .executeScript("return document.documentElement.getAttribute('data-theme') || 'unknown'");
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

    public AppearancePage clickAppearanceLink() {
        getDriver().findElement(By.xpath("//a[@href = 'appearance']")).click();
        return new AppearancePage(getDriver());
    }
}
