package school.redrover;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;
import org.testng.annotations.Test;
import school.redrover.common.BaseTest;
import school.redrover.common.TestUtils;
import school.redrover.page.FreestyleProjectConfigurationPage;
import school.redrover.page.HomePage;

import java.util.List;

import static org.openqa.selenium.support.ui.ExpectedConditions.visibilityOfAllElementsLocatedBy;


public class ConfigurationMatrixTest extends BaseTest {

    private static final String PROJECT_NAME = "Multiconfiguration project name";

    private void createConfigurationMatrix() {
        new HomePage(getDriver())
                .clickSidebarNewItem()
                .sendName(PROJECT_NAME)
                .selectMultiConfigurationProjectAndSubmit();
    }

    @Test
    public void testSectionDisplayed() {
        String configurationMatrixText = new HomePage(getDriver())
                .clickSidebarNewItem()
                .sendName(PROJECT_NAME)
                .selectMultiConfigurationProjectAndSubmit()
                .getConfigurationMatrixText();

        Assert.assertEquals(configurationMatrixText, "Configuration Matrix",
                "Configuration Matrix header is not visible");
    }

    @Test
    public void testAddAxisMenuOnlyUserDefined() {

        createConfigurationMatrix();

        By addAxisLocator = By.cssSelector("[suffix=axis]");
        getWait10().until(ExpectedConditions.visibilityOfElementLocated(addAxisLocator));
        TestUtils.clickJS(getDriver(), addAxisLocator);

        By itemMenu = By.cssSelector(".jenkins-dropdown__item");
        List<WebElement> items = getWait10().until(visibilityOfAllElementsLocatedBy(itemMenu));

        Assert.assertEquals(items.size(), 1, "Dropdown should contain exactly one item");
        Assert.assertEquals(items.get(0).getText().trim(), "User-defined Axis",
                "The only option must be 'User-defined Axis'");
    }

    @Test
    public void testSetUpEnvironmentAfterRefresh() {
        new HomePage(getDriver())
                .clickCreateJob()
                .sendName(PROJECT_NAME)
                .selectFreestyleProjectAndSubmit()
                .clickEnvironmentMenuOption();

        String urlBeforeRefresh = new FreestyleProjectConfigurationPage(getDriver())
                .getConfigUrl();

        String urlAfterRefresh = new FreestyleProjectConfigurationPage(getDriver())
                .refreshPage()
                .getConfigUrl();

        Assert.assertEquals(urlAfterRefresh, urlBeforeRefresh);
    }
}