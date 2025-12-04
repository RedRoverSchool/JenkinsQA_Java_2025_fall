package school.redrover.page;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import school.redrover.common.BasePage;

import java.util.List;
import java.util.function.Consumer;

public class GlobalCredentialsPage extends BasePage {

    public GlobalCredentialsPage(WebDriver driver) {
        super(driver);
    }

    public NewCredentialsPage clickAddCredentialsButton() {
        getDriver().findElement(By.xpath("//a[@href = 'newCredentials']")).click();

        return new NewCredentialsPage(getDriver());
    }

    public List<WebElement> getGlobalCredentialsList() {
        return getDriver().findElements(By.xpath("//table[contains(@class,'jenkins-table')]//tbody//tr/td[3]"));
    }

    public GlobalCredentialsPage getGlobalCredentialsList(Consumer<List<WebElement>> listConsumer) {
        listConsumer.accept(getGlobalCredentialsList());

        return this;
    }
}
