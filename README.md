# JCyclic Introduction
This API provides a simple a mechanism for finding cyclic dependencies in you classpath.
# Usage
From a test class instantiate the 'Cycles' class with a package name then call one of the methods that searches for dependencies:
```java
@Test
public void testForPackageCyclicDependencies() {
    ClassFinder finder = new DirectoryFinder(new File("build/classes/main"));
    ClassDependencies classDependencies = new ClassDependencies(classFinder, "se.cyclic.jcyclic");

    Set<String> cycles = classDependencies.getPackagesInCycles();
    List<List<String>> cycles = classDependencies.getPackageCycles();
}
```
# Coming soon
- Gradle plugin
  
    
