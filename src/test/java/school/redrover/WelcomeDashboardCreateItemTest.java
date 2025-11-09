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
        WebElement h1 = getDriver().findElement(By.tagName("h1"));
        System.out.println("-----> class: " + h1.getAttribute("class"));
        System.out.println("-----> text: " + h1.getText());

        JavascriptExecutor js = (JavascriptExecutor) getDriver();
        String allAttrs = (String) js.executeScript("""
                const el = arguments[0];
                const attrs = {};
                for (const a of el.attributes) attrs[a.name] = a.value;
                return JSON.stringify(attrs, null, 2);
                """, h1);
        System.out.println("-----> " + allAttrs);

        System.out.println("-----> All h1 count: " +
                getDriver().findElements(By.tagName("h1")).size());
        List<WebElement> h1s = getDriver().findElements(By.tagName("h1"));
        for (int i = 0; i < h1s.size(); i++) {
            WebElement el = h1s.get(i);
            System.out.printf("-----> H1[%d]: text='%s', class='%s'%n", i, el.getText(), el.getAttribute("class"));
        }

        String html = h1.getAttribute("outerHTML");
        System.out.println("-----> HTML элемента:\n" + html);


        Assert.assertEquals(h1.getText(), itemName);

        WebElement createdJobHeader = getDriver().findElement(By.className("page-headline"));
        Assert.assertEquals(createdJobHeader.getText(), itemName);

    }

    public void scrollToElement(WebElement element) {
        ((JavascriptExecutor) getDriver()).executeScript(
                "arguments[0].scrollIntoView({block: 'center', inline: 'center'});",
                element);
    }
}
