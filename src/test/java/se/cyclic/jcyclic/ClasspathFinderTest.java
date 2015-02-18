package se.cyclic.jcyclic;

import org.apache.bcel.classfile.JavaClass;
import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

public class ClasspathFinderTest {

    @Test
    public void testGetJavaClasses() {
        ClasspathFinder classDependencies = new ClasspathFinder("se.cyclic.jcyclic");
        List<String> classNames = convertToClassNames(classDependencies.getJavaClassList());
        verifyContainedClasses(classNames);
    }

    private List<String> convertToClassNames(List<JavaClass> javaClasses) {
        List<String> classNames = new ArrayList<>();
        for (JavaClass javaClass : javaClasses) {
            classNames.add(javaClass.getClassName());
        }
        return classNames;
    }

    private void verifyContainedClasses(List<String> classNames) {
        Assert.assertTrue(classNames.contains("se.cyclic.jcyclic.ClassDependencies"));
        Assert.assertTrue(classNames.contains("se.cyclic.jcyclic.ClassDependenciesTest"));
        Assert.assertTrue(classNames.contains("se.cyclic.jcyclic.ReferenceExtractingVisitor"));
        Assert.assertTrue(classNames.contains("se.cyclic.jcyclic.dummypackage.JustForTesting"));
        Assert.assertTrue(classNames.contains("se.cyclic.jcyclic.dummypackage2.JustForTesting2"));
        Assert.assertTrue(classNames.contains("se.cyclic.jcyclic.ClassDependencies"));
        Assert.assertTrue(classNames.contains("se.cyclic.jcyclic.ClasspathFinder"));
        Assert.assertTrue(classNames.contains("se.cyclic.jcyclic.ClassFinder"));
        Assert.assertTrue(classNames.contains("se.cyclic.jcyclic.DirectoryFinder"));
        Assert.assertTrue(classNames.contains("se.cyclic.jcyclic.DirectoryFinderTest"));
        Assert.assertFalse(classNames.contains("se.cyclic.jcyclic.DirectoryFinderTest$InnerClassForTesting"));
        Assert.assertEquals(10, classNames.size());
    }

}