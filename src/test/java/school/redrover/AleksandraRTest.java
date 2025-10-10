package school.redrover;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.testng.Assert;
import org.testng.annotations.Test;

public class AleksandraRTest {

    @Test
    public void getConcerts() throws InterruptedException {
        WebDriver driver = new ChromeDriver();
        driver.get("https://www.ticketmaster.com/discover/washington-dc");

        WebElement concertsButton = driver.findElement(By.xpath("//a[@data-testid='Concerts']"));

        concertsButton.click();
        Thread.sleep(1200);


        WebElement seeAllConcertsButton = driver.findElement(By.xpath("//*[@id=\":r1:-Concerts\"]/div[1]/div/a"));
        seeAllConcertsButton.click();

        WebElement concertTickets = driver.findElement(By.xpath("//*[@id=\"main-content\"]/div[1]/div/h1"));
        Assert.assertEquals(concertTickets.getText(), "CONCERT TICKETS");

        driver.quit();
    }

    @Test
    public void filterWork() throws InterruptedException {
        WebDriver driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.get("https://www.ticketmaster.com/discover/washington-dc");

        WebElement concertsButton = driver.findElement(By.xpath("//a[@data-testid='Concerts']"));
        concertsButton.click();
        Thread.sleep(1200);

        WebElement cookies = driver.findElement(By.xpath("//*[@id=\"onetrust-accept-btn-handler\"]"));
        cookies.click();
        Thread.sleep(1000);

        WebElement seeAllConcertsButton = driver.findElement(By.xpath("//*[@id=\":r1:-Concerts\"]/div[1]/div/a"));
        seeAllConcertsButton.click();

        WebElement dropdownButton = driver.findElement(By.xpath("//button[@data-testid='category-filter']"));
        dropdownButton.click();
        Thread.sleep(2000);


        WebElement bluesGenreButton = driver.findElement(By.xpath("//*[@id=\":Radam:\"]/li[4]/a"));

        Actions actions = new Actions(driver);
        actions.scrollToElement(bluesGenreButton).perform();
        bluesGenreButton.click();
        Thread.sleep(2000);

        WebElement bluesGenre = driver.findElement(By.xpath("//*[@id=\"main-content\"]/div[1]/div/h1"));
        Assert.assertEquals(bluesGenre.getText(), "BLUES");

        driver.quit();
    }
}
