package school.redrover;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;
import org.testng.annotations.Test;
import school.redrover.common.BaseTest;


public class FreestyleProjectConfigurationSCMTest extends BaseTest {

    final String scmTitleExpected = "Source Code Management";
    final String freestyleProjectName = "FreestyleProject2025";

    private void createFreestyleProject(String freestyleProjectName) {
        getWait5().until(ExpectedConditions.elementToBeClickable(By.cssSelector("a[href='/view/all/newJob']"))).click();
        getDriver().findElement(By.id("name")).sendKeys(freestyleProjectName);
        getDriver().findElement(By.xpath("//span[text()='Freestyle project']")).click();
        getWait5().until(ExpectedConditions.elementToBeClickable(By.id("ok-button"))).click();
        getWait5().until(ExpectedConditions.visibilityOfElementLocated(By.id("general")));
    }

    @Test
    public void testAccessSCMInNewJob() {
        createFreestyleProject(freestyleProjectName);

        WebElement scmTitle = getDriver().findElement(By.xpath("//div[@id='source-code-management']"));
        getWait5().until(ExpectedConditions.visibilityOf(scmTitle));

        Assert.assertEquals(scmTitle.getText(), scmTitleExpected);
    }

    @Test
    public void testSCMSectionElements() {
        createFreestyleProject(freestyleProjectName);

        WebElement scmDescription = getDriver().findElement(By.xpath("//div[normalize-space()='Connect and manage " +
                "your code repository to automatically pull the latest code for your builds.']"));

        WebElement selectedInput = getDriver().findElement(By.xpath("//input[@name='scm' and @checked='true']"));
        String inputId = selectedInput.getAttribute("id");
        WebElement linkedLabel = getDriver().findElement(By.xpath("//label[@for='%s']".formatted(inputId)));
        String actualLabelText = linkedLabel.getText();

        WebElement gitLabel = getDriver().findElement(By.xpath("//label[normalize-space(text())='Git']"));
        WebElement gitHelpIcon = getDriver().findElement(
                By.xpath("//a[@title='Help for feature: Git']"));
        String tooltipText = gitHelpIcon.getAttribute("tooltip");

        Assert.assertTrue(scmDescription.isDisplayed(), "SCM Description is not displayed or the description text doesn't match");
        Assert.assertEquals(actualLabelText, "None", "Radio button 'None' should be selected by default");
        Assert.assertTrue(gitLabel.isDisplayed(), "Radio button 'Git' should be displayed");
        Assert.assertEquals(tooltipText, "Help for feature: Git", "Tooltip text should match expected value");
    }

    @Test
    public void testAccessSCMInExistingJob() {
        createFreestyleProject(freestyleProjectName);

        getWait5().until(ExpectedConditions.elementToBeClickable(By.xpath("//span[@class='jenkins-mobile-hide']"))).click();

        getDriver().findElement(By.xpath("//a[@href='job/%s/']".formatted(freestyleProjectName))).click();
        getDriver().findElement(By.xpath("//a[@href='/job/%s/configure']".formatted(freestyleProjectName))).click();
        WebElement scmTitle = getDriver().findElement(By.xpath("//div[@id='source-code-management']"));

        Assert.assertEquals(scmTitle.getText(), scmTitleExpected);
    }

    @Test
    public void testNavigationToSCMViaMenu() {
        createFreestyleProject(freestyleProjectName);

        getDriver().findElement(By.xpath("//button[@data-section-id='source-code-management']")).click();
        WebElement scmTitle = getDriver().findElement(By.xpath("//div[@id='source-code-management']"));

        Assert.assertEquals(scmTitle.getText(), scmTitleExpected);
    }

    @Test
    public void testNavigationToSCMByScrollingDown() {
        createFreestyleProject(freestyleProjectName);

        WebElement scmTitle = getDriver().findElement(By.xpath("//div[@id='source-code-management']"));
        ((JavascriptExecutor) getDriver()).executeScript("arguments[0].scrollIntoView(true);", scmTitle);

        Assert.assertEquals(scmTitle.getText(), scmTitleExpected);
    }
}
