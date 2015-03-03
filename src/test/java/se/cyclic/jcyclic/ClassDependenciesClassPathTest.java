package se.cyclic.jcyclic;


import org.junit.Assert;
import org.junit.Test;

import java.util.Set;

/**
 * Tests the cyclic dependency analysis on the classpath
 */
public class ClassDependenciesClassPathTest {
    private ClasspathFinder classFinder = new ClasspathFinder("se.cyclic.jcyclic");
    private ClassDependencies classDependencies = new ClassDependencies(classFinder, "se.cyclic.jcyclic");

    @Test
    public void testFindPackageCycles() {
        Set<String> cycles = classDependencies.getPackagesInCycles();
        Assert.assertTrue(cycles.contains("se.cyclic.jcyclic.dummypackage"));
        Assert.assertTrue(cycles.contains("se.cyclic.jcyclic.dummypackage2"));
        Assert.assertEquals(2, cycles.size());
    }
    
    @Test
    public void testPackageCount() {
        Assert.assertEquals(3, classDependencies.getNumberOfPackages());
    }
    
    @Test
    public void testCycleToPackageRatio() {
        Assert.assertEquals(2/3d,  classDependencies.getCycleToPackageRatio(), 0.0);
    } 
}