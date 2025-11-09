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
//                By.linkText("New Item"),
                By.linkText("Create a job")
        );
        Map<String, By> items = Map.of(
//                "Freestyle Project (%s)".formatted(timestamp), By.className("hudson_model_FreeStyleProject"),
//                "Pipeline (%s)".formatted(timestamp), By.className("org_jenkinsci_plugins_workflow_job_WorkflowJob"),
                "Multi-configuration Project (%s)".formatted(timestamp), By.className("hudson_matrix_MatrixProject"),
//                "Folder (%s)".formatted(timestamp), By.className("com_cloudbees_hudson_plugins_folder_Folder"),
                "Multibranch Pipeline (%s)".formatted(timestamp), By.className("org_jenkinsci_plugins_workflow_multibranch_WorkflowMultiBranchProject")//,
//                "Organization Folder (%s)".formatted(timestamp), By.className("jenkins_branch_OrganizationFolder")
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
//        System.out.println("-----> class: " + element.getAttribute("class"));
//        System.out.println("-----> text: " + element.getText());
//
//        JavascriptExecutor js = (JavascriptExecutor) getDriver();
//        String allAttrs = (String) js.executeScript("""
//                const el = arguments[0];
//                const attrs = {};
//                for (const a of el.attributes) attrs[a.name] = a.value;
//                return JSON.stringify(attrs, null, 2);
//                """, element);
//        System.out.println("-----> " + allAttrs);
//
//        System.out.println("-----> All h1 count: " +
//                getDriver().findElements(By.tagName("h1")).size());
//        List<WebElement> h1s = getDriver().findElements(By.tagName("h1"));
//        for (int i = 0; i < h1s.size(); i++) {
//            WebElement el = h1s.get(i);
//            System.out.printf("-----> H1[%d]: text='%s', class='%s'%n", i, el.getText(), el.getAttribute("class"));
//        }
        try {
            Thread.sleep(10000);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        String html = getDriver().findElement(By.tagName("html")).getAttribute("outerHTML");
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

/*
* Login
-----> class:
-----> text: Multibranch Pipeline (1762724021088)
-----> {}
-----> All h1 count: 1
-----> H1[0]: text='Multibranch Pipeline (1762724021088)', class=''
-----> HTML элемента:
*
<h1><svg class="symbol-git-branch-outline plugin-ionicons-api icon-xlg" tooltip="Multibranch Pipeline" xmlns="http://www.w3.org/2000/svg" aria-hidden="true" viewBox="0 0 512 512" title="Multibranch Pipeline"><circle cx="160" cy="96" fill="none" r="48" stroke="currentColor" stroke-linecap="round" stroke-linejoin="round" stroke-width="32"></circle><circle cx="160" cy="416" fill="none" r="48" stroke="currentColor" stroke-linecap="round" stroke-linejoin="round" stroke-width="32"></circle><path d="M160 368V144" fill="none" stroke="currentColor" stroke-linecap="round" stroke-linejoin="round" stroke-width="32"></path><circle cx="352" cy="160" fill="none" r="48" stroke="currentColor" stroke-linecap="round" stroke-linejoin="round" stroke-width="32"></circle><path d="M352 208c0 128-192 48-192 160" fill="none" stroke="currentColor" stroke-linecap="round" stroke-linejoin="round" stroke-width="32"></path></svg>
    Multibranch Pipeline (1762724021088)
  </h1>
  *
Execution time is 11.377 sec
Run school.redrover.WelcomeDashboardCreateItemTest.testSuccessfulItemCreation
Clear data
Open the browser
Nov 09, 2025 9:33:54 PM org.openqa.selenium.devtools.CdpVersionFinder findNearestMatch
Get the web page
Login
WARNING: Unable to find an exact match for CDP version 142, returning the closest version; found: 139; Please update to a Selenium version that supports CDP version 142
-----> class: matrix-project-headline page-headline
-----> text: Multi-configuration Project (1762724021088)
-----> {
  "class": "matrix-project-headline page-headline"
}
-----> All h1 count: 1
-----> H1[0]: text='Multi-configuration Project (1762724021088)', class='matrix-project-headline page-headline'
-----> HTML элемента:
*
<h1 class="matrix-project-headline page-headline">Multi<wbr>-configuration Project <wbr>(1762724021088)</h1>
*
Execution time is 1.221 sec
Run school.redrover.WelcomeDashboardCreateItemTest.testSuccessfulItemCreation
Clear data
Open the browser
Nov 09, 2025 9:33:56 PM org.openqa.selenium.devtools.CdpVersionFinder findNearestMatch
Get the web page
WARNING: Unable to find an exact match for CDP version 142, returning the closest version; found: 139; Please update to a Selenium version that supports CDP version 142
Login
*/