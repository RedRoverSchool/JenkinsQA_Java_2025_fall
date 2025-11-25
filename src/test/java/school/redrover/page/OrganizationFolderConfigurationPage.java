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
        getWait2().until(ExpectedConditions.invisibilityOf(button));

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
}
