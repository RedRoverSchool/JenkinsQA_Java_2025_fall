package school.redrover.type;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

public interface BaseType {

    WebDriver getDriver();
    WebDriverWait getWait2();
    WebDriverWait getWait5();
    WebDriverWait getWait10();
}
