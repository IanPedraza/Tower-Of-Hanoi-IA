package smai.domain;

public interface Heuristic {
    public int getHeuristic(State currentState);
    public int getCostOfApplyOperator(State state, Operator operator);
}
