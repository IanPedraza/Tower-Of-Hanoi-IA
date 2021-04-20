package smai.domain;

public abstract class Operator {
    
    public abstract Successor apply(State state);
    public abstract String getName();

    @Override
    public String toString() {
        return getName();
    }
    
}
