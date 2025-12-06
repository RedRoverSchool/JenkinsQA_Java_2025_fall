package school.redrover;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Ignore;
import org.testng.annotations.Test;
import school.redrover.common.BaseTest;
import school.redrover.page.HomePage;

import java.util.List;
import java.time.Duration;

public class JenkinsManagementTest extends BaseTest {
    private final String TITLE_TEXT = "Dashboard - Jenkins";
    private static final String SETTING_TITLE = "System";


    @DataProvider
    public Object[][] systemConfigurationItems() {
        return new String[][]{
                {"System"},
                {"Tools"},
                {"Plugins"},
                {"Nodes"},
                {"Clouds"},
                {"Appearance"}
        };
    }

    private void openGlobalProperties() {
        getDriver().findElement(By.id("root-action-ManageJenkinsAction")).click();
        getDriver().findElement(By.xpath("//a[@href='configure']")).click();

        new Actions(getDriver())
                .scrollByAmount(0, 1500)
                .perform();
    }

    @Test
    public void testDeferredWipeoutSettingIsSaved() {
        openGlobalProperties();

        WebElement checkbox = getDriver().findElement(By.cssSelector("[id='cb3'] + label"));
        checkbox.click();

        getDriver().findElement(By.name("Submit")).click();

        WebDriverWait wait = new WebDriverWait(getDriver(), Duration.ofSeconds(2));
        wait.until(ExpectedConditions.textToBe(By.tagName("h1"), "Welcome to Jenkins!"));

        openGlobalProperties();

        Assert.assertTrue(getDriver().findElement(By.cssSelector("[id='cb3']")).isSelected(), "Checkbox is not selected");

        checkbox = getDriver().findElement(By.cssSelector("[id='cb3'] + label"));
        checkbox.click();
        getDriver().findElement(By.name("Apply")).click();
    }

    @Test
    public void testDeferredWipeoutTooltip() {
        final String expectedTooltipText = "Help for feature: Disable deferred wipeout on this node";

        openGlobalProperties();

        WebElement tooltipButton = getDriver().findElement(By.xpath("//a[contains(@tooltip,'Disable deferred wipeout on this node')]"));

        Actions actions = new Actions(getDriver());
        actions
                .moveToElement(tooltipButton)
                .perform();

        WebDriverWait wait = new WebDriverWait(getDriver(), Duration.ofSeconds(3));
        wait.until(ExpectedConditions.attributeToBeNotEmpty(tooltipButton, "aria-describedby"));

        String tooltipId = tooltipButton.getAttribute("aria-describedby");

        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id(tooltipId)));
        String actualTooltipText = getDriver().findElement(By.id(tooltipId)).getText().trim();

        Assert.assertNotNull(tooltipId, "Tooltip id is null");
        Assert.assertEquals(actualTooltipText, expectedTooltipText, "Unexpected tooltip");
    }

    @Test
    public void testDeferredWipeoutHintText() {
        final String expectedHintText =
                "During the workspace cleanup disable improved deferred wipeout method. " +
                        "By default deferred wipeout is used if desired.";

        openGlobalProperties();
        getDriver().findElement(By.xpath("//a[contains(@tooltip,'Disable deferred wipeout on this node')]")).click();

        By hintLocator = By.xpath("//div[@nameref='cb3']//div[@class='help']/div[1]");

        new WebDriverWait(getDriver(), Duration.ofSeconds(3))
                .until(ExpectedConditions.visibilityOfElementLocated(hintLocator));

        String actualHintText = getDriver()
                .findElement(hintLocator)
                .getText();

        Assert.assertEquals(actualHintText, expectedHintText, "Unexpected tooltip");
    }

    @Test
    public void testCheckAccessDashboardFromLogo() {
        String title = new HomePage(getDriver())
                .clickManageJenkinsGear()
                .gotoHomePage()
                .getTitle();

        Assert.assertEquals(title, TITLE_TEXT);
    }

    @Test
    public void testSearchResultsList() {
        List<String> searchResults = new HomePage(getDriver())
                .clickManageJenkinsGear()
                .sendTitle(SETTING_TITLE)
                .getSearchResults();

        Assert.assertEquals(searchResults, List.of("System", "System Information", "System Log"));
    }

    @Test(dataProvider = "systemConfigurationItems")
    public void testSearchDropdownItemsSystemConfiguration(String itemName){
        String dropdownResult = new HomePage(getDriver())
                .clickManageJenkinsGear()
                .sendTitle(itemName)
                .getSearchResultName();

        Assert.assertEquals(dropdownResult, itemName, "Названия пунктов настроек не совпадают");
    }

    @Ignore
    @Test
    public void testSearchAndOpenSetting() {
        String searchHeading = new HomePage(getDriver())
                .clickManageJenkinsGear()
                .sendTitle(SETTING_TITLE)
                .clickSearchResult()
                .getHeadingText();

        Assert.assertEquals(searchHeading, "System");
    }
}
