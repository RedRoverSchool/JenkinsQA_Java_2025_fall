package school.redrover.page;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import school.redrover.common.BasePage;
import school.redrover.common.TestUtils;

import java.util.Arrays;
import java.util.List;
import java.util.function.Supplier;


public class HomePage extends BasePage {

    @FindBy(xpath = "//a[@href='/view/all/newJob']")
    private WebElement sidebarNewItem;

    @FindBy(css = "[href='/newView']")
    private WebElement createNewItem;

    @FindBy(css = "[class*=\"job-status\"] td:first-child svg")
    private WebElement statusTooltipProjectIcon;

    @FindBy(xpath = "//div[@class='tabBar']/div")
    private List<WebElement> viewNameList;

    @FindBy(xpath = "//div/section[2]/ul/li[1]/a")
    private WebElement setUpAnAgentButton;

    @FindBy(linkText = "Build Executor Status")
    private WebElement buildExecutorStatusButton;

    @FindBy(xpath = "//div[2]/span/a")
    private WebElement buildHistoryButton;


    public HomePage(WebDriver driver) {
        super(driver);
    }

    public NewItemPage clickCreateJob() {
        getDriver().findElement(By.xpath("//a[@href='newJob']")).click();

        return new NewItemPage(getDriver());
    }

    public NewItemPage clickNewItemOnLeftMenu() {
        sidebarNewItem.click();

        getWait5().until(ExpectedConditions.visibilityOfElementLocated(By.id("name")));
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

    public <T extends BaseProjectPage> T openProject(String jobName, T resultPage) {
        TestUtils.clickJS(getDriver(), By.xpath("//span[text()='%s']".formatted(jobName.trim())));

        resultPage.waitUntilPageLoad();
        return resultPage;
    }

    public <T extends BasePage> T openPage(String pageName, T resultPage) {
        TestUtils.clickJS(getDriver(), By.xpath("//span[text()='%s']".formatted(pageName.trim())));

        getWait5().until(ExpectedConditions.presenceOfElementLocated(By.tagName("h1")));
        return resultPage;
    }

    public NewItemPage clickSidebarNewItem() {
        sidebarNewItem.click();

        getWait2().until(ExpectedConditions.visibilityOfElementLocated(By.id("name")));
        return new NewItemPage(getDriver());
    }

    public String getHeadingText() {
        return getWait2()
                .until(ExpectedConditions.presenceOfElementLocated(By.cssSelector(".empty-state-block > h1")))
                .getText();
    }

    public String getProjectName() {
        return getWait2().until(ExpectedConditions.presenceOfElementLocated(
                        By.cssSelector(".jenkins-table__link >span:first-child"))).getText();
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

    public PipelineSyntaxPage clickPipelineSyntaxInDropdownMenu() {
        getWait2().until(ExpectedConditions.elementToBeClickable(By.xpath("//a[contains(@class, 'jenkins-dropdown__item') and contains(., 'Pipeline Syntax')]"))).click();

        return new PipelineSyntaxPage(getDriver());
    }


    public HomePage clickDeleteItemInDropdownMenu() {
        getWait2().until(ExpectedConditions.elementToBeClickable(By.xpath("//button[contains(@class, 'jenkins-dropdown__item') and contains(., 'Delete')]"))).click();

        return this;
    }

    public FreestyleProjectConfigurationPage clickConfigureInDropdownMenu() {
        getWait2().until(ExpectedConditions.elementToBeClickable(By.xpath("//a[contains(@href,'configure')]"))).click();

        return new FreestyleProjectConfigurationPage(getDriver());
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

    public HomePage cancelDelete() {
        WebElement yesButton = getWait2().until(
                ExpectedConditions.elementToBeClickable(
                        By.xpath("//dialog[@open]//button[@data-id='cancel']"))
        );
        yesButton.click();
        getWait5().until(ExpectedConditions.stalenessOf(yesButton));

        return this;
    }

    public String getActiveViewName() {
        return getDriver().findElement(By.cssSelector(".tab.active a")).getText();
    }

    public CreateViewPage clickPlusToCreateView() {
        createNewItem.click();

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

    public HomePage clearTextDescription() {
        getWait2().until(ExpectedConditions.visibilityOfElementLocated(By.name("description"))).clear();
        return this;
    }

    public WebElement getRestApiLink() {

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

    public String getNumberOfExecutors() {
        WebElement executors = getDriver().findElement(By.cssSelector("div#executors"));

        String executorsLine;
        if (executors.getAttribute("class").contains("expanded")) {
            executorsLine = getDriver().findElement(By.cssSelector("span[tooltip*='executors busy']")).getAttribute("tooltip");
        } else {
            executorsLine = getDriver().findElement(By.className("executors-collapsed")).getText();
        }

        return Arrays.stream(executorsLine.trim().split(" "))
                .skip(2)
                .findFirst()
                .orElse(null);
    }

    public int getCountOfDisplayedColumnsOnDashboard() {
        List<WebElement> columnsList = getDriver().findElements(By.cssSelector("#projectstatus > thead > tr > th"));

        return columnsList.size();
    }

    public String getStatusProjectIconTooltipTextOnHover() {
        new Actions(getDriver()).moveToElement(statusTooltipProjectIcon).perform();

        return getWait5().until(ExpectedConditions.visibilityOfElementLocated(By.className("tippy-content")))
                .getText();
    }

    public EditViewPage clickEditViewButton(String listViewName) {
        getWait10().until(ExpectedConditions.elementToBeClickable(By
                .xpath(".//a[@href='/view/%s/configure']".formatted(listViewName)))).click();

        return new EditViewPage(getDriver());
    }

    public String getTitle() {
        return getDriver().getTitle();
    }

    public NewNodePage clickSetUpAnAgent() {
        setUpAnAgentButton.click();

        getWait5().until(ExpectedConditions.presenceOfElementLocated(By.tagName("h1")));
        return new NewNodePage(getDriver());
    }

    public NodesPage clickBuildExecutorStatus() {
        buildExecutorStatusButton.click();

        getWait5().until(ExpectedConditions.presenceOfElementLocated(By.tagName("h1")));
        return new NodesPage(getDriver());
    }

    public BuildHistoryOfJenkinsPage clickBuildHistory() {
        buildHistoryButton.click();

        getWait5().until(ExpectedConditions.presenceOfElementLocated(By.tagName("h1")));
        return new BuildHistoryOfJenkinsPage(getDriver());
    }

    public ArchitectingforScalePage clickLearnMoreAboutDistributedBuildsLink() {
        getDriver().findElement(By.xpath(".//a[span[text()='Learn more about distributed builds']]"))
                .click();
        Object[] windowHandles = getDriver().getWindowHandles().toArray();
        getDriver().switchTo().window((String) windowHandles[1]);

        getWait2().until(ExpectedConditions.urlContains("architecting-for-scale"));

        return new ArchitectingforScalePage(getDriver());
    }

    public boolean isBuildButtonVisible(String projectName) {
        return getWait5().until(ExpectedConditions.visibilityOfElementLocated(
                By.xpath("(//a[@title='Schedule a Build for %s'])[1]".formatted(projectName)))).isDisplayed();
    }

    public boolean isIconTableVisible (String projectName) {
        return getWait5().until((ExpectedConditions.visibilityOfElementLocated(By.cssSelector("#job_%s > td:nth-child(1)".formatted(projectName))))).isDisplayed();
    }

    public JobPage clickJob (String projectName) {
        getDriver().findElement(By.cssSelector("#job_%s > td:nth-child(3) > a".formatted(projectName))).click();
        return new JobPage(getDriver());
    }
}