# JCyclic Introduction
This API provides a simple a mechanism for finding cyclic dependencies in you classpath.
# Usage
From a test class instantiate the 'Cycles' class with a package name then call one of the methods that searches for dependencies:
```java
    @Test
    public void testForPackageCyclicDependencies() {
        Cycles classDependencies = new Cycles('org.foo');
        Assert.assertEquals(0, classDependencies.getPackageCycles());
    }  
    
    @Test
    public void testForClassCyclicDependencies() {
        Cycles classDependencies = new Cycles('org.foo');
        Assert.assertEquals(0, classDependencies.getClassCycles());
    }  
```
# Coming soon
- Better performance
- Class cycles
- Exclusions for classes and packages
- Gradle plugin
  
    
