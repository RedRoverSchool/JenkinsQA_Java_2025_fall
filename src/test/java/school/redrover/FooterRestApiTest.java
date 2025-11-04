package school.redrover;

import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.annotations.Test;
import school.redrover.common.BaseTest;

public class FooterRestApiTest extends BaseTest {

    @Test
    public void testRestApiMainPage() {
        final String restApiText = "REST API";

        getDriver().findElement(By.className("rest-api")).click();

        String actualText = getDriver().findElement(By.cssSelector("h1")).getText();

        Assert.assertEquals(actualText, restApiText, "Текст заголовка не совпадает с ожидаемым");
    }
}
