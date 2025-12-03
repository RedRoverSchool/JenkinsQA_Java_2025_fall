package school.redrover;

import org.testng.Assert;
import org.testng.annotations.Test;
import school.redrover.common.BaseTest;
import school.redrover.page.HomePage;
import school.redrover.page.OrganizationFolderPage;

import java.util.List;

public class OrganizationFolderConfigurationTest extends BaseTest {

    private static final String FOLDER_NAME = "Organization Folder %d".formatted(System.currentTimeMillis());

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
    public void testDisplayNameTooltipLink() {
        String expectedExternalLink = "https://plugins.jenkins.io/branch-api";

        String displayNameTooltipLink = new HomePage(getDriver())
                .clickCreateJob()
                .sendName(FOLDER_NAME)
                .selectOrganizationFolderAndSubmit()
                .clickDisplayNameTooltip()
                .getDisplayNameTooltipLink();

        Assert.assertEquals(displayNameTooltipLink, expectedExternalLink);
    }

    @Test
    public void testRepositorySourcesFilterByNamePart() {
        String searchString = "github";
        String expectedRepositorySourceName = "GitHub Organization";

        List<String> repositorySourcesList = new HomePage(getDriver())
                .clickCreateJob()
                .sendName(FOLDER_NAME)
                .selectOrganizationFolderAndSubmit()
                .filterRepositorySources(searchString)
                .getRepositorySourceNames();

        Assert.assertEquals(repositorySourcesList.size(), 1);
        Assert.assertListContainsObject(
                repositorySourcesList,
                expectedRepositorySourceName,
                "No <%s> in list".formatted(expectedRepositorySourceName));
    }
}
