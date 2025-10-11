package school.redrover;

import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.testng.Assert;
import org.testng.annotations.Test;
import java.util.List;


public class NickyTest {

    @Test
    public void Test_book_choose_demoQA() {

        ChromeOptions options = new ChromeOptions();
        options.addArguments("--headless");
        options.addArguments("--window-size=1920,1080");

        WebDriver driver = new ChromeDriver(options);
        String baseURL =  "https://demoqa.com/";
        driver.get(baseURL);

        //WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

        // Try to get rid of the ad banner preventing click on a tile
        ((JavascriptExecutor) driver).executeScript(
                "var ad = document.getElementById('fixedban');" +
                        "if (ad) ad.remove();"
        );

        WebElement BookStoreApp_tile = driver.findElement(By.xpath("//div[contains(@class,'card-body')][.//h5[text()='Book Store Application']]"));

        //Wait for element to be clickable
        //WebElement BookStoreApp_tile_wait = wait.until(ExpectedConditions.visibilityOf(BookStoreApp_tile));

        //// Scroll the tile into view to allow for clicking
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", BookStoreApp_tile);

        BookStoreApp_tile.click();

        String booksURL = driver.getCurrentUrl();
        Assert.assertEquals(booksURL, "https://demoqa.com/books");

        WebElement searchbox = driver.findElement(By.id("searchBox"));
        searchbox.sendKeys("el");

        List<WebElement> search_results_grid = driver.findElements (By.cssSelector(".rt-tr-group .rt-tr:not(.-padRow)"));
        int count = search_results_grid.size();
        System.out.println(count);

        WebElement book_title = driver.findElement(By.id("see-book-Eloquent JavaScript, Second Edition"));
        book_title.click();
        String book_title_url = driver.getCurrentUrl();
        Assert.assertEquals(book_title_url, "https://demoqa.com/books?book=9781593275846");

        driver.quit();
    }

}
