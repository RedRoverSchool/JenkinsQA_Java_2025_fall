package school.redrover;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
//import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.Test;

public class NatPTest {

        //Test Case 1: Register User

        @Test
        public void registerUser() throws InterruptedException {
//        1. Launch browser
            WebDriver driver = new ChromeDriver();

//        2. Navigate to url 'http://automationexercise.com'
            driver.get("http://automationexercise.com");
//        driver.getTitle();
//        driver.manage().timeouts().implicitlyWait(Duration.ofMillis(1000));

//        3. Verify that home page is visible successfully
            driver.findElement(By.xpath("/html/body/div/div[2]/div[2]/div[2]/div[2]/button[1]/p")).click();

//        4. Click on 'Signup / Login' button
            driver.findElement(By.xpath("//*[@id=\"header\"]/div/div/div/div[2]/div/ul/li[4]/a")).click();

//        5. Verify 'New User Signup!' is visible

//        6. Enter name and email address
            driver.findElement(By.xpath("//*[@id=\"form\"]/div/div/div[3]/div/form/input[2]")).sendKeys("nata");
            driver.findElement(By.xpath("//*[@id=\"form\"]/div/div/div[3]/div/form/input[3]")).sendKeys("123*1@email.com");

//        7. Click 'Signup' button
            driver.findElement(By.xpath("//*[@id=\"form\"]/div/div/div[3]/div/form/button")).submit();

//        8. Verify that 'ENTER ACCOUNT INFORMATION' is visible

//        9. Fill details: Title, Name, Email, Password, Date of birth
            driver.findElement(By.id("id_gender2")).click();
            driver.findElement(By.id("password")).sendKeys("135!#%qetQET");
            driver.findElement(By.xpath("//*[@id=\"days\"]/option[2]")).click();
            driver.findElement(By.xpath("//*[@id=\"months\"]/option[2]")).click();
            driver.findElement(By.xpath("//*[@id=\"years\"]/option[17]")).click();

//        10. Select checkbox 'Sign up for our newsletter!'
            driver.findElement(By.id("newsletter")).click();

//        11. Select checkbox 'Receive special offers from our partners!'
            driver.findElement(By.id("optin")).click();

//        12. Fill details: First name, Last name, Company, Address, Address2, Country, State, City, Zipcode, Mobile Number
            driver.findElement(By.id("first_name")).sendKeys("nata");
            driver.findElement(By.id("last_name")).sendKeys("pro");
            driver.findElement(By.id("company")).sendKeys("home");
            driver.findElement(By.id("address1")).sendKeys("address1");
            driver.findElement(By.id("address2")).sendKeys("2/22");
            driver.findElement(By.xpath("//*[@id=\"country\"]/option[4]")).click();
            driver.findElement(By.id("state")).sendKeys("Victoria");
            driver.findElement(By.id("city")).sendKeys("Bendigo");
            driver.findElement(By.id("zipcode")).sendKeys("3550");
            driver.findElement(By.id("mobile_number")).sendKeys("123456");

//        13. Click 'Create Account button'
            driver.findElement(By.xpath("//*[@id=\"form\"]/div/div/div/div/form/button")).submit();

//        14. Verify that 'ACCOUNT CREATED!' is visible
            //WebElement message = driver.findElement(By.xpath("//*[@id=\"form\"]/div/div/div/h2/b"));
            //Assert.assertEquals(message.getText(), "ACCOUNT CREATED!");

//        15. Click 'Continue' button
            driver.findElement(By.xpath("//*[@id=\"form\"]/div/div/div/div/a")).click();

//        16. Verify that 'Logged in as username' is visible
            Thread.sleep(2000);

//        17. Click 'Delete Account' button
            driver.findElement(By.xpath("//*[@id=\"header\"]/div/div/div/div[2]/div/ul/li[5]/a")).click();

//        18. Verify that 'ACCOUNT DELETED!' is visible and click 'Continue' button
            Thread.sleep(3000);
            driver.findElement(By.xpath("//*[@id=\"form\"]/div/div/div/div/a")).click();

            Thread.sleep(1000);

            Assert.assertEquals(driver.getCurrentUrl(), "https://automationexercise.com/");

            driver.quit();
        }
}
