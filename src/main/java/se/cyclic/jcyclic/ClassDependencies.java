package se.cyclic.jcyclic;

import org.apache.bcel.Repository;
import org.apache.bcel.classfile.ConstantPool;
import org.apache.bcel.classfile.DescendingVisitor;
import org.apache.bcel.classfile.JavaClass;
import org.jgrapht.DirectedGraph;
import org.jgrapht.alg.cycle.TarjanSimpleCycles;
import org.jgrapht.graph.DefaultDirectedGraph;
import org.jgrapht.graph.DefaultEdge;

import java.util.List;
import java.util.Set;

public class ClassDependencies {
    private DirectedGraph<String, DefaultEdge> classGraph = new DefaultDirectedGraph<>(DefaultEdge.class);
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

    public void analyseClasses() {
        getClassDependencyGraph(classFinder.getClassList());
    }


    private void getClassDependencyGraph(List<String> classes) {
        classGraph = new DefaultDirectedGraph<>(DefaultEdge.class);
        packageGraph = new DefaultDirectedGraph<>(DefaultEdge.class);

        try {
            for (String from : classes) {
                JavaClass javaClass = Repository.lookupClass(from);
                ConstantPool constantPool = javaClass.getConstantPool();
                ReferenceExtractingVisitor visitor = new ReferenceExtractingVisitor(constantPool);
                DescendingVisitor descendingVisitor = new DescendingVisitor(javaClass, visitor);
                descendingVisitor.visit();
                Set<String> efferentDependencies = visitor.getDependencies();
                for (String to : efferentDependencies) {
                    if (to.startsWith(basePackage)) {
                        if (!to.equals(from)) {
                            classGraph.addVertex(from);
                            classGraph.addVertex(to);
                            classGraph.addEdge(from, to);
                        }
                        String fromPkg = convertToPackage(from);
                        String toPkg = convertToPackage(to);
                        if (!fromPkg.equals(toPkg)) {
                            packageGraph.addVertex(fromPkg);
                            packageGraph.addVertex(toPkg);
                            packageGraph.addEdge(fromPkg, toPkg);
                        }
                    }

                }
            }
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }

    }


    public List<List<String>> getPackageCycles() {
        return getCycles(packageGraph);
    }


    public List<List<String>> getClassCycles() {
        return getCycles(classGraph);
    }

    private List<List<String>> getCycles(DirectedGraph<String, DefaultEdge> graph) {
        TarjanSimpleCycles<String, DefaultEdge> cycles = new TarjanSimpleCycles<>(graph);
        return cycles.findSimpleCycles();
    }

    private String convertToPackage(String input) {
        final int lastDotIndex = input.lastIndexOf('.');
        return input.substring(0, lastDotIndex);
    }
}
