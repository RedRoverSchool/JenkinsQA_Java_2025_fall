package school.redrover.type;

import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;

public interface BasePageWithHeading extends BaseType {

    default String getHeadingText() {
        return getWait5()
                .until(ExpectedConditions.presenceOfElementLocated(By
                .tagName("h1"))).getText().trim();
    }
}
