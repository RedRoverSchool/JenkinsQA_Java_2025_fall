package school.redrover.page;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import school.redrover.common.BasePage;
import school.redrover.common.TestUtils;

import java.util.List;

public class HomePage extends BasePage {

    public HomePage(WebDriver driver) {
        super(driver);
    }

    public NewItemPage clickCreateJob() {
        getDriver().findElement(By.xpath("//a[@href='newJob']")).click();

        return new NewItemPage(getDriver());
    }

    public NewItemPage clickNewItemOnLeftMenu() {
        getDriver().findElement(By.cssSelector("a[href='/view/all/newJob']")).click();

        return new NewItemPage(getDriver());
    }

    public List<String> getProjectList() {
        return getDriver().findElements(By.cssSelector(".jenkins-table__link >span:first-child"))
                .stream()
                .map(WebElement::getText)
                .toList();
    }

    public FolderPage clickFolder(String folderName) {
        getDriver().findElement(By.xpath("//span[text()='%s']".formatted(folderName))).click();

        return new FolderPage(getDriver());
    }

    public <T extends BasePage> T openJobPage(String jobName, T resultPage) {
        TestUtils.clickJS(getDriver(), By.xpath("//span[text()='%s']".formatted(jobName.trim())));

        return resultPage;
    }

    public NewItemPage clickSidebarNewItem() {
        getDriver().findElement(By.xpath("//a[@href='/view/all/newJob']")).click();

        return new NewItemPage(getDriver());
    }

    public String getTitle() {
        return getWait2().until(ExpectedConditions.presenceOfElementLocated(By.tagName("h1"))).getText();
    }

    public WebElement findItem(String itemName) {
        return getDriver().findElement(By.xpath("//a[@href='job/" + itemName + "/']"));
    }

    public HomePage openDropdownMenu(String itemName) {
        Actions actions = new Actions(getDriver());
        actions.moveToElement(getDriver().findElement(By.xpath("//span[text()='%s']".formatted(itemName)))).perform();
        actions.moveToElement(getDriver().findElement(By.xpath(
                "//a[.//span[text()='%s']]//button[@class='jenkins-menu-dropdown-chevron']".formatted(itemName)))).click().perform();
        return this;
    }

    public HomePage clickDeleteItemInDropdownMenu() {
        TestUtils.clickJS(getDriver(), By.xpath("//button[.//text()[contains(., 'Delete')]]"));

        return this;
    }

    public HomePage confirmDelete() {
        WebElement yesButton = getWait2().until(
                ExpectedConditions.elementToBeClickable(
                        By.xpath("//dialog[@open]//button[@data-id='ok']"))
        );
        yesButton.click();
        getWait5().until(ExpectedConditions.stalenessOf(yesButton));

        return this;
    }
}
