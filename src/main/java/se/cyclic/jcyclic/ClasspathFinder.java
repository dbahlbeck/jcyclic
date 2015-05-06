package se.cyclic.jcyclic;

import com.google.common.collect.ImmutableSet;
import com.google.common.reflect.ClassPath;
import org.apache.bcel.Repository;
import org.apache.bcel.classfile.JavaClass;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;


/**
 * This class finder simply scans the system class loader looking for all classes in a particular package and below.
 */
public class ClasspathFinder implements ClassFinder {

    /**
     * Constructs a class finder for a particular package.
     * @param basePackage base package name
     */
    public ClasspathFinder(String basePackage) {
        this.basePackage = basePackage;
    }

    private String basePackage;

    private List<JavaClass> getJavaClassList() {
        final ImmutableSet<ClassPath.ClassInfo> allClasses;
        try {
            allClasses = ClassPath.from(ClassLoader.getSystemClassLoader()).getAllClasses();
            final List<JavaClass> javaClasses = new ArrayList<>();
            for (ClassPath.ClassInfo allClass : allClasses) {
                if (allClass.getPackageName().startsWith(basePackage) && !allClass.getName().contains("$")) {
                    JavaClass javaClass = Repository.getRepository().loadClass(allClass.getName());
                    javaClasses.add(javaClass);
                }
            }
            return javaClasses;
        } catch (IOException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Collection<JavaClassInformation> getJavaClassInformationList() {
        List<JavaClassInformation> classes = new ArrayList<>();
        for (JavaClass javaClass : getJavaClassList()) {
            classes.add(new JavaClassInformation(javaClass));
        }
        return classes;
    }

}
