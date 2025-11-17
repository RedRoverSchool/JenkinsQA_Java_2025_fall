package school.redrover.page;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.interactions.Actions;
import school.redrover.common.BasePage;

public class CredentialsPage extends BasePage {

    public CredentialsPage(WebDriver driver) {
        super(driver);
    }

    public GlobalCredentialsPage clickGlobalLink() {

        new Actions(getDriver())
                .doubleClick(getDriver().findElement(By.xpath("//a[contains(text(), 'global')]")))
                .perform();

        return new GlobalCredentialsPage(getDriver());
    }
}
