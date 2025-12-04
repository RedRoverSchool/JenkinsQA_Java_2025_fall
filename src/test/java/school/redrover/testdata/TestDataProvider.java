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

    @DataProvider(name = "ConfigurationMenuItem")
    Object[][] configurationOfProjectData() {
        return new Object[][]{
                {"freestyle01", "Freestyle project", "Configure", ProjectPage.FREESTYLE_PROJECT_PAGE},
                {"pipeline01", "Pipeline", "Configure", ProjectPage.PIPELINE_PROJECT_PAGE},
                {"multiConfig01", "Multi-configuration project", "Configure", ProjectPage.MULTI_CONFIGURATION_PROJECT_PAGE},
                {"folder01", "Folder", "Configuration", ProjectPage.FOLDER_PROJECT_PAGE},
                {"Multibranch01", "Multibranch Pipeline", "Configuration", ProjectPage.MULTIBRANCH_PIPELINE_PROJECT_PAGE},
                {"orgFolder01", "Organization Folder", "Configuration", ProjectPage.ORGANIZATION_FOLDER_PROJECT_PAGE}
        };
    }
}
