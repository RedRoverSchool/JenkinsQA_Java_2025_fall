package school.redrover;

import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.Test;
import school.redrover.common.BaseTest;
import school.redrover.page.*;


public class FreestyleProjectConfigurationSCMTest extends BaseTest {

    private static final String SCM_TITLE_EXPECTED = "Source Code Management";
    private static final String FREESTYLE_PROJECT_NAME = "FreestyleProject2025";

    @Test
    public void testAccessSCMInNewJob() {
        WebElement scmTitle = new HomePage(getDriver())
                .clickCreateJob()
                .sendName(FREESTYLE_PROJECT_NAME)
                .selectFreestyleProjectAndSubmit()
                .verifySCMTitleIsVisible();

        Assert.assertEquals(scmTitle.getText(), SCM_TITLE_EXPECTED);
    }

    @Test
    public void testSCMSectionElements() {
        FreestyleProjectConfigurationPage configPage = new HomePage(getDriver()).clickCreateJob()
                .sendName(FREESTYLE_PROJECT_NAME)
                .selectFreestyleProjectAndSubmit();

        Assert.assertTrue(configPage.getScmDescription().isDisplayed(),"SCM Description is not displayed or the description text doesn't match");
        Assert.assertEquals(configPage.getSelectedRadioLabel(), "None","Radio button 'None' should be selected by default");
        Assert.assertTrue(configPage.isGitOptionDisplayed(),"Radio button 'Git' should be displayed");
        Assert.assertEquals(configPage.getGitTooltipText(),"Help for feature: Git","Tooltip text should match expected value");
    }

    @Test
    public void testAccessSCMInExistingJob() {
        WebElement scmTitle = new HomePage(getDriver())
                .clickCreateJob()
                .sendName(FREESTYLE_PROJECT_NAME)
                .selectFreestyleProjectAndSubmit()
                .gotoHomePage()
                .openJobPage(FREESTYLE_PROJECT_NAME, new FreestyleProjectPage(getDriver()))
                .clickConfigure(FREESTYLE_PROJECT_NAME)
                .verifySCMTitleIsVisible();

        Assert.assertEquals(scmTitle.getText(), SCM_TITLE_EXPECTED);
    }

    @Test
    public void testNavigationToSCMViaMenu() {
        WebElement scmTitle = new HomePage(getDriver())
                .clickCreateJob()
                .sendName(FREESTYLE_PROJECT_NAME)
                .selectFreestyleProjectAndSubmit()
                .clickSourceCodeManagementMenuOption()
                .verifySCMTitleIsVisible();

        Assert.assertEquals(scmTitle.getText(), SCM_TITLE_EXPECTED);
    }

    @Test
    public void testNavigationToSCMByScrollingDown() {
        WebElement scmTitle = new HomePage(getDriver())
                .clickCreateJob()
                .sendName(FREESTYLE_PROJECT_NAME)
                .selectFreestyleProjectAndSubmit()
                .scrollToSourceCodeManagementWithJS()
                .verifySCMTitleIsVisible();

        Assert.assertEquals(scmTitle.getText(), SCM_TITLE_EXPECTED);
    }
}
