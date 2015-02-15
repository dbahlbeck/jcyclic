package se.cyclic.jcycles;


import org.jgrapht.DirectedGraph;
import org.jgrapht.graph.DefaultEdge;
import org.junit.Assert;
import org.junit.Test;

import java.util.List;
import java.util.Set;

public class CyclesTest {

    @Test
    public void testGetClassNames() {
        Cycles cycles = new Cycles("se.cyclic.jcycles");
        List<String> classNames = cycles.getClassNames();
        Assert.assertTrue(classNames.contains("se.cyclic.jcycles.Cycles"));
        Assert.assertTrue(classNames.contains("se.cyclic.jcycles.Dependency"));
        Assert.assertTrue(classNames.contains("se.cyclic.jcycles.CyclesTest"));
        Assert.assertTrue(classNames.contains("se.cyclic.jcycles.ReferenceExtractingVisitor"));
        Assert.assertTrue(classNames.contains("se.cyclic.jcycles.dummypackage.JustForTesting"));
        Assert.assertTrue(classNames.contains("se.cyclic.jcycles.dummypackage2.JustForTesting2"));

        Assert.assertEquals(6, classNames.size());
    }

    @Test
    public void testGetClassDependencies() {
        Cycles cycles = new Cycles("se.cyclic.jcycles");
        Set<Dependency> deps = cycles.getClassDependencies();
        Assert.assertTrue(deps.contains(new Dependency("se.cyclic.jcycles.CyclesTest","se.cyclic.jcycles.Cycles")));
        Assert.assertTrue(deps.contains(new Dependency("se.cyclic.jcycles.dummypackage.JustForTesting","se.cyclic.jcycles.dummypackage2.JustForTesting2")));
        Assert.assertTrue(deps.contains(new Dependency("se.cyclic.jcycles.dummypackage2.JustForTesting2", "se.cyclic.jcycles.dummypackage.JustForTesting")));
        Assert.assertEquals(6, deps.size());
    }

    @Test
    public void testGetPackages() {
        Cycles cycles = new Cycles("se.cyclic.jcycles");
        Set<String> packages = cycles.getPackages();
        Assert.assertTrue(packages.contains("se.cyclic.jcycles"));
        Assert.assertTrue(packages.contains("se.cyclic.jcycles.dummypackage"));
        Assert.assertTrue(packages.contains("se.cyclic.jcycles.dummypackage2"));
        Assert.assertEquals(3, packages.size());
    }

    @Test
    public void testGetPackageDependencies() {
        Cycles cycles = new Cycles("se.cyclic.jcycles");
        Set<Dependency> packageDependencies = cycles.getPackageDependencies();
        Assert.assertEquals(2, packageDependencies.size());

    }

    @Test
    public void testFindPackageCycles() {
        Cycles classes = new Cycles("se.cyclic.jcycles");
        List<List<String>> cycles = classes.getPackageCycles();
        Assert.assertEquals(1, cycles.size());
    }

    @Test
    public void testClassDependencyGraph() {
        Cycles cycles = new Cycles("se.cyclic.jcycles");
        DirectedGraph<String, DefaultEdge> graph = cycles.getClassDependencyGraph();
        Set<String> vertexes = graph.vertexSet();
        Assert.assertTrue(vertexes.contains("se.cyclic.jcycles.Cycles"));
        Assert.assertTrue(vertexes.contains("se.cyclic.jcycles.Dependency"));
        Assert.assertTrue(vertexes.contains("se.cyclic.jcycles.CyclesTest"));
        Assert.assertTrue(vertexes.contains("se.cyclic.jcycles.ReferenceExtractingVisitor"));
        Assert.assertTrue(vertexes.contains("se.cyclic.jcycles.dummypackage.JustForTesting"));
        Assert.assertTrue(vertexes.contains("se.cyclic.jcycles.dummypackage2.JustForTesting2"));

    }
}