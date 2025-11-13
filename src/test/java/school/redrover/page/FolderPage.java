package school.redrover.page;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import school.redrover.common.BasePage;

import java.util.ArrayList;
import java.util.List;

public class FolderPage extends BasePage {

    public FolderPage(WebDriver driver) {
        super(driver);
    }

    public NewItemPage clickNewItem() {
        getDriver().findElement(By.xpath("//a[@href='newJob']")).click();
        return new NewItemPage(getDriver());
    }

    public List<String> getBreadcrumbTexts() {
        List<WebElement> breadcrumbElements = getWait2().until(
                ExpectedConditions.visibilityOfAllElementsLocatedBy(
                        By.xpath("//ol[@id='breadcrumbs']/li/a")));

        List<String> breadcrumbTexts = new ArrayList<>();
        for (WebElement element : breadcrumbElements) {
            breadcrumbTexts.add(element.getText());
        }

        return breadcrumbTexts;
    }
}
