package school.redrover.common.filter;

import org.testng.*;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

public class FilterUtils {

    public static List<IMethodInstance> filterMethods(List<String> fileList, List<IMethodInstance> methodList) {
        Set<String> changedFiles = fileList.stream()
                .filter(e -> !e.startsWith("D="))
                .map(e -> e.substring(e.lastIndexOf('=') + 1))
                .collect(Collectors.toSet());

        Map<Class<?>, String> classMap = methodList.stream()
                .map(IMethodInstance::getMethod).map(ITestNGMethod::getTestClass).map(IClass::getRealClass)
                .collect(Collectors.toMap(
                        Function.identity(),
                        clazz -> String.format("src/test/java/%s.java", clazz.getName().replace('.', '/')),
                        (pathA, pathB) -> pathA
                ));

        if (classMap.values().containsAll(changedFiles)) {
            return methodList.stream().filter(method -> changedFiles.contains(classMap.get(method.getMethod().getTestClass().getRealClass()))).collect(Collectors.toList());
        }

        return methodList;
    }
}
