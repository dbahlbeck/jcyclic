package se.cyclic.jcyclic;

import org.apache.bcel.classfile.JavaClass;
import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class ClasspathFinderTest {

    @Test
    public void testGetJavaClasses() {
        ClasspathFinder classDependencies = new ClasspathFinder("se.cyclic.jcyclic");
        List<String> classNames = convertToClassNames(classDependencies.getJavaClassInformationList());
        verifyContainedClasses(classNames);
    }

    private List<String> convertToClassNames(Collection<JavaClassInformation> javaClasses) {
        List<String> classNames = new ArrayList<>();
        for (JavaClassInformation javaClass : javaClasses) {
            classNames.add(javaClass.getFullyQualifiedClassName());
        }
        return classNames;
    }

    private void verifyContainedClasses(Collection<String> classNames) {
        Assert.assertTrue(classNames.contains("se.cyclic.jcyclic.ClassDependencies"));
        Assert.assertTrue(classNames.contains("se.cyclic.jcyclic.ClassDependenciesTest"));
        Assert.assertTrue(classNames.contains("se.cyclic.jcyclic.ReferenceExtractingVisitor"));
        Assert.assertTrue(classNames.contains("se.cyclic.jcyclic.ClassDependencies"));
        Assert.assertTrue(classNames.contains("se.cyclic.jcyclic.ClasspathFinder"));
        Assert.assertTrue(classNames.contains("se.cyclic.jcyclic.ClassFinder"));
        Assert.assertTrue(classNames.contains("se.cyclic.jcyclic.Dependency"));
        Assert.assertTrue(classNames.contains("se.cyclic.jcyclic.DirectoryFinder"));
        Assert.assertTrue(classNames.contains("se.cyclic.jcyclic.DirectoryFinderTest"));
        Assert.assertTrue(classNames.contains("se.cyclic.jcyclic.JavaClassInformation"));
        Assert.assertTrue(classNames.contains("se.cyclic.jcyclic.TestClassFinder"));
        Assert.assertFalse(classNames.contains("se.cyclic.jcyclic.DirectoryFinderTest$InnerClassForTesting"));
    }

}