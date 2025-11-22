package school.redrover;

import org.testng.Assert;
import org.testng.annotations.Test;
import school.redrover.common.BaseTest;
import school.redrover.page.HomePage;

public class OrganisationFolder1Test extends BaseTest {

    private static final String FOLDER_NAME = "Organization Folder";

    @Test
    public void testCreateOrganisationFolder() {
        String OrganizationFolderName = new HomePage(getDriver())
                .clickCreateJob()
                .sendName(FOLDER_NAME)
                .selectOrganizationFolderAndSubmit()
                .clickSave()
                .getNameFolder();

        Assert.assertEquals(OrganizationFolderName, FOLDER_NAME);
    }
}
