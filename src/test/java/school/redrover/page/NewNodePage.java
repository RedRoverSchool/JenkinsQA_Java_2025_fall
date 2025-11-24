package school.redrover.page;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import school.redrover.common.BasePage;

public class NewNodePage extends BasePage {

    public NewNodePage(WebDriver driver) {
        super(driver);
    }

    public RestApiPage clickRestApiLink(){
        getDriver().findElement(By.xpath("//a[@href='api/']")).click();

        return new RestApiPage(getDriver());
    }

    public String getHeadingText() {
        return getDriver().findElement(By.xpath("//h1")).getText();
    }

    public boolean isFormDisplayed() {
        return getDriver().findElement(By.xpath("//form")).isDisplayed();
    }
}
