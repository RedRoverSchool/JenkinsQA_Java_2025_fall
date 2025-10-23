package school.redrover.old;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.Ignore;
import org.testng.annotations.Test;

@Ignore
public class OlgaBuTest {

    @Test
    public void testFigureSkatingNewsPageSmallWindow() {
        WebDriver driver = new ChromeDriver();

        driver.get("https://www.sports.ru/");
        driver.findElement(By.cssSelector("#popup-anchor-burgerMobile > button > span")).click();
        driver.findElement(By.xpath("//div[@Class='navigation-item']/a[@href='/figure-skating/']")).click();

        Assert.assertEquals(driver.findElement(By.xpath("//a[@href='/figure-skating/topnews/']")).getText(),
                "ГЛАВНЫЕ НОВОСТИ ФИГУРНОГО КАТАНИЯ");

        driver.quit();
    }

    @Test
    public void testFigureSkatingNewsPageFullWindow() {
        WebDriver driver = new ChromeDriver();

        driver.get("https://www.sports.ru/");
        driver.manage().window().maximize();
        driver.findElement(By.xpath("//nav[@id='navigation-navbar']//a[@href='/figure-skating/']")).click();

        Assert.assertEquals(driver.findElement(By.xpath("//a[@href='/figure-skating/topnews/']")).getText(),
                "ГЛАВНЫЕ НОВОСТИ ФИГУРНОГО КАТАНИЯ");

        driver.quit();
    }

    @Test
    public void testCoursePage() {
        WebDriver driver = new ChromeDriver();

        driver.get("https://online.berklee.edu");
        driver.manage().window().maximize();

        driver.findElement(By.xpath("//a[@class='btn-primary-outline'][@href='/courses']")).click();
        driver.findElement(By.xpath("//a[@href='/courses/ableton-live-fundamentals']")).click();

        Assert.assertEquals(driver.findElement(By.xpath("//div[@class='course-head']/h1")).getText(),
                "Ableton Live Fundamentals");

        driver.quit();
    }
}
