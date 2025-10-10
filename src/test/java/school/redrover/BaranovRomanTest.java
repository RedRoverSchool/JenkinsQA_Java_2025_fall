package school.redrover;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.time.Duration;

public class BaranovRomanTest {
    @Test
    public void testSelenium() throws InterruptedException {
        WebDriver driver = new ChromeDriver();
        driver.get("https://animevost.org/");
        driver.manage().timeouts().implicitlyWait(Duration.ofMillis(5000));
        WebElement searchInsern = driver.findElement(By.name("story"));
        searchInsern.sendKeys("Моя жизнь это аниме");
        WebElement buttonSearch = driver.findElement(By.className("searchButton"));
        buttonSearch.click();
        Thread.sleep(1000);

        WebElement searchAnime = driver.findElement(By.xpath("//a[contains(text(), 'Vigilante: Boku no Hero Academia')]"));

        Assert.assertEquals(searchAnime.getText(), "Моя геройская академия: Вне закона / Vigilante: Boku no Hero Academia Illegals [1-13 из 13]");
        driver.quit();
    }

    @Test
    public void testCoinMarketCap() throws InterruptedException {
        WebDriver driver = new ChromeDriver();

        driver.get("https://coinmarketcap.com/");

        Thread.sleep(1000);

        driver.findElement(By.className("Search_mobile-icon-wrapper__95svj")).click();

        driver.findElement(By.cssSelector("input[placeholder='Search coin, NFT, contract address, exchange, or post']")).sendKeys("BNB");

        Thread.sleep(3000);
        driver.findElement(By.xpath("//div[contains(@class,'SearchCryptoRow_coin-name')]/span[1]")).click();

        Thread.sleep(3000);
        WebElement foundETHPrice = driver.findElement(By.cssSelector("span[data-test='text-cdp-price-display']"));
        System.out.println(foundETHPrice.getText());
        driver.quit();

    }
}
