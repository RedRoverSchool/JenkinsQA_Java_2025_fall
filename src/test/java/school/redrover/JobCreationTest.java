package school.redrover;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.Test;
import school.redrover.common.BaseTest;
import java.time.Duration;
import java.util.List;


public class JobCreationTest extends BaseTest {

    @Test
    public void testAvailableJobTypes() {

        getDriver().findElement(By.xpath("//a[@href='newJob']")).click();

        WebDriverWait wait = new WebDriverWait(getDriver(), Duration.ofSeconds(5));
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@class='jenkins-form-label']")));

        List<String> expectedItemType = List.of(
                "Freestyle project",
                "Pipeline",
                "Multi-configuration project",
                "Folder",
                "Multibranch Pipeline",
                "Organization Folder"
        );

        List<WebElement> itemType = getDriver().findElements(By.xpath("//div[@id='items']//label"));

        List<String> actualItemType = itemType
                .stream()
                .map(WebElement::getText)
                .toList();

        Assert.assertEquals(actualItemType, expectedItemType);
    }

    @Test
    public void testCreateClassicJob() {
        final String jobName = "Classic job";

        getDriver().findElement(By.xpath("//a[@href='newJob']")).click();

        getDriver().findElement(By.xpath("//input[@name='name']")).sendKeys(jobName);
        getDriver().findElement(By.className("hudson_model_FreeStyleProject")).click();
        getDriver().findElement(By.xpath("//button[@id='ok-button']")).click();

        WebDriverWait wait = new WebDriverWait(getDriver(), Duration.ofSeconds(3));
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//span[@class='jenkins-mobile-hide']")));

        getDriver().findElement(By.xpath("//textarea[@name='description']")).sendKeys("Test");
        getDriver().findElement(By.xpath("//button[@value='Save']")).click();

        wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("span.jenkins-mobile-hide"))).click();

        WebElement createdJobLink = getDriver().findElement(By.xpath("//a[@class='jenkins-table__link model-link inside']"));
        Assert.assertEquals(createdJobLink.getText(), jobName);
    }
}
