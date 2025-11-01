package school.redrover;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.Test;
import school.redrover.common.BaseTest;

import java.util.List;

public class PipelineTest extends BaseTest {

    private static final String PIPELINE_NAME = "PipelineName";

    /**
     * Остается на Pipeline Configuration page
     * @param name имя создаваемого pipeline; не должно быть пустым или null
     */
    private void createPipeline(String name) {

        getDriver().findElement(By.xpath("//a[@href='/view/all/newJob']")).click();

        getDriver().findElement(By.id("name")).sendKeys(name);
        getDriver().findElement(By.className("org_jenkinsci_plugins_workflow_job_WorkflowJob")).click();
        getDriver().findElement(By.id("ok-button")).click();
        getDriver().findElement(By.name("Submit")).click();
    }

    @Test
    public void testCreatePipeline() throws InterruptedException {
        getDriver().findElement(By.cssSelector(".task:nth-child(1) a")).click();
        getDriver().findElement(By.cssSelector("#name")).sendKeys(PIPELINE_NAME);
        getDriver().findElement(By.cssSelector("div:first-child > ul > li:nth-child(2)")).click();
        getDriver().findElement(By.id("ok-button")).click();
        getDriver().findElement(By.xpath("//button[@name='Submit']")).click();

        Thread.sleep(2000);
        getDriver().findElement(By.xpath("//a[@href='/']/img")).click();

        Assert.assertEquals(getDriver().findElement(By.xpath("//a[@href='job/" + PIPELINE_NAME + "/']")).getText(),
                PIPELINE_NAME);
    }

    @Test
    public void testDeletePipeline() throws InterruptedException {
        getDriver().findElement(By.cssSelector(".task:nth-child(1) a")).click();
        getDriver().findElement(By.cssSelector("#name")).sendKeys(PIPELINE_NAME);
        getDriver().findElement(By.cssSelector("div:first-child > ul > li:nth-child(2)")).click();
        getDriver().findElement(By.id("ok-button")).click();
        getDriver().findElement(By.xpath("//button[@name='Submit']")).click();

        Thread.sleep(2000);
        getDriver().findElement(By.xpath("//a[@href='/']/img")).click();

        List<WebElement> countPosition = getDriver().findElements(By.cssSelector("#projectstatus > tbody > tr"));

        getDriver().findElement(By.xpath("//a[@href='job/" + PIPELINE_NAME + "/']")).click();
        getDriver().findElement(By.cssSelector(".task:nth-child(6)")).click();
        getDriver().findElement(By.xpath("//button[@data-id='ok']")).click();

        Assert.assertEquals(countPosition.size() - 1, 0);
    }

    @Test
    public void testSuccessfulBuildPipeline() throws InterruptedException {

        createPipeline(PIPELINE_NAME);

        getDriver().findElement(By.xpath("//a[@data-build-success='Build scheduled']")).click();

        Thread.sleep(3000);
        getDriver().findElement(By.id("jenkins-build-history")).click();
        getDriver().findElement(By.xpath("//a[substring-before(@href, 'console')]")).click();

        String consoleOutputText = getDriver().findElement(By.id("out")).getText();

        Assert.assertTrue(consoleOutputText.contains("Finished: SUCCESS"), "Build output should contain 'Finished: SUCCESS'");
    }

}
