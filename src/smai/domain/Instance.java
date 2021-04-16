package smai.domain;

public class Instance {
    
    private State initialState, targetState;
    private Operator[] operators;
    
    public Instance() {
        this.initialState = null;
        this.targetState = null;
        this.operators = new Operator[0];
    }

    public Instance(State initialState, State targetState, Operator[] operators) {
        this.initialState = initialState;
        this.targetState = targetState;
        this.operators = operators;
    }

    public boolean isFinalState(State state) {
        return state.equals(targetState);
    }
    
    public State getInitialState() {
        return initialState;
    }

    public void setInitialState(State initialState) {
        this.initialState = initialState;
    }

    public State getTargetState() {
        return targetState;
    }

    public void setTargetState(State targetState) {
        this.targetState = targetState;
    }

    public Operator[] getOperators() {
        return operators;
    }

    public void setOperators(Operator[] operators) {
        this.operators = operators;
    }
    
}
