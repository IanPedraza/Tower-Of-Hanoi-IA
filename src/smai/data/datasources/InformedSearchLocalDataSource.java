package smai.data.datasources;

import java.util.LinkedList;
import smai.common.utils.Callback;
import smai.domain.Answer;
import smai.domain.Heuristic;
import smai.domain.Instance;
import smai.domain.Node;

public abstract class InformedSearchLocalDataSource extends Thread {    
    
    private Instance instance;
    private Callback<Answer> callback;
    private Heuristic heuristic;
    
    protected abstract void process(Instance instance, Heuristic heuristic, Callback<Answer> callback);
    
    @Override
    public void run() {
        try {
            process(instance, heuristic, callback);
        } catch (Exception e) {
            if (callback != null) callback.onFailed(e);
        }
    }

    public final void resolve(Instance instance, Heuristic heuristic, Callback<Answer> callback) {
        this.instance = instance;
        this.callback = callback;
        this.heuristic = heuristic;
        this.start();
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

    protected final LinkedList<Node> findPath(Node finalNode) {
        LinkedList<Node> path = findPathIterative(finalNode);
        
        return path;
    }
    
}
