package school.redrover.page;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import school.redrover.common.BasePage;

public class FolderPage extends BasePage {

    public FolderPage(WebDriver driver) {
        super(driver);
    }

    public String getFolderContext() {
        return getWait2()
                .until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//h2[text()='This folder is empty']")))
                .getText();
    }

    public NewItemPage clickCreateJob() {
        getDriver().findElement(By.xpath("//a[@href='newJob']")).click();

        return new NewItemPage(getDriver());
    }

    public NewItemPage clickNewItem() {
        getWait2().until(ExpectedConditions.visibilityOfElementLocated(By.tagName("h1")));
        getDriver().findElement(By.xpath("//span[text()='New Item']/..")).click();

        return new NewItemPage(getDriver());
    }

    public WebElement getElement(String name){
        return getDriver().findElement(By.xpath("//span[text()='%s']".formatted(name)));
    }


}
