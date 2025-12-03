package school.redrover.page;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class MultiConfigurationProjectConfigurationPage extends BaseSideMenuItemPage {

    @FindBy(tagName = "h1")
    private WebElement headingText;

    public MultiConfigurationProjectConfigurationPage(WebDriver driver) {
        super(driver);
    }

    @Override
    protected void waitUntilPageLoad() {
        getWait5().until(ExpectedConditions.presenceOfElementLocated(By.name("Submit")));
    }

    @Override
    public String getHeadingText() {
        return headingText.getText();
    }
}
