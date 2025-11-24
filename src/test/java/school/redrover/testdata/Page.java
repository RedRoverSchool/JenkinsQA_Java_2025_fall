package school.redrover.testdata;

import org.openqa.selenium.WebDriver;
import school.redrover.common.BasePage;
import school.redrover.page.CloudsPage;
import school.redrover.page.NewItemPage;
import school.redrover.page.NewNodePage;

public enum Page {

    NEW_ITEM_PAGE {
        @Override
        public BasePage createPage(WebDriver driver) {
            return new NewItemPage(driver);
        }
    },

    NEW_NODE_PAGE {
        @Override
        public BasePage createPage(WebDriver driver) {
            return new NewNodePage(driver);
        }
    },

    CLOUDS_PAGE {
        @Override
        public BasePage createPage(WebDriver driver) {
            return new CloudsPage(driver);
        }
    };

    public abstract BasePage createPage(WebDriver driver);
}
