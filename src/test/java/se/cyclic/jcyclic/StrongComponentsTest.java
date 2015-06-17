package se.cyclic.jcyclic;

import org.junit.Assert;
import org.junit.Test;

import java.util.List;
import java.util.Set;

public class StrongComponentsTest {
    @Test
    public void testStrongComponentDetection() {
        final TestClassFinder classFinder = new TestClassFinder();
        classFinder.addDependency("a.b.c.d.X", "a.b.c.d.Z");
        classFinder.addDependency("a.b.c.d.Y", "a.b.c.d.X");
        classFinder.addDependency("a.b.c.d.Z", "a.b.c.d.Y");
        classFinder.addDependency("a.b.c.d.Y", "a.b.c.d.Z");
        classFinder.addDependency("a.b.c.d.A", "a.b.c.d.B");
        classFinder.addDependency("a.b.c.d.B", "a.b.c.d.A");
        ClassDependencies dependencies = new ClassDependencies(classFinder, "a.b.c");
        List<Set<String>> strongComponents = dependencies.getStrongComponents();
     
        // Two components
        Assert.assertEquals(2, strongComponents.size());
    }
}
