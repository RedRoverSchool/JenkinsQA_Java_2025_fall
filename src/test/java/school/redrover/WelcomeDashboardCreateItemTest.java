package school.redrover;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import school.redrover.common.BaseTest;

import java.util.List;
import java.util.Map;

public class WelcomeDashboardCreateItemTest extends BaseTest {

    @DataProvider(name = "newItemLocators")
    public Object[][] newItemLocators() {
        List<By> links = List.of(
                By.linkText("New Item"),
                By.linkText("Create a job")
        );

        Map<By, String> items = Map.of(
                By.className("hudson_model_FreeStyleProject"), "Test - Freestyle Project",
                By.className("org_jenkinsci_plugins_workflow_job_WorkflowJob"), "Test - Pipeline",
                By.className("hudson_matrix_MatrixProject"), "Test - Multi-configuration Project",
                By.className("com_cloudbees_hudson_plugins_folder_Folder"), "Test - Folder",
                By.className("org_jenkinsci_plugins_workflow_multibranch_WorkflowMultiBranchProject"), "Test - Multibranch Pipeline",
                By.className("jenkins_branch_OrganizationFolder"), "Test - Organization Folder"
        );

        return links.stream()
                .flatMap(link -> items.entrySet().stream()
                        .map(entry -> new Object[]{link, entry.getKey(), entry.getValue()}))
                .toArray(Object[][]::new);
    }

    @Test(dataProvider = "newItemLocators")
    public void testSuccessfulItemCreation(By linkLocator, By itemLocator, String itemName) {
        getDriver().findElement(linkLocator).click();

        getDriver().findElement(By.id("name")).sendKeys(itemName);
        WebElement currentItem = getDriver().findElement(itemLocator);
        scrollToElement(currentItem);
        currentItem.click();
        getDriver().findElement(By.id("ok-button")).click();
        getDriver().findElement(By.name("Submit")).click();

        WebElement createdItemHeader = getWait5().until(ExpectedConditions.visibilityOfElementLocated(By
                .xpath("//h1")));
        Assert.assertEquals(createdItemHeader.getText(), itemName);
    }

    public void scrollToElement(WebElement element) {
        ((JavascriptExecutor) getDriver()).executeScript(
                "arguments[0].scrollIntoView({block: 'center', inline: 'center'});",
                element);
    }
}
