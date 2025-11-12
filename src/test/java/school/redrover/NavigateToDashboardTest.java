package school.redrover;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;
import org.testng.annotations.Test;
import school.redrover.common.BaseTest;

import java.util.List;

public class NavigateToDashboardTest extends BaseTest {

    @Test
    public void testCheckAccessDashboardFromLogo(){
        clickElement(By.id("root-action-ManageJenkinsAction"));
        clickElement(By.linkText("Jenkins"));

        getWait5().until(ExpectedConditions.elementToBeClickable(
                getDriver().findElement(By.className("app-jenkins-logo")))).click();

    @Test
    public void testVerifyDashboardDisplay(){
        int countOfItem = 4;
        for (int i = 1; i <= countOfItem; i++) {
            createItem("TestProject_" + i);
        }

        List<WebElement> items = getDriver().findElements(By.cssSelector("tr[id]"));
        Assert.assertTrue(items.size() >= countOfItem,
                "Not all created Jenkins items are displayed on the Dashboard");

        for (WebElement element: items){
            System.out.println(element.getDomAttribute("id"));
            WebElement status = element.findElement(By.cssSelector("td[data='12']"));
            String itemName = element.getText().split(" ")[0];

            Assert.assertTrue(status.isDisplayed(), "Status icon not visible for: " + itemName);
            Assert.assertFalse(itemName.isEmpty(), "Item name is empty.");
        }
    }

    void createItem(String name){
        clickElement(By.linkText("New Item"));
        sendText(By.id("name"), name);
        clickElement(By.xpath("//li[@class='hudson_model_FreeStyleProject']"));
        clickElement(By.id("ok-button"));
        clickElement(By.cssSelector("button[value='Save']"));
        clickLogoToGoHome();
    }

    private void clickElement(By locator){
        getWait5().until(ExpectedConditions.elementToBeClickable(
                getDriver().findElement(locator))).click();
    }

    private void sendText(By locator, String text){
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