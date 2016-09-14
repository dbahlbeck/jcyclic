package se.cyclic.jcyclic;

public class Dependency {
    private final Package from;
    private final Package to;

    public Dependency(Package from, Package to) {
        this.from = from;
        this.to = to;
    }

    public Package getFrom() {
        return from;
    }

    public Package getTo() {
        return to;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Dependency that = (Dependency) o;

        if (from != null ? !from.equals(that.from) : that.from != null) return false;
        return !(to != null ? !to.equals(that.to) : that.to != null);

    }

    @Override
    public int hashCode() {
        int result = from != null ? from.hashCode() : 0;
        result = 31 * result + (to != null ? to.hashCode() : 0);
        return result;
    }
}
