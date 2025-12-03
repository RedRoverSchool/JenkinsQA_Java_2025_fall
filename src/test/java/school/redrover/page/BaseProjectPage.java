package school.redrover.page;

import org.openqa.selenium.WebDriver;
import school.redrover.common.BasePage;

public abstract class BaseProjectPage extends BasePage {

    public BaseProjectPage(WebDriver driver) {
        super(driver);
    }

    public abstract <P extends BaseSideMenuItemPage> P clickConfigureLinkInSideMenu();

    public abstract String getHeadingText();
}
