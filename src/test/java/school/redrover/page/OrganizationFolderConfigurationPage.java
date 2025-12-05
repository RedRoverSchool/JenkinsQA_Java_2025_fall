package school.redrover.page;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.util.List;

public class OrganizationFolderConfigurationPage extends BaseSideMenuItemPage {

    @FindBy(name = "Submit")
    private WebElement submitButton;

    @FindBy(xpath = "//span[text()='Delete Organization Folder']/ancestor::a")
    private WebElement deleteMenuItem;

    public OrganizationFolderConfigurationPage(WebDriver driver) {
        super(driver);
    }

    @Override
    protected void waitUntilPageLoad() {
        getWait5().until(ExpectedConditions.visibilityOf(submitButton));
    }

    public OrganizationFolderPage clickSave() {
        submitButton.click();
        getWait10().until(ExpectedConditions.visibilityOf(deleteMenuItem));

        return new OrganizationFolderPage(getDriver());
    }

    public OrganizationFolderConfigurationPage inputDisplayName(String name) {
        getWait2().until(ExpectedConditions.visibilityOfElementLocated(By
                .name("_.displayNameOrNull"))).sendKeys(name);

        return this;
    }

    public OrganizationFolderConfigurationPage inputDescription(String name) {
        getWait2().until(ExpectedConditions.visibilityOfElementLocated(By
                .name("_.description"))).sendKeys(name);

        return this;
    }

    public OrganizationFolderConfigurationPage clickDisplayNameTooltip() {
        getWait2().until(ExpectedConditions.elementToBeClickable(By
                .cssSelector("a[tooltip='Help for feature: Display Name']"))).click();

        return this;
    }

    public String getDisplayNameTooltipLink() {
        return getWait2().until(ExpectedConditions.elementToBeClickable(By
                .linkText("Branch API Plugin"))).getAttribute("href");
    }

    public String getBreadcrumbItem() {
        return getWait10().until(ExpectedConditions.visibilityOfElementLocated(By
                .xpath("//span[contains(text(),'Configuration')]"))).getText();
    }

    public OrganizationFolderConfigurationPage filterRepositorySources(String sourceName) {
        getWait2().until(ExpectedConditions.elementToBeClickable(By
                .cssSelector("button[suffix='navigators']"))).click();
        getWait2().until(ExpectedConditions.elementToBeClickable(By
                .cssSelector("input[placeholder='Filter']"))).sendKeys(sourceName);

        return this;
    }

    public List<String> getRepositorySourceNames() {
        return getWait2()
                .until(ExpectedConditions.presenceOfAllElementsLocatedBy(By
                        .cssSelector("button.jenkins-dropdown__item:not([style*='display: none'])")))
                .stream()
                .map(WebElement::getText)
                .toList();
    }
}
