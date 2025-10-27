package school.redrover.old;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.Ignore;

@Ignore
public class VitalyMTest {
    @org.testng.annotations.Test
    public void test () throws InterruptedException {
        WebDriver driver = new ChromeDriver();

        driver.get("https://testsheepnz.github.io/BasicCalculator.html");

        WebElement firstTextFrame = driver.findElement(By.xpath("//*[@id='number1Field']"));
        WebElement secondTextFrame = driver.findElement(By.xpath("//*[@id=\"number2Field\"]"));
        WebElement buttonCalc = driver.findElement(By.xpath("//*[@id=\"calculateButton\"]"));
        WebElement finalTextFrame = driver.findElement(By.xpath("//*[@id=\"numberAnswerField\"]"));

        firstTextFrame.sendKeys("1");
        secondTextFrame.sendKeys("2");
        buttonCalc.click();

        Thread.sleep(1000);

        Assert.assertEquals(finalTextFrame.getAttribute("value"), "3");

        firstTextFrame.clear();
        secondTextFrame.clear();
        firstTextFrame.sendKeys("18");
        secondTextFrame.sendKeys("3");
        driver.findElement(By.xpath("//*[@id=\"selectOperationDropdown\"]/option[4]")).click();
        buttonCalc.click();

        Assert.assertEquals(finalTextFrame.getAttribute("value"), "6");

        driver.quit();
    }
}
