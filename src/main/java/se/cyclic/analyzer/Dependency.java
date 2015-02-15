package se.cyclic.analyzer;

public class Dependency {
    private String fromClass;
    private String toClass;

    Dependency(String fromClass, String toClass) {
        this.fromClass = fromClass;
        this.toClass = toClass;
        if (fromClass.equals(toClass)) {
            throw new RuntimeException("Cannot have dependency to oneself");
        }
    }

    public String from() {
        return fromClass;
    }

    public String to() {
        return toClass;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Dependency that = (Dependency) o;

        return fromClass.equals(that.fromClass) && toClass.equals(that.toClass);

    }

    @Override
    public int hashCode() {
        int result = fromClass.hashCode();
        result = 31 * result + toClass.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "Dependency{" +
                "fromClass='" + fromClass + '\'' +
                ", toClass='" + toClass + '\'' +
                '}';
    }
}
