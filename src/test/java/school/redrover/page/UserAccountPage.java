package school.redrover.page;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import school.redrover.common.BasePage;


public class UserAccountPage extends BasePage {

    private static final By FULL_NAME_FIELD = By.name("_.fullName");
    private static final By EMAIL_FIELD = By.xpath("//*[@id='main-panel']//section[4]/div[4]/div[1]/input");


    public UserAccountPage(WebDriver driver) {
        super(driver);
    }


    public UserAccountPage sendFullName(String fullName) {
        WebElement fullNameField = getWait5().until(ExpectedConditions.visibilityOfElementLocated(FULL_NAME_FIELD));
        fullNameField.clear();
        fullNameField.sendKeys(fullName);

        return this;
    }

    public <P extends BasePage> P clickSave(P returnedPage) {
        getWait5().until(ExpectedConditions.visibilityOfElementLocated(By.name("Submit"))).click();

        return returnedPage;
    }

    public UserAccountPage editDescription(String text) {
        getDriver().findElement(By.id("description-link")).click();
        getDriver().findElement(By.name("description")).sendKeys(text);
        getDriver().findElement(By.name("Submit")).click();

        return this;
    }

    public String getDescriptionText() {
        return getWait2().until(ExpectedConditions.visibilityOfElementLocated(By.id("description-content"))).getText();
    }

    public UserAccountPage editEmail(String EMAIL) {
        WebElement emailField = getWait5().until(ExpectedConditions.visibilityOfElementLocated(EMAIL_FIELD));
        emailField.clear();
        emailField.sendKeys(EMAIL);
        getDriver().findElement(By.name("Apply")).click();

        return this;
    }

    public String getEmailText() throws InterruptedException {
        Thread.sleep(10000);
        return getWait10().until(ExpectedConditions.visibilityOfElementLocated(EMAIL_FIELD)).getCssValue("value");
    }

}
