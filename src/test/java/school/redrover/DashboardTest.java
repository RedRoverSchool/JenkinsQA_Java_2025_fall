package school.redrover;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;
import org.testng.annotations.Test;
import school.redrover.common.BaseTest;
import school.redrover.page.HomePage;

import java.util.List;

public class DashboardTest extends BaseTest {

    private static final List<String> CRAETED_JOBS_NAME = List.of(
            "FreestyleName1",
            "FreestyleName2",
            "FreestyleName3",
            "FreestyleName4",
            "FreestyleName5"
    );

    @Test
    public void testHomePageHeading() {
        WebElement actualHeading = getWait5()
                .until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(".empty-state-block > h1")));

        Assert.assertEquals(actualHeading.getText(), "Welcome to Jenkins!");
    }

    @Test
    public void testHomePageParagraph() {
        final String expectedParagraphText = "This page is where your Jenkins jobs will be displayed. " +
                "To get started, you can set up distributed builds or start building a software project.";

        WebElement actualParagraph = getWait5().until(ExpectedConditions.visibilityOfElementLocated(By.tagName("p")));

        Assert.assertEquals(actualParagraph.getText(), expectedParagraphText);
    }

    @Test
    public void testContentBlockLinks() {
        final List<String> expectedUrlEndpoints = List.of(
                "/newJob",
                "/computer/new",
                "/cloud/",
                "/#distributed-builds-architecture"
        );

        List<WebElement> contentBlockLinks = getWait5()
                .until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.cssSelector(".content-block > a")));

        Assert.assertFalse(contentBlockLinks.isEmpty());

        for (int i = 0; i < contentBlockLinks.size(); i++) {
            WebElement currentLink = contentBlockLinks.get(i);

            new Actions(getDriver())
                    .keyDown(Keys.CONTROL)
                    .click(currentLink)
                    .keyUp(Keys.CONTROL)
                    .perform();

            getWait5().until(ExpectedConditions.numberOfWindowsToBe(2));

            Object[] windowHandles = getDriver().getWindowHandles().toArray();
            getDriver().switchTo().window((String) windowHandles[1]);

            String currentUrl = getDriver().getCurrentUrl();
            String expectedUrlEndpoint = expectedUrlEndpoints.get(i);

            Assert.assertTrue(currentUrl.contains(expectedUrlEndpoint));

            getDriver().close();
            getDriver().switchTo().window((String) windowHandles[0]);
        }
    }

    @Test
    public void testCheckCreatedJobsOnDashboard(){
        HomePage homePage = new HomePage(getDriver());

        for (int i = 0; i < CRAETED_JOBS_NAME.size(); i++){
            homePage
                    .clickNewItemOnLeftMenu()
                    .sendName(CRAETED_JOBS_NAME.get(i))
                    .selectFreestyleProjectAndSubmit()
                    .gotoHomePage();
        }
        List<String> actualJobs = homePage.getProjectList();

        Assert.assertFalse(actualJobs.isEmpty(), "Item's list is empty!");
        Assert.assertEquals(actualJobs, CRAETED_JOBS_NAME, "Имена не совпадают!");
    }

    @Test
    public void testCheckDeleteViewOnDashboard() {
        final String viewName = "myView";

        int viewListSize = new HomePage(getDriver())
                .clickNewItemOnLeftMenu()
                .sendName(CRAETED_JOBS_NAME.get(0))
                .selectFreestyleProjectAndSubmit()
                .gotoHomePage()
                .clickPlusToCreateView()
                .sendViewName(viewName)
                .clickMyViewName()
                .clickCreateButtonForNewView()
                .clickDeleteViewOnSidebar()
                .clickYesToConfirmDelete()
                .getSizeOfViewNameList();

        Assert.assertEquals(viewListSize, 2, "Есть созданный пользователем View на Dashboard");
    }
}
