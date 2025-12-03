package school.redrover.page;

import org.openqa.selenium.WebDriver;
import school.redrover.common.BasePage;

public abstract class BaseSideMenuItemPage extends BasePage {

    public BaseSideMenuItemPage(WebDriver driver) {
        super(driver);
    }

    protected abstract void waitUntilPageLoad();

    public abstract String getHeadingText();
}
