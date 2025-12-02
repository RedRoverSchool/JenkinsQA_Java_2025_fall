package school.redrover.page;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import school.redrover.common.BasePage;

public class OrganizationFolderConfigurationPage extends BasePage {

    public OrganizationFolderConfigurationPage(WebDriver driver) {
        super(driver);
    }

    public OrganizationFolderPage clickSave() {
        WebElement button = getDriver().findElement(By.name("Submit"));
        button.click();
        getWait5().until(ExpectedConditions.invisibilityOf(button));

        return new OrganizationFolderPage(getDriver());
    }

    public OrganizationFolderConfigurationPage inputDisplayName(String name) {
        getWait2().until(ExpectedConditions.visibilityOfElementLocated(By.name("_.displayNameOrNull"))).sendKeys(name);

        return this;
    }

    public OrganizationFolderConfigurationPage inputDescription(String name) {
        getWait2().until(ExpectedConditions.visibilityOfElementLocated(By.name("_.description"))).sendKeys(name);

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
}
