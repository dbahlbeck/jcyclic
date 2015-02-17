package se.cyclic.jcyclic;

import org.junit.Assert;
import org.junit.Test;

import java.io.File;
import java.util.List;


public class DirectoryFinderTest {
    @Test
    public void testGetMainClassNames() {
        ClassFinder finder = new DirectoryFinder(new File("build/classes/main"));
        List<String> classNames = finder.getClassList();
        Assert.assertTrue(classNames.contains("se.cyclic.jcyclic.ReferenceExtractingVisitor"));
        Assert.assertTrue(classNames.contains("se.cyclic.jcyclic.ClassDependencies"));
        Assert.assertTrue(classNames.contains("se.cyclic.jcyclic.ClassFinder"));
        Assert.assertTrue(classNames.contains("se.cyclic.jcyclic.ClasspathFinder"));
        Assert.assertTrue(classNames.contains("se.cyclic.jcyclic.DirectoryFinder"));

        Assert.assertEquals(5, classNames.size());
    }
    
    @Test
    public void testGetTestClassNames() {
        ClassFinder finder = new DirectoryFinder(new File("build/classes/test"));
        List<String> classNames = finder.getClassList();
        Assert.assertTrue(classNames.contains("se.cyclic.jcyclic.dummypackage.JustForTesting"));
        Assert.assertTrue(classNames.contains("se.cyclic.jcyclic.dummypackage2.JustForTesting2"));
        Assert.assertTrue(classNames.contains("se.cyclic.jcyclic.ClassFinderTest"));
        Assert.assertTrue(classNames.contains("se.cyclic.jcyclic.DirectoryFinderTest"));
        Assert.assertTrue(classNames.contains("se.cyclic.jcyclic.ClassDependenciesTest"));

        Assert.assertEquals(5, classNames.size());
    }
    
    // DirectoryFinder should ignore this
    private class InnerClassForTesting {

    }
}
