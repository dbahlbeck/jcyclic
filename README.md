# JCyclic Introduction
This API provides a simple a mechanism for finding cyclic dependencies in you classpath.
# Usage
The basic usage is this:
1. Grab something that can find your classes (either from class files in a Directory or from the classpath/classloader)
2. Instantiate ClassDependencies with that 'class finder' and a base package.
3. Now you can query the results.

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
  
    
