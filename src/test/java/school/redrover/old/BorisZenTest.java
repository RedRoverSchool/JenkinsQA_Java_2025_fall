package school.redrover.old;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.testng.Assert;
import org.testng.annotations.Ignore;
import org.testng.annotations.Test;
import java.util.HashMap;
import java.util.Map;

@Ignore
public class BorisZenTest {
    String userId = "mngr638292";
    String password = "zYqebUm";
    String url = "https://demo.guru99.com/";

    @Test
    public void testBankProject() throws InterruptedException {
        ChromeOptions options = new ChromeOptions();
        Map<String, Object> prefs = new HashMap<>();
        prefs.put("autofill.profile_enabled", false);
        prefs.put("credentials_enable_service", false);
        prefs.put("profile.password_manager_enabled", false);
        options.setExperimentalOption("prefs", prefs);

        WebDriver driver = new ChromeDriver(options);
        driver.get(url);
        Thread.sleep(1000);
        // Переходим в раздел Bank Project
        driver.findElement(By.xpath("//*[@id=\"navbar-brand-centered\"]/ul/li[5]/a")).click();
        Thread.sleep(500);
        // Login
        driver.findElement(By.xpath("/html/body/form/table/tbody/tr[1]/td[2]/input")).sendKeys(userId);
        driver.findElement(By.xpath("/html/body/form/table/tbody/tr[2]/td[2]/input")).sendKeys(password);
        driver.findElement(By.xpath("/html/body/form/table/tbody/tr[3]/td[2]/input[1]")).click();
        Thread.sleep(500);
        // Проверяем наличие картинок на странице
        Assert.assertFalse(driver.findElements(By.xpath("/html/body/table/tbody/tr/td/center/img[1]")).isEmpty());
        Assert.assertFalse(driver.findElements(By.xpath("/html/body/table/tbody/tr/td/center/img[2]")).isEmpty());
        Assert.assertFalse(driver.findElements(By.xpath("/html/body/table/tbody/tr/td/center/img[3]")).isEmpty());
        // Переходим в раздел New Customer
        driver.findElement(By.xpath("/html/body/div[3]/div/ul/li[2]/a")).click();
        Assert.assertEquals(driver.getCurrentUrl(), "https://demo.guru99.com/V1/html/addcustomerpage.php");
        // Заполнение полей
        // Name input
        String customerName = "MegaCustomer";
        driver.findElement(By.xpath("/html/body/table/tbody/tr/td/table/tbody/tr[4]/td[2]/input")).sendKeys(customerName);
        // Date of Birth Input
        String dateOfBirth = "07081986";
        driver.findElement(By.xpath("//*[@id=\"dob\"]")).sendKeys(dateOfBirth);
        // Address input
        String address = "Red Square, 1";
        WebElement txtAddress = driver.findElement(By.xpath("/html/body/table/tbody/tr/td/table/tbody/tr[7]/td[2]/textarea"));
        txtAddress.sendKeys(address);
        // City input
        String city = "Moscow";
        WebElement txtCity = driver.findElement(By.xpath("/html/body/table/tbody/tr/td/table/tbody/tr[8]/td[2]/input"));
        txtCity.click();
        txtAddress.click();
        WebElement addressWarning = driver.findElement(By.xpath("//*[@id=\"message4\"]"));
        Assert.assertEquals(addressWarning.getText(), "City Field must be not blank");
        txtCity.sendKeys(city);
        // State input
        String state = "Region";
        driver.findElement(By.xpath("/html/body/table/tbody/tr/td/table/tbody/tr[9]/td[2]/input")).sendKeys(state);
        // PIN input
        String pin = "123456";
        driver.findElement(By.xpath("/html/body/table/tbody/tr/td/table/tbody/tr[10]/td[2]/input")).sendKeys(pin);
        // Phone Number input
        String phone = "78945612323";
        driver.findElement(By.xpath("/html/body/table/tbody/tr/td/table/tbody/tr[11]/td[2]/input")).sendKeys(phone);
        // Email input
        String email = "ratuhra@gmail.com";
        driver.findElement(By.xpath("/html/body/table/tbody/tr/td/table/tbody/tr[12]/td[2]/input")).sendKeys(email);
        // Submit
        driver.findElement(By.xpath("/html/body/table/tbody/tr/td/table/tbody/tr[13]/td[2]/input[1]")).click();
        Thread.sleep(500);
        Assert.assertEquals(driver.getCurrentUrl(), "https://demo.guru99.com/V1/html/insrtCustomer.php");
        Assert.assertEquals(driver.findElement(By.xpath("//*[@id=\"main-message\"]/h1/span")).getText(),
                "Страница недоступна");
        driver.quit();
    }
}
