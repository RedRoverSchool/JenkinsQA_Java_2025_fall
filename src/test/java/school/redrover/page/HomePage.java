package school.redrover.page;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import school.redrover.common.BasePage;
import school.redrover.common.TestUtils;

import java.util.List;

public class HomePage extends BasePage {

    public HomePage(WebDriver driver) {
        super(driver);
    }

    public NewItemPage clickNewItem() {
        getDriver().findElement(By.xpath("//a[@href='newJob']")).click();

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
        TestUtils.clickJS(getDriver(), By.cssSelector("#projectstatus a[href='job/%s/']".formatted(jobName)));

        return resultPage;
    }
}
