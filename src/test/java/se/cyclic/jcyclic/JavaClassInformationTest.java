package se.cyclic.jcyclic;


import org.junit.Assert;
import org.junit.Test;

public class JavaClassInformationTest {

    @Test
    public void testGetFullyQualifiedClassName() {
        JavaClassInformation classInformation = new JavaClassInformation("se.jcyclic.ClassName");
        Assert.assertEquals("se.jcyclic.ClassName", classInformation.getFullyQualifiedClassName());
    }

}