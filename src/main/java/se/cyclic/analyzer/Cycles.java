package se.cyclic.analyzer;

import com.google.common.collect.ImmutableSet;
import com.google.common.reflect.ClassPath;
import org.apache.bcel.Repository;
import org.apache.bcel.classfile.ConstantPool;
import org.apache.bcel.classfile.DescendingVisitor;
import org.apache.bcel.classfile.JavaClass;
import org.jgrapht.DirectedGraph;
import org.jgrapht.alg.cycle.TarjanSimpleCycles;
import org.jgrapht.graph.DefaultDirectedGraph;
import org.jgrapht.graph.DefaultEdge;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Cycles {
    private final String basePackage;

    public Cycles(String basePackage) {
        this.basePackage = basePackage;
    }

    List<String> getClassNames() {
        final ImmutableSet<ClassPath.ClassInfo> allClasses;
        try {
            allClasses = ClassPath.from(ClassLoader.getSystemClassLoader()).getAllClasses();
            final List<String> strings = new ArrayList<String>();
            for (ClassPath.ClassInfo allClass : allClasses) {
                if (allClass.getPackageName().startsWith(basePackage))
                    strings.add(allClass.getName());
            }
            return strings;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public Set<Dependency> getClassDependencies() {
        try {
            Set<Dependency> classDependencies = new HashSet<Dependency>();
            for (String className : getClassNames()) {
                JavaClass javaClass = Repository.lookupClass(className);
                ConstantPool constantPool = javaClass.getConstantPool();
                ReferenceExtractingVisitor visitor = new ReferenceExtractingVisitor(constantPool);
                DescendingVisitor descendingVisitor = new DescendingVisitor(javaClass, visitor);
                descendingVisitor.visit();
                Set<String> efferentDependencies = visitor.getDependencies();
                for (String efferentDependency : efferentDependencies) {
                    if (efferentDependency.startsWith(basePackage) && !efferentDependency.equals(className))
                        classDependencies.add(new Dependency(className, efferentDependency));
                }
            }

            return classDependencies;
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public Set<String> getPackages() {
        final Set<String> packages = new HashSet<String>();
        for (String s : getClassNames()) {
            packages.add(convertToPackage(s));
        }

        return packages;
    }

    public Set<Dependency> getPackageDependencies() {
        Set<Dependency> deps = new HashSet<Dependency>();
        for (Dependency dependency : getClassDependencies()) {
            String fromClass = dependency.from();
            String fromPkg = convertToPackage(fromClass);
            String toClass = dependency.to();
            String toPkg = convertToPackage(toClass);
            if (!fromPkg.equals(toPkg)) {
                deps.add(new Dependency(fromPkg, toPkg));
            }
        }
        return deps;
    }

    public List<List<String>> getPackageCycles() {
        DirectedGraph<String, DefaultEdge> graph = new DefaultDirectedGraph<String, DefaultEdge>(DefaultEdge.class);
        for (final Dependency dependency : getPackageDependencies()) {
            graph.addVertex(dependency.from());
            graph.addVertex(dependency.to());
            graph.addEdge(dependency.from(), dependency.to());
        }
        TarjanSimpleCycles<String, DefaultEdge> cycles = new TarjanSimpleCycles<String, DefaultEdge>(graph);
        return cycles.findSimpleCycles();
    }

    private String convertToPackage(String input) {
        final int lastDotIndex = input.lastIndexOf('.');
        return input.substring(0, lastDotIndex);
    }


}
