package school.redrover.page;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import school.redrover.common.BasePage;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class FolderPage extends BasePage {

    public FolderPage(WebDriver driver) {
        super(driver);
    }

    public FolderConfigurationPage clickConfigure() {
        getDriver().findElement(By.xpath("//span[text()='Configure']/..")).click();

        return new FolderConfigurationPage(getDriver());
    }

    public FolderInfo getInfo() {

        String displayName = getWait5().until(ExpectedConditions.visibilityOfElementLocated(By.tagName("h1"))).getText();
        String description = getDriver().findElement(By.id("view-message")).getText();

        return new FolderInfo(displayName, description);
    }

    public static class FolderInfo {
        private final String displayName;
        private final String description;

        public FolderInfo(String displayName, String description) {
            this.displayName = displayName;
            this.description = description;
        }

        public String getDisplayName() {
            return displayName;
        }

        public String getDescription() {
            return description;
        }
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

    public FolderPage confirmDeleteChildItem() {
        String urlBeforeDelete = getDriver().getCurrentUrl();

        WebElement yesButton = getWait2().until(
                ExpectedConditions.elementToBeClickable(
                        By.xpath("//dialog[@open]//button[@data-id='ok']"))
        );
        yesButton.click();

        getWait5().until(ExpectedConditions.not(ExpectedConditions.urlToBe(urlBeforeDelete)));
        return new FolderPage(getDriver());
    }

    public FolderPage clickAddDescriptionButton() {
        getDriver().findElement(By.id("description-link")).click();
        return this;
    }

    public FolderPage addDescriptionAndSave(String description) {
        getDriver().findElement(By.xpath("//textarea[@name='description']")).sendKeys(description);
        getDriver().findElement(By.name("Submit")).click();
        return this;
    }

    public String getDescription() {
        return getWait2().until(ExpectedConditions.visibilityOfElementLocated(
                By.id("description-content"))).getText();
    }

    public String getFolderContext() {
        return getWait2()
                .until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(".empty-state-block>section>h2")))
                .getText();
    }

    public NewItemPage clickNewItem() {
        getWait2().until(ExpectedConditions.visibilityOfElementLocated(By.tagName("h1")));
        getDriver().findElement(By.xpath("//span[text()='New Item']/..")).click();

        return new NewItemPage(getDriver());
    }

    public WebElement getElement(String name) {
        return getDriver().findElement(By.xpath("//span[text()='%s']".formatted(name)));
    }

    public List<String> getProjectList() {
        return getDriver().findElements(By.cssSelector(".jenkins-table__link >span:first-child"))
                .stream()
                .map(WebElement::getText)
                .toList();
    }

    public String getNameFolder() {
        return getWait2().until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//h1"))).getText();

    }

    public FolderPage clickBreadcrumbsItem(String folderName) {
        getDriver().findElement(By.xpath("//a[text()='%s']".formatted(folderName))).click();

        return this;
    }

    public String getFolderIconAttribute(String folderName) {
        return getDriver().findElement(By.xpath("//tr[td[a[span[text()='%s']]]]//*[@d]".formatted(folderName))).getAttribute("d");
    }

    public List<String> getItemsWithIconAttribute(String iconAttribute) {
        List<String> itemNames = new ArrayList<>();
        for (WebElement element : getDriver().findElements(By.xpath("//tr[.//*[contains(@d,'%s')]]//a//span".formatted(iconAttribute)))) {
            itemNames.add(element.getText());
        }
        return itemNames;
    }

    public String getFolderTooltip(String folderName) {
        Actions actions = new Actions(getDriver());
        WebElement folderStatusIcon = getDriver().findElement(By.xpath(
                "//tr[td//a[span[text()='%s']]]//*[contains(@class, 'symbol-folder-outline')]".formatted(folderName)));
        actions
                .moveToElement(folderStatusIcon)
                .perform();
        String folderTooltipIDByAttribute = getDriver().findElement(By.xpath("//*[@aria-describedby]"))
                .getAttribute("aria-describedby");

        return getDriver().findElement(By.xpath("//*[@id='%s']/div/div".formatted(folderTooltipIDByAttribute))).getText();
    }

    public List<String> getItemsWithTooltip(String expectedTooltip) {
        Actions actions = new Actions(getDriver());
        List<String> itemsWithTooltip = new ArrayList<>();
        for (WebElement statusIcon : getDriver().findElements(By.xpath("//tr[contains(@class, 'job')]/td[1]//*[@tooltip]"))) {
            actions
                    .moveToElement(statusIcon)
                    .perform();
            String itemTooltipIDByAttribute = getDriver().findElement(By.xpath("//*[@aria-describedby]"))
                    .getAttribute("aria-describedby");
            String actualTooltip = getDriver().findElement(By.xpath("//*[@id='%s']/div/div".formatted(itemTooltipIDByAttribute))).getText();

            if (actualTooltip.equals(expectedTooltip)) {
                WebElement itemNameElement = statusIcon.findElement(By.xpath("./ancestor::tr[1]//a//span"));
                itemsWithTooltip.add(itemNameElement.getText());
            }
        }

        return itemsWithTooltip;
    }

    public boolean checkURLContains(String expectedPath) {
        return Objects.requireNonNull(getDriver().getCurrentUrl()).contains(expectedPath);
    }
}
