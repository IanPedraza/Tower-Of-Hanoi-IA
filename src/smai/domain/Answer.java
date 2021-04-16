package smai.domain;

import java.util.ArrayList;

public class Answer {

    private boolean hasAnswer;
    private Instance instance;
    private final ArrayList<Node> tree;
    private ArrayList<Node> path;
    private long elapsedTime;
    private int analyzedNodes;

    public Answer(Instance instance) {
        this.hasAnswer = false;
        this.tree = new ArrayList();
        this.instance = instance;
        this.elapsedTime = 0L;
        this.analyzedNodes = 0;
        this.path = null;
    }

    public Answer() {
        this.hasAnswer = false;
        this.tree = new ArrayList();
        this.instance = null;
        this.analyzedNodes = 0;
        this.path = null;
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

    public ArrayList<Node> getTree() {
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

    public Instance getInstance() {
        return instance;
    }

    public void setInstance(Instance instance) {
        this.instance = instance;
    }

    public int getAnalyzedNodes() {
        return analyzedNodes;
    }

    public void setAnalyzedNodes(int analyzedNodes) {
        this.analyzedNodes = analyzedNodes;
    }

    public ArrayList<Node> findPath(Node node, ArrayList<Node> path) {
        path.add(0, node);

        if (node == null || node.getParent() == null) {
            return path;
        } else {
            return findPath(node.getParent(), path);
        }
    }

    public ArrayList<Node> findPath(State finalState) {
        Node resultNode = this.getNode(finalState);
        this.path = findPath(resultNode, new ArrayList());
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
