package se.cyclic.jcyclic;


import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.List;
import java.util.Set;

/**
 * Tests the cyclic dependency analysis on the classpath
 */
public class ClassDependenciesTest {

    private ClassDependencies classDependencies;

    @Before
    public void setUp() {
        final TestClassFinder classFinder = new TestClassFinder();
        classFinder.addDependency("se.cyclic.jcyclic.dummypackage.Foo", "se.cyclic.jcyclic.dummypackage2.Bar");
        classFinder.addDependency("se.cyclic.jcyclic.dummypackage2.Foo", "se.cyclic.jcyclic.dummypackage.Foo");
        classDependencies = new ClassDependencies(classFinder, "se.cyclic.jcyclic");


    }
    
    @Test
    public void testFindPackageCycles() {
        Set<String> cycles = classDependencies.getPackagesInCycles();
        Assert.assertTrue(cycles.contains("se.cyclic.jcyclic.dummypackage"));
        Assert.assertTrue(cycles.contains("se.cyclic.jcyclic.dummypackage2"));
        Assert.assertEquals(2, cycles.size());
    }
    
    @Test
    public void testGetPacakgeCycles() {
        List<List<String>> cycles = classDependencies.getPackageCycles();
        Assert.assertEquals(1, cycles.size());
        List<String> packageCycle = cycles.get(0);
        Assert.assertEquals(2, packageCycle.size());
        Assert.assertTrue(packageCycle.contains("se.cyclic.jcyclic.dummypackage"));
        Assert.assertTrue(packageCycle.contains("se.cyclic.jcyclic.dummypackage2"));
    }
    
    @Test
    public void testPackageCount() {
        Assert.assertEquals(2, classDependencies.getNumberOfPackages());
    }
    
    @Test
    public void testCycleToPackageRatio() {
        Assert.assertEquals(1,  classDependencies.getCycleToPackageRatio(), 0.0);
    } 
}