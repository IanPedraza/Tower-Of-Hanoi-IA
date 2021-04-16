package smai.domain;

public class Instance {
    
    private State initialState, finalState;
    private Operator[] operators;
    
    public Instance() {
        this.initialState = null;
        this.finalState = null;
        this.operators = new Operator[0];
    }

    public Instance(State initialState, State targetState, Operator[] operators) {
        this.initialState = initialState;
        this.finalState = targetState;
        this.operators = operators;
    }

    public boolean isFinalState(State state) {
        return state.equals(finalState);
    }
    
    public State getInitialState() {
        return initialState;
    }

    public void setInitialState(State initialState) {
        this.initialState = initialState;
    }

    public State getFinalState() {
        return finalState;
    }

    public void setFinalState(State finalState) {
        this.finalState = finalState;
    }

    public Operator[] getOperators() {
        return operators;
    }

    public void setOperators(Operator[] operators) {
        this.operators = operators;
    }
    
}
