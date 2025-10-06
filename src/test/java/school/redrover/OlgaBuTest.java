package school.redrover;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.Test;

public class OlgaBuTest {
    @Test
    public void testFigureSkatingNewsPage() {
        WebDriver driver = new ChromeDriver();

        driver.get("https://www.sports.ru/");
        driver.findElement(By.cssSelector("#popup-anchor-burgerMobile > button > span")).click();
        driver.findElement(By.xpath("//ul[@class='navigation-item-list']/li[6]")).click();

        Assert.assertEquals(driver.findElement(By.xpath("//div[@data-control='Common.LeftColumn']/section[1]/h3")).getText(),
                "ГЛАВНЫЕ НОВОСТИ ФИГУРНОГО КАТАНИЯ");

        driver.quit();
    }
}
