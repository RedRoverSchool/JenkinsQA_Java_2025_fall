package school.redrover.page;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
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

    public String getHeadingText() {
        return getWait2()
                .until(ExpectedConditions.presenceOfElementLocated(By.cssSelector(".empty-state-block > h1")))
                .getText();
    }

    public WebElement findItem(String itemName) {
        return getDriver().findElement(By.xpath("//a[@href='job/" + itemName + "/']"));
    }

    public String getSystemMessage() {
        return getWait5().until(ExpectedConditions.visibilityOfElementLocated(
                By.id("systemmessage"))).getText();
    }

    public HomePage openDropdownMenu(String itemName) {
        WebElement dropdownButton = getWait5().until(
                ExpectedConditions.visibilityOfElementLocated(By.xpath(
                        "//a[.//span[text()='%s']]//button[@class='jenkins-menu-dropdown-chevron']".formatted(itemName))));

        TestUtils.mouseEnterJS(getDriver(), dropdownButton);
        TestUtils.clickJS(getDriver(), dropdownButton);

        return this;
    }

    public MovePage clickMoveInDropdownMenu() {
        getWait2().until(ExpectedConditions.elementToBeClickable(By.xpath("//a[contains(@class, 'jenkins-dropdown__item') and contains(., 'Move')]"))).click();

        return new MovePage(getDriver());
    }

    public HomePage clickDeleteItemInDropdownMenu() {
        getWait2().until(ExpectedConditions.elementToBeClickable(By.xpath("//button[contains(@class, 'jenkins-dropdown__item') and contains(., 'Delete')]"))).click();

        return this;
    }

    public RenameFolderPage clickRenameItemInDropdownMenu() {
        getWait5().until(ExpectedConditions.elementToBeClickable(By.xpath("//div[@class='tippy-content']//div[@class='jenkins-dropdown']//a[normalize-space()='Rename']"))).click();

        return new RenameFolderPage(getDriver());
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

    public String getActiveViewName() {
        return getDriver().findElement(By.cssSelector(".tab.active a")).getText();
    }

    public CreateViewPage clickPlusToCreateView() {
        getDriver().findElement(By.cssSelector("[href='/newView']")).click();

        return new CreateViewPage(getDriver());
    }

    public HomePage clickViewName(String viewName) {
        getDriver().findElement(By.linkText(viewName)).click();

        return new HomePage(getDriver());
    }

    public HomePage clickDeleteViewOnSidebar() {
        getWait5().until(ExpectedConditions.elementToBeClickable(
                By.xpath("//a[@data-title='Delete View']"))).click();

        return this;
    }

    public HomePage clickYesToConfirmDelete() {
        String urlBeforeDelete = getDriver().getCurrentUrl();

        getWait5().until(ExpectedConditions.elementToBeClickable(
                By.xpath("//button[@data-id='ok']"))).click();

        getWait5().until(ExpectedConditions.not(ExpectedConditions.urlToBe(urlBeforeDelete)));

        return new HomePage(getDriver());
    }

    public int getSizeOfViewNameList() {
        List<WebElement> viewNameList = getDriver().findElements(By.xpath("//div[@class='tabBar']/div"));

        return viewNameList.size();
    }

    public String getParagraghText() {
        return getWait2()
                .until(ExpectedConditions.presenceOfElementLocated(By.tagName("p")))
                .getText();
    }

    public HomePage clickDescription() {
        getWait2().until(ExpectedConditions.elementToBeClickable(By.id("description-link"))).click();
        return this;
    }

    public HomePage sendDescriptionText(String text) {
        getWait2().until(ExpectedConditions.visibilityOfElementLocated(By.name("description"))).sendKeys(text);
        return this;
    }

    public HomePage submitDescription() {
        getDriver().findElement(By.name("Submit")).click();

        return this;
    }

    public String getDescription() {
        return getWait2().until(ExpectedConditions.visibilityOfElementLocated(By.id("description-content"))).getText();
    }

    public ManageJenkinsPage clickManageJenkinsIcon() {
        getDriver().findElement(By.id("root-action-ManageJenkinsAction")).click();

        return new ManageJenkinsPage(getDriver());
    }

    public HomePage clearTextDescription() {
        getWait2().until(ExpectedConditions.visibilityOfElementLocated(By.name("description"))).clear();
        return this;
    }

    public UserAccountPage clickUserAccountViaIconInHeader() {
        TestUtils.clickJS(getDriver(), By.id("root-action-UserAction"));

        return new UserAccountPage(getDriver());
    }

    public WebElement getRestApiLink(){
        return getDriver().findElement(By.xpath("//a[@href='api/']"));
    }

    public void pressTabAndEnter(WebElement element) {
        WebElement body = getDriver().findElement(By.tagName("body"));

        int max_tabs = 50;

        for (int i = 0; i < max_tabs; i++) {
            body.sendKeys(Keys.TAB);
            WebElement activeElement = getDriver().switchTo().activeElement();
            if (activeElement.equals(element)) {
                activeElement.sendKeys(Keys.ENTER);
                break;
            }
        }
    }

    public String getProjectStatus(String projectName) {
        new Actions(getDriver())
                .moveToElement(getDriver().findElement(By.xpath("//*[@id='job_%s']/td[1]/div".formatted(projectName))))
                .perform();
        return getWait5().until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("[data-tippy-root]")))
                .getText();
    }
      
    public <T extends BasePage> T clickHomePageSectionLink(String linkText) {
        getWait2().until(ExpectedConditions.elementToBeClickable(By.xpath("//span[text()='%s']/..".formatted(linkText))))
                .click();

        return (T) this;
    }
}
