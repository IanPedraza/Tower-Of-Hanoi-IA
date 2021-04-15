package smai.domain;

public class Instance {
    private State initialState, targetState;
    
    public Instance() {
        this.initialState = null;
        this.targetState = null;
    }

    public Instance(State initialState, State targetState) {
        this.initialState = initialState;
        this.targetState = targetState;
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
    
}
