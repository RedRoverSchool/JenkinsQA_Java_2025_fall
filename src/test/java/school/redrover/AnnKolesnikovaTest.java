package school.redrover;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.time.Duration;


public class AnnKolesnikovaTest {
    @Test
    public void testDataOpeningL2() {
        //Тест переходит на сайт и сверяет дату открытия сервера по названию сервера

        WebDriver driver = new ChromeDriver();

        driver.get("https://www.la2era.com/ru");

        WebElement textBox = driver.findElement(By.xpath("/html/body/div/section/section/div/div/div[2]/div[2]/a[1]"));

        textBox.click();

        Assert.assertEquals(driver.findElement(By.cssSelector("body > div > section > section > div > div > div.home-about__stroke.flex-cc > div.tab-content > div.tab-pane.active > div.home-about__stroke.flex-ss > div:nth-child(9)")).getText(), "Дата открытия: 09.02.2018");

        driver.quit();
    }

    @Test
    public void testChangeLanguage() {
        //Тест переходит на сайт, меняет язык с Ru на En и сверяет язык написания главного заголовка.

        WebDriver driver = new ChromeDriver();

        driver.get("https://www.la2era.com/ru");

        WebElement submitButton = driver.findElement(By.cssSelector("body > div.wrapper > header > nav > div > div.navigation__langs > div"));

        submitButton.click();

        driver.manage().timeouts().implicitlyWait(Duration.ofMillis(5000));

        WebElement submitButton2 = driver.findElement(By.cssSelector("body > div > header > nav > div > div.navigation__langs > div > a:nth-child(3) > div.navigation__langs-item-name"));

        submitButton2.click();

        Assert.assertEquals(driver.findElement(By.cssSelector("body > div.wrapper > section > section > div > div > div.home-about__textbox > div")).getText(), "HIGH FIVE X3: LAUNCHING OCTOBER 3RD.\n" + "GET READY FOR ADVENTURE!");

        driver.quit();
    }

}
