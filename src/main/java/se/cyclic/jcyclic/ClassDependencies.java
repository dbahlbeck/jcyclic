package se.cyclic.jcyclic;

import org.jgrapht.DirectedGraph;
import org.jgrapht.alg.CycleDetector;
import org.jgrapht.alg.StrongConnectivityInspector;
import org.jgrapht.alg.cycle.TiernanSimpleCycles;
import org.jgrapht.graph.DefaultDirectedGraph;
import org.jgrapht.graph.DefaultEdge;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;

/**
 * A class that represents a graph of the package dependencies of some classes.
 * The classes that are analysed are provided by an optional instance of ClassFinder.
 * If a ClassFinder is not specified then the current classpath is used.
 * <br>
 * All classes that are found will form a part of the dependency graph, even if they
 * don't have any dependencies.
 */
public class ClassDependencies {
    private static final String NO_PACAKGE = "<no package>";
    private DirectedGraph<String, DefaultEdge> packageGraph = new DefaultDirectedGraph<>(DefaultEdge.class);
    private DirectedGraph<String, DefaultEdge> classGraph = new DefaultDirectedGraph<>(DefaultEdge.class);
    private ClassFinder classFinder;
    private String basePackage;

    /**
     * Create a cyclic dependency analyser for all the classes and package under the specified base package.
     *
     * @param classFinder something to help find classes on the classpath
     * @param basePackage the parent package for all classes to analyse
     */
    public ClassDependencies(ClassFinder classFinder, String basePackage) {
        this.classFinder = classFinder;
        this.basePackage = basePackage;
        analyseClasses();
    }

    /**
     * Creates and analyses the dependencies of classes on the classpath and under the specified base package.
     *
     * @param basePackage the parent package for all classes to analyse
     */
    public ClassDependencies(String basePackage) {
        this.classFinder = new ClasspathFinder(basePackage);
        this.basePackage = basePackage;
        analyseClasses();
    }

    private void analyseClasses() {
        getClassDependencyGraph(classFinder.getJavaClassInformationList());
    }

    private void getClassDependencyGraph(Collection<JavaClassInformation> classes) {
        packageGraph = new DefaultDirectedGraph<>(DefaultEdge.class);

        for (final JavaClassInformation from : classes) {
            Set<String> efferentDependencies = from.getReferencedClasses();
            String fromPkg = convertToPackage(from.getFullyQualifiedClassName());

            if (fromPkg.startsWith(basePackage)) {
                packageGraph.addVertex(fromPkg);
                classGraph.addVertex(from.getFullyQualifiedClassName());
                for (String to : efferentDependencies) {
                    if (to.startsWith(basePackage) || to.startsWith(NO_PACAKGE)) {
                        classGraph.addVertex(to);
                        classGraph.addEdge(from.getFullyQualifiedClassName(), to);
                        String toPkg = convertToPackage(to);
                        if (!fromPkg.equals(toPkg)) {
                            packageGraph.addVertex(toPkg);
                            packageGraph.addEdge(fromPkg, toPkg);
                        }
                    }

                }
            }
        }
    }

    /**
     * Returns a set of all the packages in the dependency graph that are involved in a cycle.
     *
     * @return a set of package names
     */
    public Set<String> getPackagesInCycles() {
        CycleDetector<String, DefaultEdge> cycleDetector = new CycleDetector<>(packageGraph);
        return cycleDetector.findCycles();
    }

    /**
     * Returns the number of packages in the dependency graph including those not involved in a dependency cycle.
     *
     * @return the number of packages
     */
    public int getNumberOfPackages() {
        return packageGraph.vertexSet().size();
    }

    /**
     * Returns the package from a fully qualified class name. For example, a.b.c.Clazz will be converted to
     * a.b.c.
     * @param fullyQualifiedClassName a fully qualified class name
     * @return the class's package
     */
    private String convertToPackage(String fullyQualifiedClassName) {
        final int lastDotIndex = fullyQualifiedClassName.lastIndexOf('.');
        if (lastDotIndex == -1) {
            return NO_PACAKGE;
        }
        return fullyQualifiedClassName.substring(0, lastDotIndex);
    }

    /**
     * Returns the ratio of number of packages in cycles to the total number of packages in the dependency graph.
     * @return the cycle ratio
     */
    public double getCycleToPackageRatio() {
        int numberOfPackages = getNumberOfPackages();
        int numberOfPackagesInCycles = getPackagesInCycles().size();
        
        if (numberOfPackages == 0) {
            return 0;
        }
        return (double) numberOfPackagesInCycles / numberOfPackages;
    }

    public List<List<String>> getPackageCycles() {
        return new TiernanSimpleCycles<>(packageGraph).findSimpleCycles();
    }

    public int getNumberOfCycles() {
        return new TiernanSimpleCycles<>(packageGraph).findSimpleCycles().size();
    }

    public List<Dependency> getEdges() {
        final List<Dependency> result = new ArrayList<>();
        final Set<DefaultEdge> defaultEdges = packageGraph.edgeSet();
        for (DefaultEdge defaultEdge : defaultEdges) {
            result.add(new Dependency(packageGraph.getEdgeSource(defaultEdge), packageGraph.getEdgeTarget(defaultEdge)));
        }
        return result;
    }

    public List<Set<String>> getStrongComponents() {
        StrongConnectivityInspector<String,DefaultEdge> strongConnectivityInspector = new StrongConnectivityInspector<>(classGraph);
        return strongConnectivityInspector.stronglyConnectedSets();
    }
}
