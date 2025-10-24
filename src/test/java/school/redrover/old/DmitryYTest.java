package school.redrover.old;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.Ignore;
import org.testng.annotations.Test;
import java.time.Duration;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.support.ui.ExpectedConditions;

@Ignore
public class DmitryYTest {
    @Test
    public void jurisdictionsTest() {

        WebDriver driver = new ChromeDriver();

        try {
            driver.get("https://endorphina.com/");
            driver.manage().window().maximize(); // добавить максимизацию окна

            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

            // Принять куки с ожиданием
            WebElement acceptButton = wait.until(ExpectedConditions.elementToBeClickable(By.className("btn-accept")));
            acceptButton.click();

            // Принять возраст с ожиданием
            WebElement ageAcceptButton = wait.until(ExpectedConditions.elementToBeClickable(
                    By.xpath("/html/body/div[1]/div[1]/div/div/button[1]")));
            ageAcceptButton.click();

            // Показать больше юрисдикций
            WebElement showMoreJurisdictionsButton = wait.until(ExpectedConditions.elementToBeClickable(
                    By.xpath("/html/body/main/section[6]/div/div[2]/button")));
            ((JavascriptExecutor) driver).executeScript("arguments[0].click();", showMoreJurisdictionsButton);

            // Выбрать Беларусь
            WebElement belarusJurisdiction = wait.until(ExpectedConditions.elementToBeClickable(
                    By.xpath("/html/body/main/section[6]/div/ul/li[30]/a")));
            belarusJurisdiction.click();

            // Проверить, что перешли на нужную страницу (опционально)
            wait.until(ExpectedConditions.urlContains("jurisdictions"));

        } finally {
            driver.quit();
        }
    }
}