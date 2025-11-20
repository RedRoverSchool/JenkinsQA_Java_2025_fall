package school.redrover.common.filter;

import org.testng.IMethodInstance;
import org.testng.IMethodInterceptor;
import org.testng.ITestContext;

import java.util.Arrays;
import java.util.List;

public class FilterForTests implements IMethodInterceptor {

    @Override
    public List<IMethodInstance> intercept(List<IMethodInstance> methods, ITestContext context) {
        String files = System.getenv("LIST_OF_CHANGED_FILES");
        String dependenciesClasses = System.getenv("LIST_OF_DEPENDENCIES_CLASSES");
        if (files != null && dependenciesClasses != null) {
            return FilterUtils.filterMethods(
                    Arrays.stream(files.split(";")).toList(),
                    dependenciesClasses,
                    methods);
        }

        return methods;
    }
}
