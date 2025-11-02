package school.redrover;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.Test;
import school.redrover.common.BaseTest;

public class CreateNewItem4Test extends BaseTest {

    @Test
    public void testNewItemPageAccessibility() throws InterruptedException {

        getDriver().findElement(By.linkText("New Item")).click();

        Thread.sleep(2000);

        WebElement label = getDriver().findElement(By.xpath("//label[text()='Enter an item name']"));
        WebElement inputField = getDriver().findElement(By.id("name"));

        Assert.assertTrue(label.isDisplayed(), "Label 'Enter an item name' should be displayed");
        Assert.assertTrue(inputField.isDisplayed(), "Input field 'name' should be visible");
    }
}


/*
Steps:

1.Click on the “New Item” button in the left navigation panel.
2.Verify that the “Enter an item name” input field is displayed.
3.Verify that the list of available item types is displayed:
    Freestyle Project
    Pipeline
    Multi-configuration Project
    Folder
    Multibranch Pipeline
    Organization Folder
Expected Result:
After clicking the “New Item” button, the user is redirected to the New Job page (URL contains /view/all/newJob).
The “Enter an item name” input field is visible on the page.
The list of available item types is displayed correctly.

Acceptance Criteria:

New Item Page Accessibility
The "New Item" page should be accessible from the Jenkins Dashboard.
The page should have an input field "Enter an item name".
A list of available item types (Freestyle Project, Pipeline, Multi-configuration Project, Folder, Multibranch Pipeline, Organization Folder) should be displayed.
 */