package school.redrover;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.time.Duration;

public class GroupTheBugStopsHereTest {
    private WebDriverWait wait;

    @Test
    public void testAddReview() {

        WebDriver driver = new ChromeDriver();

        driver.get("https://www.automationexercise.com/");

        WebElement submitProductsButton = driver.findElement(By.cssSelector("a[href='/products']"));
        submitProductsButton.click();

        String pageProductsTitle = driver.getTitle();
        driver.findElement(By.cssSelector(".title.text-center"));
        Assert.assertEquals(pageProductsTitle, "Automation Exercise - All Products", "Ошибка! Заголовок страницы не совпал с ожидаемым.");

        WebElement viewProductButton = driver.findElement(By.cssSelector("a[href='/product_details/1']"));
        viewProductButton.click();

        WebElement messageReview = driver.findElement(By.cssSelector("a[href='#reviews']"));
        Assert.assertTrue(messageReview.isDisplayed(), "The element should be visible on the page.");

        WebElement nameTextBox = driver.findElement(By.cssSelector("#name"));
        nameTextBox.sendKeys("username");

        WebElement emailTextBox = driver.findElement(By.cssSelector("#email"));
        emailTextBox.sendKeys("email@test.ru");

        WebElement reviewTextBox = driver.findElement(By.cssSelector("#review"));
        reviewTextBox.sendKeys("test review text");

        WebElement reviewSubmitButton = driver.findElement(By.cssSelector("#button-review"));
        reviewSubmitButton.click();

        WebElement element = driver.findElement(By.cssSelector("#review-section .alert-success.alert"));
        Assert.assertTrue(element.isDisplayed(), "The element should be visible on the page.");

        driver.quit();
    }
    @Test
    public void testAutomationExercise() {
        WebDriver driver = new ChromeDriver();

        driver.get("https://automationexercise.com/test_cases");
        driver.findElement(By.xpath("/html/body/div/div[2]/div[2]/div[2]/div[2]/button[1]/p")).click();
        driver.findElement(By.xpath("//*[@id=\"header\"]/div/div/div/div[2]/div/ul/li[4]/a")).click();
        driver.findElement(By.xpath("//*[@id=\"form\"]/div/div/div[1]/div/form/input[2]")).sendKeys("ememdems@hotmail.com");
        driver.findElement(By.xpath("//*[@id=\"form\"]/div/div/div[1]/div/form/input[3]")).sendKeys("8XbTY@zG@wYg2hg");
        driver.findElement(By.xpath("//*[@id=\"form\"]/div/div/div[1]/div/form/button")).click();

        Assert.assertTrue(driver.findElement(By.xpath("//*[@id=\"header\"]/div/div/div/div[2]/div/ul/li[4]/a")).isDisplayed());

        driver.quit();
    }


    @Test
    public void registerUserTest(){

        WebDriver driver = new ChromeDriver();
        driver.manage().window().maximize();
        wait = new WebDriverWait(driver, Duration.ofSeconds(10));

        // 2. Navigate to url 'http://automationexercise.com'
        driver.get("https://www.automationexercise.com");

        // 3. Verify that home page is visible successfully
        Assert.assertEquals(driver.getTitle(), "Automation Exercise");

        // 4. Click on 'Signup / Login' button
        driver.findElement(By.xpath("//a[contains(text(),'Signup / Login')]")).click();

        // 5. Verify 'New User Signup!' is visible
        WebElement newUserSignup = wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.xpath("//h2[contains(text(),'New User Signup!')]")));
        Assert.assertTrue(newUserSignup.isDisplayed(), "'New User Signup!' is not visible");

        // 6. Enter name and email address
        String uniqueEmail = "alex" + System.currentTimeMillis() + "@test.com";
        driver.findElement(By.name("name")).sendKeys("AlexTest");
        driver.findElement(By.xpath("//input[@data-qa='signup-email']")).sendKeys(uniqueEmail);

        // 7. Click 'Signup' button
        driver.findElement(By.xpath("//button[@data-qa='signup-button']")).click();

        // 8. Verify that 'ENTER ACCOUNT INFORMATION' is visible
        WebElement enterAccountInfo = wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.xpath("//b[contains(text(),'Enter Account Information')]")));
        Assert.assertTrue(enterAccountInfo.isDisplayed(), "'ENTER ACCOUNT INFORMATION' is not visible");

        // 9. Fill details: Title, Name, Email, Password, Date of birth
        driver.findElement(By.id("id_gender1")).click();
        driver.findElement(By.id("password")).sendKeys("123456");
        new Select(driver.findElement(By.id("days"))).selectByValue("10");
        new Select(driver.findElement(By.id("months"))).selectByValue("5");
        new Select(driver.findElement(By.id("years"))).selectByValue("1995");

        // 10. Select checkbox 'Sign up for our newsletter!'
        driver.findElement(By.id("newsletter")).click();

        // 11. Select checkbox 'Receive special offers from our partners!'
        driver.findElement(By.id("optin")).click();

        // 12. Fill details: First name, Last name, Company, Address, Address2, Country, State, City, Zipcode, Mobile Number
        driver.findElement(By.id("first_name")).sendKeys("Alex");
        driver.findElement(By.id("last_name")).sendKeys("Tester");
        driver.findElement(By.id("company")).sendKeys("Automation Inc");
        driver.findElement(By.id("address1")).sendKeys("123 Test Street");
        driver.findElement(By.id("address2")).sendKeys("Apt 45");
        new Select(driver.findElement(By.id("country"))).selectByVisibleText("Canada");
        driver.findElement(By.id("state")).sendKeys("Ontario");
        driver.findElement(By.id("city")).sendKeys("Toronto");
        driver.findElement(By.id("zipcode")).sendKeys("A1B2C3");
        driver.findElement(By.id("mobile_number")).sendKeys("1234567890");

        // 13. Click 'Create Account button'
        driver.findElement(By.xpath("//button[@data-qa='create-account']")).click();

        // 14. Verify that 'ACCOUNT CREATED!' is visible
        WebElement accountCreated = wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.xpath("//b[contains(text(),'Account Created!')]")));
        Assert.assertTrue(accountCreated.isDisplayed(), "'ACCOUNT CREATED!' is not visible");

        // 15. Click 'Continue' button
        driver.findElement(By.xpath("//a[@data-qa='continue-button']")).click();

        // 16. Verify that 'Logged in as username' is visible
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//a[contains(text(),'Logged in as')]")));
        WebElement loggedIn = driver.findElement(By.xpath("//a[contains(text(),'Logged in as')]"));
        Assert.assertTrue(loggedIn.isDisplayed(), "'Logged in as username' is not visible");

        // 17. Click 'Delete Account' button
        driver.findElement(By.xpath("//a[contains(text(),'Delete Account')]")).click();

        // 18. Verify that 'ACCOUNT DELETED!' is visible and click 'Continue' button
        WebElement accountDeleted = wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.xpath("//b[contains(text(),'Account Deleted!')]")));
        Assert.assertTrue(accountDeleted.isDisplayed(), "'ACCOUNT DELETED!' is not visible");
        driver.findElement(By.xpath("//a[@data-qa='continue-button']")).click();

        driver.quit();
    }
}