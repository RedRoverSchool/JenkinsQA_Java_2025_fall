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
        By newItemLink = By.linkText("New Item");
        By createJobLink = By.linkText("Create a job");

        By freestyleProjectItem = By.className("hudson_model_FreeStyleProject");
        By pipelineItem = By.className("org_jenkinsci_plugins_workflow_job_WorkflowJob");
        By multiConfigurationProjectItem = By.className("hudson_matrix_MatrixProject");
        By folderItem = By.className("com_cloudbees_hudson_plugins_folder_Folder");
        By multibranchPipelineItem = By.className("org_jenkinsci_plugins_workflow_multibranch_WorkflowMultiBranchProject");
        By organizationFolderItem = By.className("jenkins_branch_OrganizationFolder");

        return new Object[][]{
                {newItemLink, freestyleProjectItem, "Test Freestyle Project"},
                {newItemLink, pipelineItem, "Test Pipeline"},
                {newItemLink, multiConfigurationProjectItem, "Test Multi-configuration Project"},
                {newItemLink, folderItem, "Test Folder"},
                {newItemLink, multibranchPipelineItem, "Test Multibranch Pipeline"},
                {newItemLink, organizationFolderItem, "Test Organization Folder"},

                {createJobLink, freestyleProjectItem, "Test Freestyle Project"},
                {createJobLink, pipelineItem, "Test Pipeline"},
                {createJobLink, multiConfigurationProjectItem, "Test Multi-configuration Project"},
                {createJobLink, folderItem, "Test Folder"},
                {createJobLink, multibranchPipelineItem, "Test Multibranch Pipeline"},
                {createJobLink, organizationFolderItem, "Test Organization Folder"},
        };
    }

    @Test(dataProvider = "newItemLocators")
    public void testSuccessfulItemCreation(By linkLocator, By itemLocator, String createdItemName) {
        getDriver().findElement(linkLocator).click();

        getDriver().findElement(By.id("name")).sendKeys(createdItemName);
        WebElement currentItem = getDriver().findElement(itemLocator);
        scrollToElement(currentItem);
        currentItem.click();
        getDriver().findElement(By.id("ok-button")).click();
        getDriver().findElement(By.name("Submit")).click();

        WebElement createdJobHeader = getDriver().findElement(By.className("page-headline"));
        Assert.assertEquals(createdJobHeader.getText(), createdItemName);
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
