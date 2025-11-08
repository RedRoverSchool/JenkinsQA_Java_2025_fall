package school.redrover;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import school.redrover.common.BaseTest;

public class WelcomeDashboardCreateItemTest extends BaseTest {

    @DataProvider(name = "newItemLocators")
    public Object[][] newItemLocators() {
        final String newItemText = "New Item";
        final String createJobText = "Create a job";

        final By newItemLink = By.linkText(newItemText);
        final By createJobLink = By.linkText(createJobText);

        final By freestyleProjectItem = By.className("hudson_model_FreeStyleProject");
        final By pipelineItem = By.className("org_jenkinsci_plugins_workflow_job_WorkflowJob");
        final By multiConfigurationProjectItem = By.className("hudson_matrix_MatrixProject");
        final By folderItem = By.className("com_cloudbees_hudson_plugins_folder_Folder");
        final By multibranchPipelineItem = By.className("org_jenkinsci_plugins_workflow_multibranch_WorkflowMultiBranchProject");
        final By organizationFolderItem = By.className("jenkins_branch_OrganizationFolder");

        return new Object[][]{
                {newItemLink, newItemText, freestyleProjectItem, "Test Freestyle Project"},
                {newItemLink, newItemText, pipelineItem, "Test Pipeline"},
                {newItemLink, newItemText, multiConfigurationProjectItem, "Test Multi-configuration Project"},
                {newItemLink, newItemText, folderItem, "Test Folder"},
                {newItemLink, newItemText, multibranchPipelineItem, "Test Multibranch Pipeline"},
                {newItemLink, newItemText, organizationFolderItem, "Test Organization Folder"},

                {createJobLink, createJobText, freestyleProjectItem, "Test Freestyle Project"},
                {createJobLink, createJobText, pipelineItem, "Test Pipeline"},
                {createJobLink, createJobText, multiConfigurationProjectItem, "Test Multi-configuration Project"},
                {createJobLink, createJobText, folderItem, "Test Folder"},
                {createJobLink, createJobText, multibranchPipelineItem, "Test Multibranch Pipeline"},
                {createJobLink, createJobText, organizationFolderItem, "Test Organization Folder"},
        };
    }

    @Test(dataProvider = "newItemLocators")
    public void testSuccessfulItemCreation(By linkLocator, String linkText, By itemLocator, String itemName) {
        getDriver().findElement(linkLocator).click();

        getDriver().findElement(By.id("name")).sendKeys(itemName);
        WebElement currentItem = getDriver().findElement(itemLocator);
        scrollToElement(currentItem);
        currentItem.click();
        getDriver().findElement(By.id("ok-button")).click();
        getDriver().findElement(By.name("Submit")).click();

        WebElement createdItemHeader = getDriver().findElement(By.className("page-headline"));
        Assert.assertEquals(
                createdItemHeader.getText(),
                itemName,
                "Start link: <%s>, Test for item: <%s>".formatted(linkText, itemName));
    }

    public void scrollToElement(WebElement element) {
        JavascriptExecutor js = (JavascriptExecutor) getDriver();
        js.executeScript("""
                const el = arguments[0];
                const rect = el.getBoundingClientRect();
                const absoluteY = rect.top + window.pageYOffset - (window.innerHeight / 2) + (rect.height / 2);
                window.scrollTo({ top: absoluteY, behavior: 'instant' });
                """, element);
    }
}
