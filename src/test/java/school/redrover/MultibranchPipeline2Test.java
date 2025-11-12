package school.redrover;

import org.testng.Assert;
import org.testng.annotations.Test;
import school.redrover.common.BaseTest;
import school.redrover.page.HomePage;

import java.util.List;


public class MultibranchPipeline2Test extends BaseTest {

    @Test
    public void testCreateMultibranchPipelineByNew() {
        final String multibranchName = "MultibranchName";

        List<String> projectList = new HomePage(getDriver())
                .clickNewItem()
                .sendName(multibranchName)
                .selectMultibranchPipelineAndSubmit()
                .gotoHomePage()
                .getProjectList();

        Assert.assertNotEquals(projectList.size(),0);
        Assert.assertEquals(projectList.get(0), multibranchName);
    }
}