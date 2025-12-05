package school.redrover.page;

import org.openqa.selenium.WebDriver;
import school.redrover.common.BasePage;
import school.redrover.type.BasePageWithHeading;

public abstract class BaseProjectPage extends BasePage implements BasePageWithHeading {

    public BaseProjectPage(WebDriver driver) {
        super(driver);
    }

    public abstract <P extends BaseSideMenuItemPage> P clickConfigureLinkInSideMenu();

    protected abstract void waitUntilPageLoad();
}
