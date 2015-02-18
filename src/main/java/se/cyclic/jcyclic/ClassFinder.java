package se.cyclic.jcyclic;

import org.apache.bcel.classfile.JavaClass;

import java.util.List;


public interface ClassFinder {
    List<JavaClass> getJavaClassList();
}
