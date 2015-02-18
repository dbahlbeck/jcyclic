package se.cyclic.jcyclic;


import org.apache.bcel.Repository;
import org.apache.bcel.classfile.JavaClass;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.filefilter.DirectoryFileFilter;
import org.apache.commons.io.filefilter.SuffixFileFilter;

import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class DirectoryFinder implements ClassFinder {
    private File directory;

    public DirectoryFinder(File directory) {
        this.directory = directory;
    }

    @Override
    public List<JavaClass> getJavaClassList() {
        Collection<File> files = FileUtils.listFiles(directory, new SuffixFileFilter("class"), DirectoryFileFilter.DIRECTORY);
        try {
            List<JavaClass> javaClasses = new ArrayList<>();
            for (File file : files) {
                String fullyQualifiedClassName = getClassNameFromFile(file);
                if (!fullyQualifiedClassName.contains("$")) {
                    javaClasses.add(Repository.getRepository().loadClass(fullyQualifiedClassName));
                }
            }
            return javaClasses;
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    
    
    private String getClassNameFromFile(File file) {
        String classNameWithSlashes = file.getAbsolutePath().replace(directory.getAbsolutePath(), "");
        String classNameWithDots = classNameWithSlashes.replace(File.separator, ".");
        if (classNameWithDots.substring(0, 1).equals(".")) {
            classNameWithDots = classNameWithDots.substring(1);
        }
        if (classNameWithDots.endsWith(".class")) {
            classNameWithDots = classNameWithDots.substring(0, classNameWithDots.length() - 6);
        }
        return classNameWithDots;
    }
}
