package se.cyclic.jcyclic;

import org.junit.Assert;
import org.junit.Test;

import java.util.List;

public class ClassFinderTest {

    @Test
    public void testGetClassNames() {
        ClasspathFinder classDependencies = new ClasspathFinder("se.cyclic.jcyclic");
        List<String> classNames = classDependencies.getClassList();
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