package school.redrover;

import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.annotations.Test;
import school.redrover.common.BaseTest;

public class FooterRestApiTest extends BaseTest {

    private String checkRestApiHeader() {
        getDriver().findElement(By.className("rest-api")).click();

        return getDriver().findElement(By.cssSelector("h1")).getText();
    }

    @Test
    public void testRestApiMainPage() {
        Assert.assertEquals(checkRestApiHeader(), "REST API", "Текст заголовка не совпадает с ожидаемым");
    }

    @Test
    public void testRestApiUserAdminPage() {
        getDriver().findElement(By.id("root-action-UserAction")).click();

        Assert.assertEquals(checkRestApiHeader(), "REST API", "Текст заголовка не совпадает с ожидаемым");
    }

    @Test
    public void testRestApiBuildsPage() {
        getDriver().findElement(By.xpath("//a[@href='/view/all/builds']")).click();

        Assert.assertEquals(checkRestApiHeader(), "REST API", "Текст заголовка не совпадает с ожидаемым");
    }

    @Test
    public void testRestApiComputerPage() {
        getDriver().findElement(By.xpath("//a[@href='computer/new']")).click();

        Assert.assertEquals(checkRestApiHeader(), "REST API", "Текст заголовка не совпадает с ожидаемым");
    }
}
