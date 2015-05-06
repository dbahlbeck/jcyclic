package se.cyclic.jcyclic;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;


public class TestClassFinder implements ClassFinder {
    private Map<String,JavaClassInformation> classInfos;

    public TestClassFinder() {
        classInfos = new HashMap<>();
    }

    public void addDependency(String from, String to) {
        JavaClassInformation fromClass = getClassInfo(from);
        fromClass.addDependencyToClass(to);
        
    }

    private JavaClassInformation getClassInfo(String className) {
        JavaClassInformation javaClassInformation = classInfos.get(className);
        JavaClassInformation classInformation = (javaClassInformation == null ? new JavaClassInformation(className) : javaClassInformation);
        classInfos.put(className, classInformation);
        return classInformation;
    }

    @Override
    public Collection<JavaClassInformation> getJavaClassInformationList() {
        return classInfos.values();
    }
}
