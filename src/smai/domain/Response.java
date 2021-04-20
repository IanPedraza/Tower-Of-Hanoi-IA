package smai.domain;

import java.util.LinkedList;

public class Response {

    private boolean hasSolution;
    private Instance instance;
    private long elapsedTime;
    private int analyzedNodes;
    private LinkedList<Node> path;

    public Response(Instance instance) {
        this.hasSolution = false;
        this.analyzedNodes = 0;
        this.path = null;
        this.instance = instance;
    }
    
    public Response() {
        this.hasSolution = false;
        this.analyzedNodes = 0;
        this.path = null;
        this.instance = null;
    }

    public Instance getInstance() {
        return instance;
    }

    public void setInstance(Instance instance) {
        this.instance = instance;
    }

    public long getElapsedTime() {
        return elapsedTime;
    }

    public long getElapsedTimeSeconds() {
        return (elapsedTime / 1000) % 60;
    }

    public void setElapsedTime(long elapsedTime) {
        this.elapsedTime = elapsedTime;
    }

    public boolean hasSolution() {
        return hasSolution;
    }

    public void hasSolution(boolean hasSolution) {
        this.hasSolution = hasSolution;
    }

    public int getAnalyzedNodes() {
        return analyzedNodes;
    }

    public void setAnalyzedNodes(int analyzedNodes) {
        this.analyzedNodes = analyzedNodes;
    }

    public LinkedList<Node> getPath() {
        return path;
    }
    
    public void setPath(LinkedList<Node> path) {
        this.path = path;
    }
    
    @Override
    public String toString() {
        String value = "";

        if (!hasSolution || path == null || path.isEmpty()) {
            value += "No path found";
        } else {
            Operator op;

            for (Node node : path) {
                op = node.getOperator();
                
                if (op != null) {
                    value += node.getOperator() + "\n";
                }

//                Node parentNode = node.getParent();
//
//                if (parentNode != null) {
//                    value += parentNode.getState() + "->" + node.getState() + "\n";
//                } else {
//                    value += "->" + node.getState() + "\n";
//                }
            }

            value += "Solved success.\n";
            value += (path.size() - 1) + " stepts found.\n";
            value += this.getAnalyzedNodes() + " nodes analyzed\n";
        }

        value += getElapsedTime() + " ms";
        value += "\n";

        return value;
    }

}
