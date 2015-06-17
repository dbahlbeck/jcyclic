package se.cyclic.jcyclic;

import org.apache.bcel.classfile.JavaClass;
import org.junit.Assert;
import org.junit.Test;

import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;


public class DirectoryFinderTest {

    @Test
    public void testGetMainJavaClasses() {
        ClassFinder finder = new DirectoryFinder(new File("build/classes/main"));
        Collection<JavaClassInformation> javaClasses = finder.getJavaClassInformationList();

        List<String> classNames = convertToClassNames(javaClasses);
        Assert.assertTrue(classNames.contains("se.cyclic.jcyclic.ReferenceExtractingVisitor"));
        Assert.assertTrue(classNames.contains("se.cyclic.jcyclic.ClassDependencies"));
        Assert.assertTrue(classNames.contains("se.cyclic.jcyclic.ClassFinder"));
        Assert.assertTrue(classNames.contains("se.cyclic.jcyclic.ClasspathFinder"));
        Assert.assertTrue(classNames.contains("se.cyclic.jcyclic.DirectoryFinder"));
        Assert.assertTrue(classNames.contains("se.cyclic.jcyclic.JavaClassInformation"));
        Assert.assertTrue(classNames.contains("se.cyclic.jcyclic.Dependency"));

        Assert.assertEquals(7, classNames.size());
    }

    @Test
    public void testGetTestJavaClasses() {
        ClassFinder finder = new DirectoryFinder(new File("build/classes/test"));
        Collection<JavaClassInformation> javaClasses = finder.getJavaClassInformationList();
        List<String> classNames = convertToClassNames(javaClasses);

        Assert.assertTrue(classNames.contains("se.cyclic.jcyclic.ClasspathFinderTest"));
        Assert.assertTrue(classNames.contains("se.cyclic.jcyclic.DirectoryFinderTest"));
        Assert.assertTrue(classNames.contains("se.cyclic.jcyclic.ClassDependenciesTest"));
        Assert.assertTrue(classNames.contains("se.cyclic.jcyclic.TestClassFinder"));

        Assert.assertEquals(4, classNames.size());
    }

    @Test
    public void testEmptyDir() {
        DirectoryFinder directoryFinder = new DirectoryFinder(new File("doesNotExist"));
        Assert.assertTrue(directoryFinder.getJavaClassInformationList().isEmpty());
    }

    private List<String> convertToClassNames(Collection<JavaClassInformation> javaClasses) {
        List<String> classNames = new ArrayList<>();
        for (JavaClassInformation javaClass : javaClasses) {
            classNames.add(javaClass.getFullyQualifiedClassName());
        }
        return classNames;
    }


    // DirectoryFinder should ignore this
    private class InnerClassForTesting {

    }
}
