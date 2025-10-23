package school.redrover.old;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.Ignore;
import org.testng.annotations.Test;

import java.time.Duration;

@Ignore
public class FoxHelperTest {

    @Test
    void testKain4raInput() {
        WebDriver driver = new ChromeDriver();
        driver.get("https://www.leagueofgraphs.com/ru/");
        driver.manage().window().maximize();

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
    void testKain4raDropdownMenu() {
        WebDriver driver = new ChromeDriver();
        driver.get("https://www.leagueofgraphs.com/ru/");

        WebDriverWait w8 = new WebDriverWait(driver, Duration.ofSeconds(5));

        WebElement input = driver.findElement(By.xpath("//form[@id='homepageForm']/input"));
        input.sendKeys("kain4ra");

        w8.until(ExpectedConditions.visibilityOfElementLocated(By.id("autocomplete-area-homepage")));

        driver.findElement(By.xpath("//*[@id='autocomplete-area-homepage']/table/tbody/tr[3]/td/a")).click();
        WebElement games = driver.findElement(By.xpath("//div[@id='graphDD4']"));
        Assert.assertEquals(games.getText(), "146");
        driver.close();
    }

    @Test
    public void TestLogInTatiana() {
        WebDriver driver = new ChromeDriver();
        driver.manage().window().maximize();

        driver.get("https://automationexercise.com/");

        WebElement login = driver.findElement(By.xpath("//*[@id=\"header\"]/div/div/div/div[2]/div/ul/li[4]/a"));
        login.click();

        WebElement nameLog = driver.findElement(By.xpath("//*[@id=\"form\"]/div/div/div[3]/div/form/input[2]"));
        nameLog.sendKeys("Tatiana");

        WebElement emailLog = driver.findElement(By.xpath("//*[@id=\"form\"]/div/div/div[3]/div/form/input[3]"));
        emailLog.sendKeys("Tanya375444@gmail.com");

        WebElement buttonSingUp = driver.findElement(By.xpath("//*[@id=\"form\"]/div/div/div[3]/div/form/button"));
        buttonSingUp.click();

        WebElement text = driver.findElement((By.xpath("//*[@id=\"form\"]/div/div/div[3]/div/form/p")));
        String x = "Email Address already exist!";
        if (text.getText().equals(x)) {

            WebElement email = driver.findElement(By.xpath("//*[@id=\"form\"]/div/div/div[1]/div/form/input[2]"));
            email.sendKeys("Tanya375444@gmail.com");
            WebElement password2 = driver.findElement(By.xpath("//*[@id=\"form\"]/div/div/div[1]/div/form/input[3]"));
            password2.sendKeys("Tanya375444");
            WebElement buttonLog = driver.findElement(By.xpath("//*[@id=\"form\"]/div/div/div[1]/div/form/button"));
            buttonLog.click();

            WebElement message2 = driver.findElement(By.xpath("//*[@id=\"header\"]/div/div/div/div[2]/div/ul/li[10]/a"));
            Assert.assertEquals(message2.getText(), "Logged in as Tatiana");
            driver.quit();
}
    }
}

