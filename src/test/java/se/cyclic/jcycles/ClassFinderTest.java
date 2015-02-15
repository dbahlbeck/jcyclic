package se.cyclic.jcycles;

import org.junit.Assert;
import org.junit.Test;

import java.util.List;

public class ClassFinderTest {

    @Test
    public void testGetClassNames() {
        ClassFinder classDependencies = new ClassFinder("se.cyclic.jcycles");
        List<String> classNames = classDependencies.getClassNamesFromSystemClassLoader();
        Assert.assertTrue(classNames.contains("se.cyclic.jcycles.ClassDependencies"));
        Assert.assertTrue(classNames.contains("se.cyclic.jcycles.ClassDependenciesTest"));
        Assert.assertTrue(classNames.contains("se.cyclic.jcycles.ReferenceExtractingVisitor"));
        Assert.assertTrue(classNames.contains("se.cyclic.jcycles.dummypackage.JustForTesting"));
        Assert.assertTrue(classNames.contains("se.cyclic.jcycles.dummypackage2.JustForTesting2"));
        Assert.assertTrue(classNames.contains("se.cyclic.jcycles.ClassDependencies"));
        Assert.assertTrue(classNames.contains("se.cyclic.jcycles.ClassFinder"));

        Assert.assertEquals(7, classNames.size());
    }

}