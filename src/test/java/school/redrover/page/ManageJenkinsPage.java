package school.redrover.page;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import school.redrover.common.BasePage;

public class ManageJenkinsPage extends BasePage {

    public ManageJenkinsPage(WebDriver driver) {
        super(driver);
    }

    public ManageUsersPage clickUserButton() {
        getDriver().findElement(By.xpath("//a[@href='securityRealm/']")).click();

        return new ManageUsersPage(getDriver());
    }

    public CredentialsPage clickCredentialsLink() {
        getDriver().findElement(By.xpath("//a[@href = 'credentials']")).click();

        return new CredentialsPage(getDriver());
    }

    public ConfigurationSystemPage clickConfigurationSystem() {
        getDriver().findElement(By.xpath("//a[@href='configure']")).click();

        return new ConfigurationSystemPage(getDriver());
    }

    public String getHeadingText() {
        return getWait5().until(ExpectedConditions.presenceOfElementLocated(By.
                tagName("h1"))).getText().trim();
    }
}
