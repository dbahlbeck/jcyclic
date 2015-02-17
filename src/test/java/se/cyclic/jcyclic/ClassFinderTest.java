package se.cyclic.jcyclic;

import org.junit.Assert;
import org.junit.Test;

import java.util.List;

public class ClassFinderTest {

    @Test
    public void testGetClassNames() {
        ClassFinder classDependencies = new ClassFinder("se.cyclic.jcyclic");
        List<String> classNames = classDependencies.getClassNamesFromSystemClassLoader();
        Assert.assertTrue(classNames.contains("se.cyclic.jcyclic.ClassDependencies"));
        Assert.assertTrue(classNames.contains("se.cyclic.jcyclic.ClassDependenciesTest"));
        Assert.assertTrue(classNames.contains("se.cyclic.jcyclic.ReferenceExtractingVisitor"));
        Assert.assertTrue(classNames.contains("se.cyclic.jcyclic.dummypackage.JustForTesting"));
        Assert.assertTrue(classNames.contains("se.cyclic.jcyclic.dummypackage2.JustForTesting2"));
        Assert.assertTrue(classNames.contains("se.cyclic.jcyclic.ClassDependencies"));
        Assert.assertTrue(classNames.contains("se.cyclic.jcyclic.ClassFinder"));

        Assert.assertEquals(7, classNames.size());
    }

}