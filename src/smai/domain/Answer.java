package smai.domain;

import java.util.LinkedList;

public class Answer {

    private boolean hasAnswer;
    private final LinkedList<Node> tree;
    private LinkedList<Node> path;
    private long elapsedTime;
    private int analyzedNodes;
    private Instance instance;

    public Answer(Instance instance) {
        this.hasAnswer = false;
        this.tree = new LinkedList();
        this.analyzedNodes = 0;
        this.path = null;
        this.instance = instance;
    }
    
    public Answer() {
        this.hasAnswer = false;
        this.tree = new LinkedList();
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

    public boolean hasAnswer() {
        return hasAnswer;
    }

    public void hasAnswer(boolean hasAnswer) {
        this.hasAnswer = hasAnswer;
    }

    public LinkedList<Node> getTree() {
        return tree;
    }

    public void addNode(State state) {
        Node node = new Node(state);

        if (!tree.contains(node)) {
            this.tree.add(node);
        }
    }

    public void addNode(State parent, Successor successor) {
        Node parentNode = getNode(parent);

        if (parentNode == null) {
            parentNode = new Node(parent);
            this.tree.add(parentNode);
        }

        Node node = new Node(successor.getState(), parentNode, successor.getOperator());

        if (!tree.contains(node)) {
            this.tree.add(node);
        }
    }

    public void addNode(State state, State parent, Operator operator) {
        Node parentNode = getNode(parent);

        if (parentNode == null) {
            parentNode = new Node(parent);
            this.tree.add(parentNode);
        }

        Node node = new Node(state, parentNode, operator);

        if (!tree.contains(node)) {
            this.tree.add(node);
        }
    }

    public Node getNode(State state) {
        int index = tree.indexOf(new Node(state));
        return tree.get(index);
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

    public LinkedList<Node> findPath(Node node, LinkedList<Node> path) {
        path.add(0, node);

        if (node == null || node.getParent() == null) {
            return path;
        } else {
            return findPath(node.getParent(), path);
        }
    }

    public LinkedList <Node> findPath(State finalState) {
        Node resultNode = this.getNode(finalState);
        this.path = findPath(resultNode, new LinkedList());
        return path;
    }

    @Override
    public String toString() {
        String value = "";

        if (!hasAnswer || path == null || path.isEmpty()) {
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
