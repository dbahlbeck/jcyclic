package se.cyclic.jcyclic;


import com.google.common.collect.Iterables;
import org.junit.Assert;
import org.junit.Test;

import java.util.List;

public class ClassDependenciesTest {
    private ClasspathFinder classFinder = new ClasspathFinder("se.cyclic.jcyclic");
    private ClassDependencies classDependencies = new ClassDependencies(classFinder, "se.cyclic.jcyclic");

    @Test
    public void testFindPackageCycles() {
        List<List<String>> cycles = classDependencies.getPackageCycles();
        List<String> cycle = Iterables.getOnlyElement(cycles);
        Assert.assertEquals(2, cycle.size());

    }

    @Test
    public void testFindClassCycles() {
        List<List<String>> classCycles = classDependencies.getClassCycles();
        List<String> cycle = Iterables.getOnlyElement(classCycles);
        Assert.assertEquals(2, cycle.size());
        Assert.assertTrue(cycle.contains("se.cyclic.jcyclic.dummypackage.JustForTesting"));
        Assert.assertTrue(cycle.contains("se.cyclic.jcyclic.dummypackage2.JustForTesting2"));
    }
}