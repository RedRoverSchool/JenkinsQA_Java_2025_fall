package school.redrover.page;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import school.redrover.common.BasePage;

public class ManageJenkinsPage extends BasePage {

    public ManageJenkinsPage(WebDriver driver) {
        super(driver);
    }

    public ManageUsersPage clickUserLink() {
        getDriver().findElement(By.xpath("//a[@href='securityRealm/']")).click();

        return new ManageUsersPage(getDriver());
    }

    public ConfigurationSystemPage clickConfigurationSystem() {
        getDriver().findElement(By.xpath("//a[@href='configure']")).click();

        return new ConfigurationSystemPage(getDriver());
    }
}
