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

    public NewItemPage clickSidebarNewItem() {
        getDriver().findElement(By.xpath("//a[contains(@href, '/newJob')]")).click();
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

    public FolderPage openFolderPage(String folderName) {
        getDriver().findElement(By.linkText(folderName)).click();

        return this;
    }

    public FolderPage clickDeleteFolder() {
        WebElement deleteButton = getWait2().until(
                ExpectedConditions.elementToBeClickable(
                        By.xpath("//a[@data-title='Delete Folder']"))
        );
        deleteButton.click();

        getWait2().until(ExpectedConditions.visibilityOfElementLocated(
                By.xpath("//dialog[@open]")));

        return this;
    }

    public FolderPage confirmDeleteChild() {
        WebElement yesButton = getWait2().until(
                ExpectedConditions.elementToBeClickable(
                        By.xpath("//dialog[@open]//button[@data-id='ok']"))
        );
        yesButton.click();

        return this;
    }

    public String getFolderContext() {
        return getWait2()
                .until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//h2[text()='This folder is empty']")))
                .getText();
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
