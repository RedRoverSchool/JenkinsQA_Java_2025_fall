package school.redrover;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.Test;

public class ElenaPTest {
    @Test
    public void testAddReview() {

        //  1. Launch browser
        WebDriver driver = new ChromeDriver();

        //  2. Navigate to url 'http://automationexercise.com'
        driver.get("https://www.automationexercise.com/");

        //  3. Click on 'Products' button
        WebElement submitProductsButton = driver.findElement(By.cssSelector("a[href='/products']"));
        submitProductsButton.click();

        //  4. Verify user is navigated to ALL PRODUCTS page successfully
        String pageProductsTitle = driver.getTitle();
        driver.findElement(By.cssSelector(".title.text-center"));

        Assert.assertEquals(pageProductsTitle, "Automation Exercise - All Products", "Ошибка! Заголовок страницы не совпал с ожидаемым.");

        //  5. Click on 'View Product' button
        WebElement viewProductButton = driver.findElement(By.cssSelector("a[href='/product_details/1']"));
        viewProductButton.click();

        //  6. Verify 'Write Your Review' is visible
        WebElement messageReview = driver.findElement(By.cssSelector("a[href='#reviews']"));
        Assert.assertTrue(messageReview.isDisplayed(), "The element should be visible on the page.");

        //  7. Enter name, email and review
        WebElement nameTextBox = driver.findElement(By.cssSelector("#name"));
        nameTextBox.sendKeys("username");

        WebElement emailTextBox = driver.findElement(By.cssSelector("#email"));
        emailTextBox.sendKeys("email@test.ru");

        WebElement reviewTextBox = driver.findElement(By.cssSelector("#review"));
        reviewTextBox.sendKeys("test review text");

        //  8. Click 'Submit' button
        WebElement reviewSubmitButton = driver.findElement(By.cssSelector("#button-review"));
        reviewSubmitButton.click();

        //  9. Verify success message 'Thank you for your review.'
        WebElement element = driver.findElement(By.cssSelector("#review-section .alert-success.alert"));
        Assert.assertTrue(element.isDisplayed(), "The element should be visible on the page.");

        driver.quit();

    }
}
