package school.redrover.page;

import org.openqa.selenium.WebDriver;
import school.redrover.common.BasePage;
import school.redrover.type.BasePageWithHeading;


public abstract class BaseSideMenuItemPage extends BasePage implements BasePageWithHeading {

    public BaseSideMenuItemPage(WebDriver driver) {
        super(driver);
    }

    protected abstract void waitUntilPageLoad();
}
