package school.redrover;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.time.Duration;

public class SergeiButorinTest {

    @Test
    public static void testHeadhunter() {
        WebDriver hhdriver = new ChromeDriver();
        hhdriver.get("https://www.hh.ru/vacancy/122718353");
        hhdriver.manage().timeouts().implicitlyWait(Duration.ofMillis(500));

        Assert.assertEquals(hhdriver.findElement(By.cssSelector("h1[data-qa='vacancy-title']")).getText(), "UX-Исследователь (Junior)");

        Assert.assertEquals(hhdriver.findElement(By.cssSelector("a[data-qa='vacancy-company-name']")).getText(),
                "Филиал ФКУ Налог-Сервис ФНС России по ЦОД в г.Москве");

        hhdriver.quit();
    }
}