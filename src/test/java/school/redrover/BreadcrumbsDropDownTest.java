package school.redrover;

import org.testng.Assert;
import org.testng.annotations.Test;
import school.redrover.common.BaseTest;
import school.redrover.page.FolderPage;
import school.redrover.page.HomePage;

import java.util.List;

public class BreadcrumbsDropDownTest extends BaseTest {

    private static final String PARENT_FOLDER = "Folder1";
    private static final String CHILD_FOLDER = "Folder2";

    @Test
    public void testDisplayBreadcrumbsDropDownMenu() {
        List<String> breadcrumbTexts = new HomePage(getDriver())
                .clickCreateJob()
                .sendName(PARENT_FOLDER)
                .selectFolderAndSubmit()
                .gotoHomePage()
                .openProject(PARENT_FOLDER, new FolderPage(getDriver()))
                .clickNewItem()
                .sendName(CHILD_FOLDER)
                .selectFolderAndSubmit()
                .clickSave()
                .getBreadcrumbTexts();

        Assert.assertNotEquals(breadcrumbTexts.size(), 0);
        Assert.assertEquals(breadcrumbTexts.size(), 2);
    }

    @Test (dependsOnMethods = {"testDisplayBreadcrumbsDropDownMenu"})
    public void clickableBreadcrumbsDropDownMenu() {
        String title = new HomePage(getDriver())
                .openProject(PARENT_FOLDER, new FolderPage(getDriver()))
                .openFolderPage(CHILD_FOLDER)
                .clickBreadcrumbsItem(PARENT_FOLDER)
                .getInfo()
                .getDisplayName();

        Assert.assertEquals(title, PARENT_FOLDER);
    }
}