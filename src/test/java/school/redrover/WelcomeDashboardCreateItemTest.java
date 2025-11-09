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
        final long timestamp = System.currentTimeMillis();

        List<By> links = List.of(
                By.linkText("New Item"),
                By.linkText("Create a job")
        );
        Map<String, By> items = Map.of(
                "Freestyle Project (%s)".formatted(timestamp), By.className("hudson_model_FreeStyleProject"),
                "Pipeline (%s)".formatted(timestamp), By.className("org_jenkinsci_plugins_workflow_job_WorkflowJob"),
                "Multi-configuration Project (%s)".formatted(timestamp), By.className("hudson_matrix_MatrixProject"),
                "Folder (%s)".formatted(timestamp), By.className("com_cloudbees_hudson_plugins_folder_Folder"),
                "Multibranch Pipeline (%s)".formatted(timestamp), By.className("org_jenkinsci_plugins_workflow_multibranch_WorkflowMultiBranchProject"),
                "Organization Folder (%s)".formatted(timestamp), By.className("jenkins_branch_OrganizationFolder")
        );
        return links.stream()
                .flatMap(link -> items.entrySet().stream()
                        .map(entry -> new Object[]{link, entry.getKey(), entry.getValue()}))
                .toArray(Object[][]::new);
    }

    @Test(dataProvider = "newItemLocators")
    public void testSuccessfulItemCreation(By linkLocator, String itemName, By itemLocator) {
        getDriver().findElement(linkLocator).click();

        getDriver().findElement(By.id("name")).sendKeys(itemName);
        WebElement currentItem = getDriver().findElement(itemLocator);
        scrollToElement(currentItem);
        currentItem.click();
        getDriver().findElement(By.id("ok-button")).click();
        getDriver().findElement(By.name("Submit")).click();

        getWait2().until(ExpectedConditions.textToBePresentInElementLocated(By.tagName("h1"), itemName));
        Assert.assertEquals(getDriver().findElement(By.tagName("h1")).getText(), itemName);

        WebElement createdJobHeader = getDriver().findElement(By.className("page-headline"));
        Assert.assertEquals(createdJobHeader.getText(), itemName);

    }

    public void scrollToElement(WebElement element) {
        ((JavascriptExecutor) getDriver()).executeScript(
                "arguments[0].scrollIntoView({block: 'center', inline: 'center'});",
                element);
    }
}
