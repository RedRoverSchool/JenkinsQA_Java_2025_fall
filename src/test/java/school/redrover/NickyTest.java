package school.redrover;

import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.Test;
import java.time.Duration;
import java.util.List;


public class NickyTest {

    @Test
    public void TestBookChooseDemoQA() {

        ChromeOptions options = new ChromeOptions();
        options.addArguments("--headless");
        options.addArguments("--window-size=1920,1080");

        WebDriver driver = new ChromeDriver(options);
        String baseURL =  "https://demoqa.com/";
        driver.get(baseURL);

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

        // Try to get rid of the ad banner preventing click on a tile
        ((JavascriptExecutor) driver).executeScript(
                "var ad = document.getElementById('fixedban'); if (ad) ad.remove();"
        );

        WebElement BookStoreAppTile = driver.findElement(By.xpath("//div[contains(@class,'card-body')][.//h5[text()='Book Store Application']]"));

        //Wait for element to be clickable
        wait.until(ExpectedConditions.visibilityOf(BookStoreAppTile));

        //// Scroll the tile into view to allow for clicking
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", BookStoreAppTile);

        BookStoreAppTile.click();

        String booksUrl = driver.getCurrentUrl();
        Assert.assertEquals(booksUrl, "https://demoqa.com/books");

        WebElement searchBox = driver.findElement(By.id("searchBox"));

        //Wait for element to be clickable
        wait.until(ExpectedConditions.visibilityOf(searchBox));

        searchBox.sendKeys("el");

        List <WebElement> searchResultsGrid = driver.findElements (By.cssSelector(".rt-tr-group .rt-tr:not(.-padRow)"));
        int count = searchResultsGrid.size();
        System.out.println(count);

        WebElement bookTitle = driver.findElement(By.id("see-book-Eloquent JavaScript, Second Edition"));
        bookTitle.click();

        String bookTitleUrl = driver.getCurrentUrl();
        Assert.assertEquals(bookTitleUrl, "https://demoqa.com/books?book=9781593275846");

        driver.quit();
    }

}
