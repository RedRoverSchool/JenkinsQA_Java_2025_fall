package school.redrover;

import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.annotations.Test;
import school.redrover.common.BaseTest;
import school.redrover.page.HomePage;

public class MultibranchPipeline1Test extends BaseTest {

    private static final String MULTIBRANCH_PIPELINE_NAME = "Multibranch_Pipeline";
    private static final String MULTIBRANCH_PIPELINE_DISPLAY_NAME = "Multibranch_Pipeline_Display";

    @Test
    public void testCreateClickCreateJob() {

        String name = new HomePage(getDriver())
                .clickCreateJob()
                .sendName(MULTIBRANCH_PIPELINE_NAME)
                .selectMultibranchPipelineAndSubmit()
                .clickSaveButton()
                .gotoHomePage()
                .findItem(MULTIBRANCH_PIPELINE_NAME)
                .getText();

        Assert.assertEquals(name, MULTIBRANCH_PIPELINE_NAME);
    }

    @Test
    public void testCreateClickNewItem() {

        String name = new HomePage(getDriver())
                .clickNewItemOnLeftMenu()
                .sendName(MULTIBRANCH_PIPELINE_NAME)
                .selectMultibranchPipelineAndSubmit()
                .sendDisplayName(MULTIBRANCH_PIPELINE_DISPLAY_NAME)
                .clickSaveButton()
                .gotoHomePage()
                .findItem(MULTIBRANCH_PIPELINE_NAME)
                .getText();

        Assert.assertEquals(name, MULTIBRANCH_PIPELINE_DISPLAY_NAME);
    }


    @Test
    public void testChangeDescription() {

        getDriver().findElement(By.cssSelector("a[href='/view/all/newJob']")).click();
        getDriver().findElement(By.id("name")).sendKeys("New Multibranch Pipeline");
        getDriver().findElement(By.xpath("//span[text()='Multibranch Pipeline']")).click();
        getDriver().findElement(By.id("ok-button")).click();
        getDriver().findElement(By.xpath("//textarea")).sendKeys("New test pipeline");
        getDriver().findElement(By.name("Submit")).click();
        getDriver().findElement(By.cssSelector("a[href='./configure']")).click();
        getDriver().findElement(By.xpath("//textarea")).clear();
        getDriver().findElement(By.xpath("//textarea")).sendKeys("Change description - New test pipeline 2");
        getDriver().findElement(By.name("Submit")).click();

        Assert.assertEquals(getDriver().findElement(By.id("view-message")).getText(), "Change description - New test pipeline 2");

    }
}
