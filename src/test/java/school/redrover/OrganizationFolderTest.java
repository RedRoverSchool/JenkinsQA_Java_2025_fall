package school.redrover;

import org.testng.Assert;
import org.testng.annotations.Test;
import school.redrover.common.BaseTest;
import school.redrover.page.HomePage;
import school.redrover.page.OrganizationFolderPage;
import java.util.List;

public class OrganizationFolderTest extends BaseTest {

    private static final String FOLDER_NAME = "Organization Folder";

    @Test
    public void testCreateOrganisationFolder() {
        String OrganizationFolderName = new HomePage(getDriver())
                .clickCreateJob()
                .sendName(FOLDER_NAME)
                .selectOrganizationFolderAndSubmit()
                .clickSave()
                .getDisplayNameOrganizationFolder();

        Assert.assertEquals(OrganizationFolderName, FOLDER_NAME);
    }

    @Test
    public void testAddDisplayNameAndDescription() {
        String expectedDisplayName = "New folder name";
        String expectedDescription = "New Description";

        OrganizationFolderPage organizationFolderPage = new HomePage(getDriver())
                .clickNewItemOnLeftMenu()
                .sendName(FOLDER_NAME)
                .selectOrganizationFolderAndSubmit()
                .inputDisplayName(expectedDisplayName)
                .inputDescription(expectedDescription)
                .clickSave();

        String actualDisplayName = organizationFolderPage.getDisplayNameOrganizationFolder();
        String actualDescription = organizationFolderPage.getDescriptionOrganizationFolder();

        Assert.assertEquals(actualDisplayName, expectedDisplayName);
        Assert.assertEquals(actualDescription, expectedDescription);
    }
    @Test
    public void testDeleteOrganizationFolder() {
        List<String> actualListOfItems = new HomePage(getDriver())
                .clickNewItemOnLeftMenu()
                .sendName(FOLDER_NAME)
                .selectOrganizationFolderAndSubmit()
                .clickSave()
                .clickDelete()
                .clickYesConfirmationDelete()
                .getProjectList();

        Assert.assertFalse(actualListOfItems.contains(FOLDER_NAME),
                "Folder should be deleted but is still present in the list");
    }
}