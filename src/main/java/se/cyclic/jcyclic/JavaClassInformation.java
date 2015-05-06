package se.cyclic.jcyclic;


import org.apache.bcel.classfile.ConstantPool;
import org.apache.bcel.classfile.DescendingVisitor;
import org.apache.bcel.classfile.JavaClass;

import java.util.HashSet;
import java.util.Set;

public class JavaClassInformation {
    private String fullyQualifiedClassName;
    private Set<String> referencedClasses;

    public JavaClassInformation(String className) {
        this.fullyQualifiedClassName = className;
        this.referencedClasses = new HashSet<>();
    }

    public JavaClassInformation(JavaClass javaClass) {
        this.fullyQualifiedClassName = javaClass.getClassName();

        ConstantPool constantPool = javaClass.getConstantPool();
        ReferenceExtractingVisitor visitor = new ReferenceExtractingVisitor(constantPool);
        DescendingVisitor descendingVisitor = new DescendingVisitor(javaClass, visitor);
        descendingVisitor.visit();
        referencedClasses = visitor.getDependencies();

    }

    public String getFullyQualifiedClassName() {
        return fullyQualifiedClassName;
    }

    public Set<String> getReferencedClasses() {
        return referencedClasses;
    }

    public void addDependencyToClass(String className) {
        referencedClasses.add(className);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        JavaClassInformation that = (JavaClassInformation) o;

        return fullyQualifiedClassName.equals(that.fullyQualifiedClassName) && referencedClasses.equals(that.referencedClasses);

    }

    @Override
    public int hashCode() {
        int result = fullyQualifiedClassName.hashCode();
        result = 31 * result + referencedClasses.hashCode();
        return result;
    }
}
