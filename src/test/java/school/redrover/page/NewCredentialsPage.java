package school.redrover.page;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import school.redrover.common.BasePage;

public class NewCredentialsPage extends BasePage {

    public NewCredentialsPage(WebDriver driver) {
        super(driver);
    }

    public NewCredentialsPage sendUsernameIntoUsernameInputField(String username) {
        getDriver().findElement(By.name("_.username")).sendKeys(username);

        return this;
    }

    public NewCredentialsPage sendPasswordIntoPasswordInputField(String password) {
        getDriver().findElement(By.name("_.password")).sendKeys(password);

        return this;
    }

    public NewCredentialsPage sendDescriptionIntoDescriptionInputField(String description) {
        getDriver().findElement(By.name("_.description")).sendKeys(description);

        return this;
    }

    public GlobalCredentialsPage clickCreateButton() {
        getDriver().findElement(By.name("Submit")).click();

        return new GlobalCredentialsPage(getDriver());
    }
}
