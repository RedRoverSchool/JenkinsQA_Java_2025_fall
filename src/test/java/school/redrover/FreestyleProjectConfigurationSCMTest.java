package school.redrover;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.Test;
import school.redrover.common.BaseTest;

import java.time.Duration;

public class FreestyleProjectConfigurationSCMTest extends BaseTest {

    private WebDriverWait wait;
    private static final String SCM_TITLE_EXPECTED = "Source Code Management";
    final String freestyleProjectName = "FreestyleProject2025";

    private void createFreestyleProject(String freestyleProjectName) {
        wait = new WebDriverWait(getDriver(), Duration.ofSeconds(5));
        wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("a[href='/view/all/newJob']"))).click();
        getDriver().findElement(By.id("name")).sendKeys(freestyleProjectName);
        getDriver().findElement(By.xpath("//span[text()='Freestyle project']")).click();
        wait.until(ExpectedConditions.elementToBeClickable(By.id("ok-button"))).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("general")));
    }

    @Test
    public void testAccessSCMInNewJob() {
        // 02.003.01
        createFreestyleProject(freestyleProjectName);

        WebElement scmTitle = getDriver().findElement(By.xpath("//div[@id='source-code-management']"));
        wait.until(ExpectedConditions.visibilityOf(scmTitle));

        Assert.assertEquals(scmTitle.getText(), SCM_TITLE_EXPECTED);
    }

    @Test
    public void testSCMSectionElements() {
        // 02.003.05
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
}
