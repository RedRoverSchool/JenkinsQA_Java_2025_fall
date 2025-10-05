package school.redrover;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.time.Duration;

public class StasATest {

    @Test
    public void testPutDepositBankAccount() {

        WebDriver driver = new ChromeDriver();

        driver.get("https://www.globalsqa.com/angularJs-protractor/BankingProject/#/login");
        driver.manage().timeouts().implicitlyWait(Duration.ofMillis(900));

        WebElement customerLoginButton = driver
                .findElement(By.xpath("//button[@class='btn btn-primary btn-lg' and contains(text(), 'Customer Login')]"));
        customerLoginButton.click();

        WebElement userSelect = driver
                .findElement(By.name("userSelect"));

        Select userDropdown = new Select(userSelect);
        userDropdown.selectByValue("1");

        WebElement loginButton = driver
                .findElement(By.xpath("//button[@class='btn btn-default']"));
        loginButton.click();

        WebElement openDepositButton = driver
                .findElement(By.xpath("//button[@ng-click='deposit()']"));
        openDepositButton.click();

        WebElement makeDepositInput = driver
                .findElement(By.xpath("//input[@class='form-control ng-pristine ng-untouched ng-invalid ng-invalid-required']"));
        makeDepositInput.sendKeys("1000");

        WebElement depositButton = driver
                .findElement(By.xpath("//button[@class='btn btn-default']"));
        depositButton.click();

        WebElement depositMessage = driver
                .findElement(By.xpath("//span[@class='error ng-binding']"));

        Assert.assertEquals(depositMessage.getText(), "Deposit Successful", "Ошибка в переводе на депозит");

        driver.quit();
    }
}
