package school.redrover;

import org.testng.Assert;
import org.testng.annotations.Test;
import school.redrover.common.BaseTest;
import school.redrover.page.HomePage;

public class CreateNewItem8Test extends BaseTest {

    final String jobName = "NEW_TEST_JOB";

    @Test
    public void createMultibranchPipelineProjectItemTest() {
        String newProject = new HomePage(getDriver())
                .clickCreateJob()
                .sendName(jobName)
                .selectMultibranchPipelineAndSubmit()
                .gotoHomePage()
                .getProjectList()
                .get(0);
        Assert.assertEquals(jobName, newProject);
    }
}
