package se.cyclic.analyzer;


import org.junit.Assert;
import org.junit.Test;

import java.util.List;
import java.util.Set;

public class CyclesTest {

    @Test
    public void testGetClassNames() {
        Cycles cycles = new Cycles("se.cyclic.analyzer");
        List<String> classNames = cycles.getClassNames();
        Assert.assertTrue(classNames.contains("se.cyclic.analyzer.Cycles"));
        Assert.assertTrue(classNames.contains("se.cyclic.analyzer.Dependency"));
        Assert.assertTrue(classNames.contains("se.cyclic.analyzer.CyclesTest"));
        Assert.assertTrue(classNames.contains("se.cyclic.analyzer.ReferenceExtractingVisitor"));
        Assert.assertTrue(classNames.contains("se.cyclic.analyzer.dummypackage.JustForTesting"));
        Assert.assertTrue(classNames.contains("se.cyclic.analyzer.dummypackage2.JustForTesting2"));

        Assert.assertEquals(6, classNames.size());
    }

    @Test
    public void testGetClassDependencies() {
        Cycles cycles = new Cycles("se.cyclic.analyzer");
        Set<Dependency> deps = cycles.getClassDependencies();
        Assert.assertTrue(deps.contains(new Dependency("se.cyclic.analyzer.CyclesTest","se.cyclic.analyzer.Cycles")));
        Assert.assertTrue(deps.contains(new Dependency("se.cyclic.analyzer.dummypackage.JustForTesting","se.cyclic.analyzer.dummypackage2.JustForTesting2")));
        Assert.assertTrue(deps.contains(new Dependency("se.cyclic.analyzer.dummypackage2.JustForTesting2", "se.cyclic.analyzer.dummypackage.JustForTesting")));
        Assert.assertEquals(6, deps.size());
    }

    @Test
    public void testGetPackages() {
        Cycles cycles = new Cycles("se.cyclic.analyzer");
        Set<String> packages = cycles.getPackages();
        Assert.assertTrue(packages.contains("se.cyclic.analyzer"));
        Assert.assertTrue(packages.contains("se.cyclic.analyzer.dummypackage"));
        Assert.assertTrue(packages.contains("se.cyclic.analyzer.dummypackage2"));
        Assert.assertEquals(3, packages.size());
    }

    @Test
    public void testGetPackageDependencies() {
        Cycles cycles = new Cycles("se.cyclic.analyzer");
        Set<Dependency> packageDependencies = cycles.getPackageDependencies();
        Assert.assertEquals(2, packageDependencies.size());

    }

    @Test
    public void testFindPackageCycles() {
        Cycles classes = new Cycles("se.cyclic.analyzer");
        List<List<String>> cycles = classes.getPackageCycles();
        Assert.assertEquals(1, cycles.size());
    }
}