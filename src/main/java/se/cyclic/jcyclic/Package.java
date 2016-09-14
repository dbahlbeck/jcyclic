package se.cyclic.jcyclic;

/**
 * Representation of a Java package.
 */
public class Package {
    private final String name;

    public Package(final String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Package{name='" + name + '\'' + '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Package aPackage = (Package) o;

        return name.equals(aPackage.name);

    }

    @Override
    public int hashCode() {
        return name.hashCode();
    }

    boolean belongsTo(String basePackage) {
        return name.startsWith(basePackage);
    }
}
