package smai.domain;

public class HeuristicNode extends Node {

    private int pathCost;
    private int heuristic;

    public HeuristicNode(State state, Node parent, Operator operator) {
        super(state, parent, operator);
    }

    public HeuristicNode(State state) {
        super(state);
        this.pathCost = 0;
        this.heuristic = 0;
    }

    public int getPathCost() {
        return pathCost;
    }

    public void setPathCost(int pathCost) {
        this.pathCost = pathCost;
    }

    public int getPathCostPlusHeuristic() {
        return pathCost + heuristic;
    }

    public int getHeuristic() {
        return heuristic;
    }

    public void setHeuristic(int heuristic) {
        this.heuristic = heuristic;
    }

}
