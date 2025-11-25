package school.redrover.page;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import school.redrover.common.BasePage;

import java.util.List;


public class ManageJenkinsPage extends BasePage {

    public ManageJenkinsPage(WebDriver driver) {
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

    public ManageJenkinsPage sendTitle(String settingTitle){
        getWait5().until(ExpectedConditions.visibilityOfElementLocated(By.id("settings-search-bar"))).sendKeys(settingTitle);

        return this;
    }

    public List<String> getSearchResults(){
        List<WebElement> searchResultElements = getWait5().until(ExpectedConditions.visibilityOfAllElementsLocatedBy(
                By.className("jenkins-dropdown__item")));

        return searchResultElements
                .stream()
                .map(WebElement::getText)
                .toList();
    }

    public ManageJenkinsPage clickAppearance() {
        getDriver().findElement(By.cssSelector("a[href='appearance']")).click();
        return this;
    }

    public String changeTheme(String theme) {
        getDriver().findElement(By.cssSelector("label:has(> div[data-theme='%s'])".formatted(theme))).click();
        if (!getDriver().findElement(By.cssSelector("input[name='_.disableUserThemes']")).isSelected()) {
            getDriver().findElement(
                    By.xpath("//label[contains(., 'Do not allow users to select a different theme')]")
            ).click();
        }
        getDriver().findElement(By.cssSelector("button.jenkins-submit-button")).click();
        return getDriver().findElement(By.cssSelector("html")).getAttribute("data-theme");
    }

    public AppearancePage clickAppearanceLink() {
        getDriver().findElement(By.xpath("//a[@href = 'appearance']")).click();
        return this;
    }
}
