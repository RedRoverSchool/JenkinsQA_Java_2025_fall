package school.redrover.common.filter;

import org.testng.*;
import org.testng.annotations.Test;

import java.util.List;

public class FilterForTestsTest {

    @Test
    public void testDeletedClass() {
        List<String> fileList = List.of("D=src/test/java/FileTest.java");
        List<IMethodInstance> methodList = List.of(new FilterMock.MethodInstanceImpl(FilterForTestsTest.class));

        List<IMethodInstance> resultList = FilterUtils.filterMethods(fileList, methodList);

        Assert.assertEquals(resultList.size(), 0);
    }
}
