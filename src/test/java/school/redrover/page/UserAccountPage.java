package school.redrover.page;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import school.redrover.common.BasePage;


public class UserAccountPage extends BasePage {

    private static final By FULL_NAME_FIELD = By.name("_.fullName");

    public UserAccountPage(WebDriver driver) {
        super(driver);
    }

    public String getUserID() {
        return getDriver().findElement(By
                        .xpath("//div[@id='main-panel']/descendant::div[contains(text(),'User ID:')]"))
                .getText().substring(17);
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

    public RestApiPage clickRestApiLink(){
        getDriver().findElement(By.xpath("//a[@href='api/']")).click();

        return new RestApiPage(getDriver());
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
}
