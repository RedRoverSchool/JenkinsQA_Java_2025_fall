package school.redrover;

import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.annotations.Test;
import school.redrover.common.BaseTest;

public class EnableDisableMultiConfigProjectTest extends BaseTest {

    @Test
    public void testDisableNewMultiConfigProject() {
        getDriver().findElement(By.xpath("//a[@href='/view/all/newJob']")).click();
        getDriver().findElement(By.id("name")).sendKeys("NewProject");
        getDriver().findElement(By.xpath("//li[@class='hudson_matrix_MatrixProject']")).click();
        getDriver().findElement(By.id("ok-button")).click();

        getDriver().findElement(By.xpath("//label[@for='enable-disable-project']")).click();
        getDriver().findElement(By.name("Submit")).click();

        String actualState = getDriver().findElement(By.id("enable-project")).getText().replace("Enable", "").trim();
        Assert.assertEquals(actualState, "This project is currently disabled");
    }
}
