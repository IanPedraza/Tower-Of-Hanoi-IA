package smai.domain;

public class Node {

    private Node parent;
    private State state;
    private Operator operator;

    public Node() {
        this.parent = null;
        this.state = null;
        this.operator = null;
    } 
    
    public Node(State state, Node parent, Operator operator) {
        this.parent = parent;
        this.state = state;
        this.operator = operator;
    }

    public Node(State state) {
        this.parent = null;
        this.state = state;
        this.operator = null;
    }

    public Node getParent() {
        return parent;
    }

    public void setParent(Node parent) {
        this.parent = parent;
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

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Node) {
            Node node = (Node) obj;
            return this.state.equals(node.state);
        }
        
        return false;
    }
    
}
