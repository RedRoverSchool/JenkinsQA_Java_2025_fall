package school.redrover;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;
import org.testng.annotations.Test;
import school.redrover.common.BaseTest;
import school.redrover.page.HomePage;


public class FreestyleProjectConfigurationSCMTest extends BaseTest {

    private static final String SCM_TITLE_EXPECTED = "Source Code Management";
    private static final String FREESTYLE_PROJECT_NAME = "FreestyleProject2025";

    private void createFreestyleProject(String freestyleProjectName) {
        getWait5().until(ExpectedConditions.elementToBeClickable(By.cssSelector("a[href='/view/all/newJob']"))).click();
        getDriver().findElement(By.id("name")).sendKeys(freestyleProjectName);
        getDriver().findElement(By.xpath("//span[text()='Freestyle project']")).click();
        getWait5().until(ExpectedConditions.elementToBeClickable(By.id("ok-button"))).click();
        getWait5().until(ExpectedConditions.visibilityOfElementLocated(By.id("general")));
    }

    @Test
    public void testAccessSCMInNewJob() {
        WebElement scmTitle = new HomePage(getDriver())
                .clickCreateJob()
                .sendName(FREESTYLE_PROJECT_NAME)
                .selectFreestyleProjectAndSubmit()
                .verifySCMTitleIsVisible();

        Assert.assertEquals(scmTitle.getText(), SCM_TITLE_EXPECTED);
    }

    @Test
    public void testSCMSectionElements() {
        createFreestyleProject(FREESTYLE_PROJECT_NAME);

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
        createFreestyleProject(FREESTYLE_PROJECT_NAME);

        getWait5().until(ExpectedConditions.elementToBeClickable(By.xpath("//span[@class='jenkins-mobile-hide']"))).click();

        getDriver().findElement(By.xpath("//a[@href='job/%s/']".formatted(FREESTYLE_PROJECT_NAME))).click();
        getDriver().findElement(By.xpath("//a[@href='/job/%s/configure']".formatted(FREESTYLE_PROJECT_NAME))).click();
        WebElement scmTitle = getDriver().findElement(By.xpath("//div[@id='source-code-management']"));

        Assert.assertEquals(scmTitle.getText(), SCM_TITLE_EXPECTED);
    }

    @Test
    public void testNavigationToSCMViaMenu() {
        createFreestyleProject(FREESTYLE_PROJECT_NAME);

        getDriver().findElement(By.xpath("//button[@data-section-id='source-code-management']")).click();
        WebElement scmTitle = getDriver().findElement(By.xpath("//div[@id='source-code-management']"));

        Assert.assertEquals(scmTitle.getText(), SCM_TITLE_EXPECTED);
    }

    @Test
    public void testNavigationToSCMByScrollingDown() {
        createFreestyleProject(FREESTYLE_PROJECT_NAME);

        WebElement scmTitle = getDriver().findElement(By.xpath("//div[@id='source-code-management']"));
        ((JavascriptExecutor) getDriver()).executeScript("arguments[0].scrollIntoView(true);", scmTitle);

        Assert.assertEquals(scmTitle.getText(), SCM_TITLE_EXPECTED);
    }
}
