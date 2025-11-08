package school.redrover;

import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.annotations.Test;
import school.redrover.common.BaseTest;

    public class Footer1RestApiTest extends BaseTest {

        private String getRestApiHeader(){
            getDriver().findElement(By.xpath("/html/body/footer/div/div[2]/a")).click();
            return getDriver().findElement(By.cssSelector("h1")).getText();
        }

        @Test
        public void testRestApiMainPage(){
            Assert.assertEquals(
                    getRestApiHeader(),
                    "REST API");
        }

        @Test
        public void  testRestApiUserPage(){

            getDriver().findElement(By.id("root-action-UserAction")).click();

            Assert.assertEquals(
                    getRestApiHeader(),
                    "REST API");
        }

        @Test
        public void testRestApiNewItemPage(){

            getDriver().findElement(By.xpath("/html/body/div[2]/div[1]/div[1]/div[1]/span/a")).click();

            Assert.assertEquals(
                    getRestApiHeader(),
                    "REST API");
        }

        @Test
        public void testRestApiNewNodePage() {

            getDriver().findElement(By.xpath("/html/body/div[2]/div[2]/div[2]/div/section[2]/ul/li[1]/a")).click();

            Assert.assertEquals(
                    getRestApiHeader(),
                    "REST API");
        }
        @Test
        public void testRestApiNodesPage(){

            getDriver().findElement(By.xpath("//*[@id='executors']/div[1]")).click();

            Assert.assertEquals(
                    getRestApiHeader(),
                    "REST API");
        }

        @Test
        public void testRestApiBuildHistoryOfJenkinsPage(){

            getDriver().findElement(By.xpath("//*[@id='tasks']/div[2]/span/a")).click();

            Assert.assertEquals(
                    getRestApiHeader(),
                    "REST API");
        }
    }


