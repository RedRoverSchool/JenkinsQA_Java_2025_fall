package school.redrover.page;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import school.redrover.common.BasePage;

import java.util.List;

public class CreateViewPage extends BasePage {

    @FindBy(id = "name")
    private WebElement nameField;

    @FindBy(xpath = "//label[text() = 'My View']")
    private WebElement clickMyView;

    public CreateViewPage(WebDriver driver) {
        super(driver);
    }

    public CreateViewPage sendViewName(String name){
        nameField.sendKeys(name);

        return this;
    }

    public CreateViewPage clickMyViewName(){
        clickMyView.click();

        return this;
    }

    public HomePage clickCreateButtonForNewView() {
        getWait2().until(ExpectedConditions.elementToBeClickable(By.id("ok"))).click();

        return new HomePage(getDriver());
    }

    public List<String> getTypeViewList(){
        return getDriver().findElements(By.xpath("//div[@class='jenkins-radio']//label"))
                .stream()
                .map(WebElement::getText)
                .toList();
    }

    public EditViewPage selectListViewRadioAndCreate(){
        getWait5().until(ExpectedConditions.presenceOfElementLocated(By.xpath("//label[text() = 'List View']"))).click();

        getWait2().until(ExpectedConditions.elementToBeClickable(By.id("ok"))).click();
        getWait2().until(ExpectedConditions.presenceOfElementLocated(By.
                xpath(".//div[@id='main-panel']/descendant::h1[contains(text(),'Edit View')]")));

        return new EditViewPage(getDriver());
    }
}