package school.redrover.common.filter;

import org.testng.*;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

public class FilterForTests implements IMethodInterceptor {

    @Override
    public List<IMethodInstance> intercept(List<IMethodInstance> methods, ITestContext context) {
        String files = System.getenv("LIST_OF_CHANGED_FILES");
        if (files != null) {
            return FilterUtils.filterMethods(
                    Arrays.stream(files.split(";")).toList(),
                    methods);
        }

        return methods;
    }
}
