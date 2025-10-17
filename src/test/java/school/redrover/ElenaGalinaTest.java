package school.redrover;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.edge.EdgeDriver;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.List;


public class ElenaGalinaTest {
    private WebDriver driver;

    private static final String BASE_URL = "https://www.saucedemo.com";

    @BeforeMethod
    public void setup() {
        driver = new EdgeDriver();
        driver.manage().window().maximize();
        driver.get(BASE_URL);
    }

    public void loginStandartUser() {
        driver.findElement(By.id("user-name")).sendKeys("standard_user");
        driver.findElement(By.id("password")).sendKeys("secret_sauce");
        driver.findElement(By.id("login-button")).click();
    }

    @Test
    public void checkOfAddButton() {
        loginStandartUser();

        List<WebElement> products = driver.findElements(By.xpath(".//div[@class=\"inventory_item\"]"));

        List<String> addButtonsNameList = new ArrayList<>();
        List<String> currentAddButtonsNameList = new ArrayList<>();

        for (int i = 0; i < products.size(); i++) {
            products = driver.findElements(By.xpath(".//div[@class =\"inventory_item\"]"));

            WebElement addButtonName = products.get(i).findElement(By.cssSelector(".btn.btn_primary"));

            if (addButtonName.getText().equals("Add to cart")) {
                addButtonsNameList.add(addButtonName.getText());
            } else {
                System.out.println("Наименование кнопки " + addButtonName.getText() + " не соответствует \"Add to cart\"");
            }

            WebElement nameForClick = products.get(i).findElement(By.xpath(".//div[@class=\"inventory_item_name \"]"));
            nameForClick.click();

            WebElement currentButtonName = driver.findElement(By.id("add-to-cart"));
            currentAddButtonsNameList.add(currentButtonName.getText());

            driver.findElement(By.id("back-to-products")).click();
        }

        for (String button : currentAddButtonsNameList) {
            Assert.assertEquals(button, "Add to cart");
        }

        Assert.assertEquals(addButtonsNameList, currentAddButtonsNameList);
    }


    @Test
    public void checkOfRemoveButton() {
        loginStandartUser();

        List<WebElement> products = driver.findElements(By.xpath(".//div[@class=\"inventory_item\"]"));

        List<String> removeButtonsNamesList = new ArrayList<>();
        List<String> currentRemoveButtonsNameList = new ArrayList<>();

        for (int i = 0; i < products.size(); i++) {
            products = driver.findElements(By.xpath(".//div[@class=\"inventory_item\"]"));

            WebElement addButton = products.get(i).findElement(By.cssSelector(".btn.btn_primary"));
            addButton.click();

            try {
                WebElement removeButtonName = products.get(i).findElement(By.cssSelector(".btn.btn_secondary"));

                if (removeButtonName.getText().equals("Remove")) {
                    removeButtonsNamesList.add(removeButtonName.getText());
                } else {
                    System.out.println("Наименование кнопки " + removeButtonName.getText() + " не соответствует \"Remove\"");
                }

                WebElement nameForClick = products.get(i).findElement(By.xpath(".//div[@class=\"inventory_item_name \"]"));
                nameForClick.click();

                WebElement currentButtonName = driver.findElement(By.id("remove"));
                currentRemoveButtonsNameList.add(currentButtonName.getText());

                driver.findElement(By.id("back-to-products")).click();

            } catch (Exception e) {
                System.out.println("Кнопка Remove не найдена");
            }
        }
        for (String button : currentRemoveButtonsNameList) {
            Assert.assertEquals(button, "Remove");
        }

        Assert.assertEquals(removeButtonsNamesList, currentRemoveButtonsNameList);
    }

    @AfterMethod
    public void tearDown() {
        driver.quit();
    }
}
