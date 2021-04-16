package smai.domain;

public class Successor {
    private State state;
    private Operator operator;

    public Successor(State state, Operator operator) {
        this.state = state;
        this.operator = operator;
    }
    
    public Successor() {
        this.state = null;
        this.operator = null;
    }

    public State getState() {
        return state;
    }

    public void setState(State state) {
        this.state = state;
    }

    public Operator getOperator() {
        return operator;
    }

    public void setOperator(Operator operator) {
        this.operator = operator;
    }
    
    
    
}
