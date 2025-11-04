package school.redrover;

import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.annotations.Test;
import school.redrover.common.BaseTest;

public class FooterRestApiTest extends BaseTest {

    private void checkRestApiHeader() {
        final String restApiText = "REST API";
        getDriver().findElement(By.className("rest-api")).click();

        String actualText = getDriver().findElement(By.cssSelector("h1")).getText();

        Assert.assertEquals(actualText, restApiText, "Текст заголовка не совпадает с ожидаемым");
    }


    @Test
    public void testRestApiMainPage() {
        checkRestApiHeader();
    }

    @Test
    public void testRestApiUserAdminPage() {
        getDriver().findElement(By.id("root-action-UserAction")).click();

        checkRestApiHeader();
    }

    @Test
    public void testRestApiBuildsPage() {
        getDriver().findElement(By.xpath("//a[@href='/view/all/builds']")).click();

        checkRestApiHeader();
    }

    @Test
    public void testRestApiComputerPage() {
        getDriver().findElement(By.xpath("//a[@href='computer/new']")).click();

        checkRestApiHeader();
    }
}
