package school.redrover;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.time.Duration;

public class VladimirTarasovTest {

        public class OpenLoginPageTest {

            @Test
            public void openLoginPage(){
                WebDriver driver = new ChromeDriver();
                driver.get("https://demo.realworld.io/#/");

                WebElement loginButton = driver.findElement(By.xpath("/html/body/div/app-header/nav/div/ul[1]/li[2]/a"));
                WebElement title = driver.findElement(By.cssSelector("body > div > div > div > div.banner > div > h1"));

                String titleText = title.getText();
                Assert.assertTrue((titleText.contains("conduit")));

                loginButton.click();

                driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(1));

                WebElement loginPageTitle = driver.findElement(By.xpath("/html/body/div/div/div/div/div/div/h1"));
                String loginPageTitleText = loginPageTitle.getText();
                Assert.assertTrue(loginPageTitleText.contains("Sign in"));

                driver.quit();
            }
        }
    }
