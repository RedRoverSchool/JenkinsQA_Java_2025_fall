package school.redrover.old;

import org.testng.Assert;
import org.testng.annotations.Ignore;
import org.testng.annotations.Test;



@Ignore
public class SandraTest {
    @Test
    public void testReverse(){
        String result = reverse("123");

        Assert.assertEquals(result,"321");
    }

    public static String reverse(String str){
        String result = "";
        for (int i = str.length()-1; i >= 0; i--) {
            result += str.charAt(i);
        }
        return result;
    }

}
