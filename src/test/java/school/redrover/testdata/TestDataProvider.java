package school.redrover.testdata;

import org.testng.annotations.DataProvider;

public class TestDataProvider {

    @DataProvider(name = "Links")
    Object[][] linkData() {
        return new Object[][]{
                {"Create a job", "/newJob", Page.NEW_ITEM_PAGE},
                {"Set up an agent", "/new", Page.NEW_NODE_PAGE},
                {"Configure a cloud", "/cloud/", Page.CLOUDS_PAGE},
        };
    }
}
