package se.cyclic.jcycles;

import org.apache.bcel.classfile.ConstantClass;
import org.apache.bcel.classfile.ConstantPool;
import org.apache.bcel.classfile.EmptyVisitor;
import org.apache.bcel.classfile.Method;
import org.apache.bcel.generic.Type;

import java.util.HashSet;
import java.util.Set;

/**
 * A bytecode visitor for extracting references (dependencies) to classes within a class.
 */
public class ReferenceExtractingVisitor extends EmptyVisitor {
    private final ConstantPool constantPool;
    private Set<String> deps = new HashSet<>();

    public ReferenceExtractingVisitor(ConstantPool constantPool) {
        this.constantPool = constantPool;
    }

    @Override
    public void visitMethod(Method obj) {
        final String returnType = Type.getReturnType(obj.getSignature()).toString();
        if (!returnType.equals("void")) {
            deps.add(convertSlashes(returnType));
        }
    }

    public void visitConstantClass(final ConstantClass obj) {
        deps.add(convertSlashes(obj.getBytes(constantPool)));
    }

    public Set<String> getDependencies() {
        return deps;
    }

    private String convertSlashes(final String fullyQualifiedClassNameWithSlashed) {
        return fullyQualifiedClassNameWithSlashed.replace('/', '.');
    }

}
