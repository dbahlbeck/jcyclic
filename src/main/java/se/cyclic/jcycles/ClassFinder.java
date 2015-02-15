package se.cyclic.jcycles;

import com.google.common.collect.ImmutableSet;
import com.google.common.reflect.ClassPath;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


public class ClassFinder {

    /**
     * Constructs a class finder for a particular package.
     * @param basePackage base package name
     */
    public ClassFinder(String basePackage) {
        this.basePackage = basePackage;
    }

    private String basePackage;

    List<String> getClassNamesFromSystemClassLoader() {
        final ImmutableSet<ClassPath.ClassInfo> allClasses;
        try {
            allClasses = ClassPath.from(ClassLoader.getSystemClassLoader()).getAllClasses();
            final List<String> strings = new ArrayList<>();
            for (ClassPath.ClassInfo allClass : allClasses) {
                if (allClass.getPackageName().startsWith(basePackage))
                    strings.add(allClass.getName());
            }
            return strings;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
