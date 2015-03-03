package se.cyclic.jcyclic;

import org.apache.bcel.classfile.ConstantPool;
import org.apache.bcel.classfile.DescendingVisitor;
import org.apache.bcel.classfile.JavaClass;
import org.jgrapht.DirectedGraph;
import org.jgrapht.alg.CycleDetector;
import org.jgrapht.alg.cycle.TarjanSimpleCycles;
import org.jgrapht.graph.DefaultDirectedGraph;
import org.jgrapht.graph.DefaultEdge;

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
    private DirectedGraph<String, DefaultEdge> packageGraph = new DefaultDirectedGraph<>(DefaultEdge.class);
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
        getClassDependencyGraph(classFinder.getJavaClassList());
    }


    private void getClassDependencyGraph(List<JavaClass> classes) {
        packageGraph = new DefaultDirectedGraph<>(DefaultEdge.class);

        for (JavaClass from : classes) {

            ConstantPool constantPool = from.getConstantPool();
            ReferenceExtractingVisitor visitor = new ReferenceExtractingVisitor(constantPool);
            DescendingVisitor descendingVisitor = new DescendingVisitor(from, visitor);
            descendingVisitor.visit();
            Set<String> efferentDependencies = visitor.getDependencies();
            String fromPkg = convertToPackage(from.getClassName());
            packageGraph.addVertex(fromPkg);
            for (String to : efferentDependencies) {
                if (to.startsWith(basePackage)) {
                    String toPkg = convertToPackage(to);
                    if (!fromPkg.equals(toPkg)) {
                        packageGraph.addVertex(toPkg);
                        packageGraph.addEdge(fromPkg, toPkg);
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

    private String convertToPackage(String input) {
        final int lastDotIndex = input.lastIndexOf('.');
        return input.substring(0, lastDotIndex);
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
}
