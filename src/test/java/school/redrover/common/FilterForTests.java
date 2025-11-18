package school.redrover.common;

import org.testng.*;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

public class FilterForTests implements IMethodInterceptor {

    @Override
    public List<IMethodInstance> intercept(List<IMethodInstance> methods, ITestContext context) {
        final String srcTestJava = "src/test/java/%s.java";

        String files = System.getenv("LIST_OF_CHANGED_FILES");
        String jdeps = System.getenv("JDEPS");

        if (files != null && jdeps != null) {
            List<String> entryList = Arrays.stream(files.split(";"))
                    .toList();

            Set<String> changedFiles = entryList.stream()
                    .filter(e -> !e.startsWith("D="))
                    .map(e -> e.substring(e.lastIndexOf('=') + 1))
                    .collect(Collectors.toSet());

            if (changedFiles.stream().anyMatch(path -> !path.endsWith(".java"))) {
                return methods;
            }

            Map<Class<?>, String> classMap = methods.stream()
                    .map(IMethodInstance::getMethod).map(ITestNGMethod::getTestClass).map(IClass::getRealClass)
                    .collect(Collectors.toMap(
                            Function.identity(),
                            clazz -> String.format("src/test/java/%s.java", clazz.getName().replace('.', '/')),
                            (pathA, pathB) -> pathA
                    ));

            Map<String, Set<String>> jdepsMap = Arrays.stream(jdeps.split(";"))
                    .map(part -> part.split("->"))
                    .collect(Collectors.groupingBy(parts -> String.format(srcTestJava, parts[0].replace('.', '/')),
                            Collectors.mapping(
                                    parts -> String.format(srcTestJava, parts[1].replace('.', '/')),
                                    Collectors.toSet()
                            )));

            Map<String, Set<String>> reverseDeps = createReverseDeps(jdepsMap);

            Set<String> filesToRun = getAffectedFiles(changedFiles, reverseDeps);

            if (filesToRun.stream().noneMatch(path -> classMap.values().contains(path))) {
                return methods;
            }

            return methods.stream().filter(method -> changedFiles.contains(classMap.get(method.getMethod().getTestClass().getRealClass()))).collect(Collectors.toList());
        }

        return methods;
    }

    private static Map<String, Set<String>> createReverseDeps(Map<String, Set<String>> directDepsPaths) {
        Map<String, Set<String>> reverseDepsPaths = new HashMap<>();

        directDepsPaths.forEach((sourcePath, targetPaths) -> {
            for (String targetPath : targetPaths) {
                reverseDepsPaths.computeIfAbsent(targetPath, k -> new HashSet<>()).add(sourcePath);
            }
        });
        return reverseDepsPaths;
    }

    private static Set<String> getAffectedFiles(Set<String> directlyChangedFile, Map<String, Set<String>> reverseDeps) {
        Set<String> filesToRun = new HashSet<>(directlyChangedFile);

        for (String changedPath : directlyChangedFile) {
            Set<String> dependentPaths = reverseDeps.get(changedPath);
            if (dependentPaths != null) {
                filesToRun.addAll(dependentPaths);
            }
        }

        return filesToRun;
    }
}
