package school.redrover;

import org.testng.Assert;
import org.testng.annotations.Test;
import school.redrover.common.BaseTest;
import school.redrover.page.HomePage;

public class NodeTest extends BaseTest {
    private static final String NODE_NAME = "SecondNode";
    private static final String NODE_DESCRIPTION = "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do " +
            "eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud " +
            "exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in " +
            "reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat " +
            "cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum.";

    private static final String USAGE_VALUE_NORMAL = "NORMAL";
    private static final String USAGE_VALUE_EXCLUSIVE = "EXCLUSIVE";
    private static final String LAUNCH_METHOD_JNLPLauncher = "hudson.slaves.JNLPLauncher";
    private static final String LAUNCH_METHOD_SSHLauncher = "hudson.plugins.sshslaves.SSHLauncher";
    private static final String[] LABELS_ARRAY = {
            "docker build",
            "windows test",
            "windows staging",
            "deploy prod"};
    private static final String[] AVAILABILITY_OPTION = {
            "Keep this agent online as much as possible",
            "Bring this agent online according to a schedule",
            "Bring this agent online when in demand, and take offline when idle"};

    private static final Integer[] NODE_PROPERTIES = {
            0,1,2,3
    };

    @Test
    public void testAddNode() {
       String nodeName=  new HomePage(getDriver())
                .clickManageJenkinsGear()
                .clickNodeConfigurationSystem()
                .goToNewNodePage()
                .enterNodeName(NODE_NAME)
                .selectTypeNode()
                .createFormNode()
                .addDescription(NODE_DESCRIPTION)
                .enterNumberExecutors(3)
                .addLabels(LABELS_ARRAY)
                .selectUsageOption(USAGE_VALUE_EXCLUSIVE)
                .selectLaunchOption(LAUNCH_METHOD_JNLPLauncher)
                .selectAvailabilityOption(AVAILABILITY_OPTION[1])
                .checkNodeProperties(NODE_PROPERTIES[1])
                .checkNodeProperties(NODE_PROPERTIES[2])
                .submitNodeForm()
                .findNodesInList(NODE_NAME);

        Assert.assertEquals(nodeName,  NODE_NAME);
    }
}
