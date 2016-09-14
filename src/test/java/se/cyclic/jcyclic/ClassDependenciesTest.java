package se.cyclic.jcyclic;


import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.List;
import java.util.Set;

/**
 * Tests the cyclic dependency analysis on the classpath.
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
        Set<Package> cycles = classDependencies.getPackagesInCycles();
        Assert.assertTrue(cycles.contains(new Package("se.cyclic.jcyclic.dummypackage")));
        Assert.assertTrue(cycles.contains(new Package("se.cyclic.jcyclic.dummypackage2")));
        Assert.assertEquals(2, cycles.size());
    }
    
    @Test
    public void testExcludePackages() {
        final TestClassFinder classFinder = new TestClassFinder();
        classFinder.addDependency("a.b.c.d.X", "a.b.wrong.d.e.Y");
        classFinder.addDependency("a.b.wrong.d.e.Y", "a.b.c.d.X");
        classFinder.addDependency("a.b.c.d.X", "a.b.c.d.e.Y");
        classFinder.addDependency("a.b.c.d.e.Y", "a.b.c.d.X");
        ClassDependencies dependencies = new ClassDependencies(classFinder, "a.b.c");

        List<Dependency> deps = dependencies.getEdges();
        Assert.assertTrue(deps.contains(new Dependency(new Package("a.b.c.d"), new Package("a.b.c.d.e"))));
        Assert.assertTrue(deps.contains(new Dependency(new Package("a.b.c.d.e"), new Package("a.b.c.d"))));
        Assert.assertEquals(2, deps.size());
    }

    @Test
    public void testGetPackageCycles() {
        List<List<Package>> cycles = classDependencies.getPackageCycles();
        Assert.assertEquals(1, cycles.size());
        List<Package> packageCycle = cycles.get(0);
        Assert.assertEquals(2, packageCycle.size());
        Assert.assertTrue(packageCycle.contains(new Package("se.cyclic.jcyclic.dummypackage")));
        Assert.assertTrue(packageCycle.contains(new Package("se.cyclic.jcyclic.dummypackage2")));
    }
    
    @Test
    public void testPackageCount() {
        Assert.assertEquals(2, classDependencies.getNumberOfPackages());
    }
    
    @Test
    public void testCycleToPackageRatio() {
        Assert.assertEquals(1,  classDependencies.getCycleToPackageRatio(), 0.0);
    } 
    
    @Test
    public void testCountCycles() {
        Assert.assertEquals(1, classDependencies.getNumberOfCycles());
        
    }
    
    @Test
    public void testNoPackageNoIncluded() {
        final TestClassFinder classFinder = new TestClassFinder();
        classFinder.addDependency("Foo", "se.cyclic.jcyclic.dummypackage2.Bar");
        classFinder.addDependency("se.cyclic.jcyclic.dummypackage2.Bar", "Foo");
        ClassDependencies classDependencies = new ClassDependencies(classFinder, "se.cyclic.jcyclic");

        List<List<Package>> cycles = classDependencies.getPackageCycles();
        // No cycles because we are analysing a certain package
        Assert.assertEquals(0, cycles.size());
    }    
    
    @Test
    public void testNoPackageIncluded() {
        final TestClassFinder classFinder = new TestClassFinder();
        classFinder.addDependency("Foo", "se.cyclic.jcyclic.dummypackage2.Bar");
        classFinder.addDependency("se.cyclic.jcyclic.dummypackage2.Bar", "Foo");
        ClassDependencies classDependencies = new ClassDependencies(classFinder, "");

        List<List<Package>> cycles = classDependencies.getPackageCycles();
        // No cycles because we are analysing a certain package
        Assert.assertEquals(1, cycles.size());
    }
}