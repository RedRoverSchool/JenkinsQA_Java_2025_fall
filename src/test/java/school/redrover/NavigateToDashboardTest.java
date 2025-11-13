package school.redrover;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;
import org.testng.annotations.Test;
import school.redrover.common.BaseTest;

import java.util.ArrayList;
import java.util.List;

public class NavigateToDashboardTest extends BaseTest {
    private final List<String> createdProjects = new ArrayList<>();
    private final String logoText = "Jenkins";
    private final String newItemText = "New Item";

    @Test
    public void testCheckAccessDashboardFromLogo() {
        clickElement(By.id("root-action-ManageJenkinsAction"));
        clickElement(By.linkText(logoText));

        Assert.assertTrue(getDriver().getTitle().toLowerCase().contains("dashboard"));
    }

    @Test
    public void testVerifyDashboardDisplay() {
        int countOfItem = 4;
        for (int i = 1; i <= countOfItem; i++) {
            String itemName = "TestProject_" + i;
            createItem(itemName);
            createdProjects.add(itemName);

        }

        List<WebElement> items = getDriver().findElements(By.cssSelector("tr[id]"));
        Assert.assertTrue(items.size() >= countOfItem,
                "Not all created Jenkins items are displayed on the Dashboard");

        for (WebElement element : items) {
            System.out.println(element.getDomAttribute("id"));
            WebElement status = element.findElement(By.cssSelector("td[data='12']"));
            String itemName = element.getText().split(" ")[0];

            Assert.assertTrue(status.isDisplayed(), "Status icon not visible for: " + itemName);
            Assert.assertFalse(itemName.isEmpty(), "Item name is empty.");
        }
    }

    @Test(dependsOnMethods = "testVerifyDashboardDisplay")
    public void testVerifyNavigationBehavior(){
        String item1 = createdProjects.get(0);
        clickElement(By.linkText(item1));
        Assert.assertTrue(getDriver().getCurrentUrl().contains(item1));
        Assert.assertEquals(getDriver().getTitle(),"TestProject_1 - Jenkins");

        clickElement(By.linkText(logoText));
        Assert.assertTrue(getDriver().getTitle().toLowerCase().contains("dashboard"));

        clickElement(By.linkText(newItemText));
        Assert.assertTrue(getDriver().getCurrentUrl().contains("newJob"));

        clickElement(By.linkText(logoText));
        Assert.assertTrue(getDriver().getTitle().toLowerCase().contains("dashboard"));
    }

    void createItem(String name) {
        clickElement(By.linkText(newItemText));
        sendText(By.id("name"), name);
        clickElement(By.xpath("//li[@class='hudson_model_FreeStyleProject']"));
        clickElement(By.id("ok-button"));
        clickElement(By.cssSelector("button[value='Save']"));
        clickLogoToGoHome();
    }

    private void clickElement(By locator) {
        getWait5().until(ExpectedConditions.elementToBeClickable(
                getDriver().findElement(locator))).click();
    }

    private void sendText(By locator, String text) {
        getWait5().until(ExpectedConditions.visibilityOfElementLocated(locator)).clear();
        getDriver().findElement(locator).sendKeys(text);
    }

    public void clickLogoToGoHome() {
        By logoLocator = By.cssSelector("a[href='/'], .jenkins-home-link, .jenkins-breadcrumbs__link[href='/']");
        for (int i = 0; i < 3; i++) {
            try {
                ((JavascriptExecutor) getDriver()).executeScript("arguments[0].click();",
                        getWait10().until(ExpectedConditions.elementToBeClickable(logoLocator)));

                getWait10().until(ExpectedConditions.or(
                        ExpectedConditions.presenceOfElementLocated(By.cssSelector("a[href$='/newJob']")),
                        ExpectedConditions.titleContains("Dashboard")
                ));
                return;
            } catch (StaleElementReferenceException e) {
                System.out.println("Retrying click on Jenkins logo...");
            }
        }
        throw new IllegalStateException("Failed to navigate to Dashboard via logo");
    }
}