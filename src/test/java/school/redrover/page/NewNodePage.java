package school.redrover.page;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import school.redrover.common.BasePage;

public class NewNodePage extends BasePage {

    public NewNodePage(WebDriver driver) {
        super(driver);
    }

    @FindBy(id = "name")
    WebElement nodeName;

    @FindBy(css = "input[name='mode'] + label")
    WebElement typeModePermanentAgent;

    @FindBy(name = "Submit")
    WebElement buttonCreate;

    public String getHeadingText() {
        return getDriver().findElement(By.xpath("//h1")).getText();
    }

    public boolean isFormDisplayed() {
        return getDriver().findElement(By.xpath("//form")).isDisplayed();
    }

    public NewNodePage enterNodeName(String name) {
        nodeName.clear();
        nodeName.sendKeys(name);

        return  this;
    }
    public NewNodePage selectTypeNode() {
        typeModePermanentAgent.click();

        return this;
    }

    public NodesPage createFormNode() {
        buttonCreate.click();

        return new NodesPage(getDriver());
    }


}
