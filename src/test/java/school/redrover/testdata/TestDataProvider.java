package school.redrover.testdata;

import org.testng.annotations.DataProvider;

public class TestDataProvider {

    @DataProvider(name = "Links")
    Object[][] linkData() {
        return new Object[][]{
                {"Create a job", "/newJob"},
                {"Set up an agent", "/new"},
                {"Configure a cloud", "/cloud/"},
        };
    }
}
