package school.redrover;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.Test;

import java.time.Duration;

public class DenisGorbachevTest {
    @Test
    public void DenisTest1() {

        WebDriver driver = new ChromeDriver();
        driver.get("https://www.selenium.dev/selenium/web/web-form.html");
        driver.getTitle();
        driver.manage().timeouts().implicitlyWait(Duration.ofMillis(500));
        WebElement textBox = driver.findElement(By.xpath("/html/body/main/div/form/div/div[1]/label[3]/textarea"));
        WebElement submitButton = driver.findElement(By.xpath("/html/body/main/div/form/div/div[2]/button"));
        textBox.sendKeys("Selenium Test");
        submitButton.click();
        WebElement message = driver.findElement(By.id("message"));
        message.getText();
        driver.quit();
    }

    @Test
    public void DenisTest2() {
        WebDriver driver = new ChromeDriver();

        driver.get("https://habr.com/");
        driver.getTitle();
        driver.manage().timeouts().implicitlyWait(Duration.ofMillis(500));
        WebElement searchButton = driver.findElement(By.cssSelector("#app > div > header > div > div > div > a.tm-header-user-menu__item.tm-header-user-menu__search > svg"));
        searchButton.click();
        WebElement textBox = driver.findElement(By.xpath("//*[@id=\"app\"]/div/div[2]/main/div/div/div/div[1]/div/div[2]/form/div/input"));
        WebElement submitButton = driver.findElement(By.cssSelector("#app > div > div.tm-layout > main > div > div > div > div.tm-page__main.tm-page__main_has-sidebar > div > div.tm-page__top > form > div > div > span > svg"));
        textBox.sendKeys("Selenium Test");
        submitButton.click();
        WebElement message = driver.findElement(By.className("searched-item"));
        if (message.getText().contains("Selenium Test")){
            driver.quit();
        }
        else {
            System.out.println(message.getText());
            System.out.println("Failed");
            driver.quit();
        }

    }
}
