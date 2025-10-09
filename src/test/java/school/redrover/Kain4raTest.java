package school.redrover;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.time.Duration;

public class Kain4raTest {
    @Test
    void testForm() {
        WebDriver driver = new ChromeDriver();
        driver.get("https://www.leagueofgraphs.com/ru/");
        WebElement input = driver.findElement(By.xpath("//form[@id=\"homepageForm\"]/input"));
        WebElement btn = driver.findElement(By.xpath("//form[@id=\"homepageForm\"]/button"));

        input.sendKeys("kain4ra");
        btn.click();

        WebElement target = driver.findElement(By.xpath("//*[@id=\"mainContent\"]/div/div[2]/div/table/tbody/tr[3]/td[1]/a"));
        target.click();

        WebElement rank = driver.findElement(By.xpath("//div[@id=\"mainContent\"]/div/div/div/div/div/div/div/div/div[2]/div"));
        Assert.assertEquals(rank.getText(), "Золото IV");
        driver.close();
    }

    @Test
    void testDropdownMenu() {
        WebDriver driver = new ChromeDriver();
        driver.get("https://www.leagueofgraphs.com/ru/");

        WebDriverWait w8 = new WebDriverWait(driver, Duration.ofSeconds(5));

        WebElement input = driver.findElement(By.xpath("//form[@id=\"homepageForm\"]/input"));
        input.sendKeys("kain4ra");

        w8.until(ExpectedConditions.visibilityOfElementLocated(By.id("autocomplete-area-homepage")));

        driver.findElement(By.xpath("//*[@id=\"autocomplete-area-homepage\"]/table/tbody/tr[3]/td/a")).click();
        WebElement games = driver.findElement(By.xpath("//div[@id=\"graphDD4\"]"));
        Assert.assertEquals(games.getText(), "146");
        driver.close();
    }

}