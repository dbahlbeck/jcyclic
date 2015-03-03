package se.cyclic.jcyclic;

import org.apache.bcel.classfile.JavaClass;

import java.util.List;

/**
 * An interface used for finding classes for analysis. 
 */
public interface ClassFinder {
    /**
     * Get a list of classes for analysis. 
     * @return a list of JavaClass instances. I should probably do this better but currently this relies on the awesome 
     * apache BCEL library.
     */
    List<JavaClass> getJavaClassList();
}
