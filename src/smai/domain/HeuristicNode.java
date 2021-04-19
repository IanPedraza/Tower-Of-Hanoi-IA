package smai.domain;

public class HeuristicNode extends Node {
    
    private int pathCost;
    private int pathCostPlusHeuristic;

    public HeuristicNode(State state, Node parent, Operator operator) {
        super(state, parent, operator);
    }
    
    public HeuristicNode(State state) {
        super(state);
        this.pathCost = 0;
        this.pathCostPlusHeuristic = 0;
    }

    public int getPathCost() {
        return pathCost;
    }

    public void setPathCost(int pathCost) {
        this.pathCost = pathCost;
    }

    public int getPathCostPlusHeuristic() {
        return pathCostPlusHeuristic;
    }

    public void setPathCostPlusHeuristic(int pathCostPlusHeuristic) {
        this.pathCostPlusHeuristic = pathCostPlusHeuristic;
    }
    
}
