package school.redrover.page;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import school.redrover.common.BasePage;

public class FreestyleProjectPage extends BasePage {

    public FreestyleProjectPage(WebDriver driver) {
        super(driver);
    }

    public FreestyleProjectConfigurationPage clickConfigure(String projectName) {
        getDriver().findElement(By.xpath("//a[@href='/job/%s/configure']".formatted(projectName))).click();

        return new FreestyleProjectConfigurationPage(getDriver());
    }

    public String getHeadingText() {
        return getWait2().until(ExpectedConditions.presenceOfElementLocated(By.
                tagName("h1"))).getText();
    }
}