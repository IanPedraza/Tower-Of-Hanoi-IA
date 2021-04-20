package smai.common.utils;

import java.util.LinkedList;
import smai.domain.Node;

public class PathFinder {

    private static PathFinder sharedInstance;

    private PathFinder() {
    }

    public static PathFinder getInstance() {
        if (sharedInstance == null) {
            sharedInstance = new PathFinder();
        }

        return sharedInstance;
    }
    
    public final LinkedList<Node> findPath(Node finalNode) {
        LinkedList<Node> path = findPathIterative(finalNode);
        return path;
    }

    private LinkedList<Node> findPathRecursive(Node node, LinkedList<Node> path) {
        path.add(0, node);

        if (node == null || node.getParent() == null) {
            return path;
        } else {
            return findPathRecursive(node.getParent(), path);
        }
    }

    private LinkedList<Node> findPathIterative(Node node) {
        LinkedList<Node> path = new LinkedList();
        path.add(node);

        Node parent = node.getParent();

        while (parent != null) {
            path.add(0, parent);
            parent = parent.getParent();
        }

        return path;
    }

}
