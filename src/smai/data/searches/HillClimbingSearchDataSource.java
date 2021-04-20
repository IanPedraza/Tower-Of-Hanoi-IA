package smai.data.searches;

import java.util.LinkedList;
import smai.common.utils.PathFinder;
import smai.data.Callback;
import smai.common.utils.TicToc;
import smai.data.datasources.InformedSearchLocalDataSource;
import smai.domain.Response;
import smai.domain.Heuristic;
import smai.domain.HeuristicNode;
import smai.domain.Instance;
import smai.domain.Successor;

public class HillClimbingSearchDataSource extends InformedSearchLocalDataSource {
    
    @Override
    protected void process(Instance instance, Heuristic heuristic, Callback<Response> callback) {
        Response response = new Response(instance);

        TicToc.getInstance().tic();

        HeuristicNode currentNode = new HeuristicNode(instance.getInitialState());
        currentNode.setHeuristic(heuristic.getHeuristic(instance.getInitialState()));
        
        int analyzedNodes = 0;
        LinkedList<HeuristicNode> successors = new LinkedList();

        while (currentNode != null) {
            analyzedNodes++;

            if (instance.isFinalState(currentNode.getState())) {
                response.setElapsedTime(TicToc.getInstance().toc());
                response.setPath(PathFinder.getInstance().findPath(currentNode));
                response.hasSolution(true);
                response.setAnalyzedNodes(analyzedNodes);
                callback.onSuccess(response);
                break;
            }

            successors.clear();            
            
            for (Successor successor : currentNode.getState().getSuccessors(instance.getOperators())) {
                HeuristicNode successorNode = new HeuristicNode(successor.getState(), currentNode, successor.getOperator());
                int h = heuristic.getHeuristic(successor.getState());
                successorNode.setHeuristic(h); 
                successors.add(successorNode);
            }
            
            currentNode = best(successors, currentNode.getHeuristic());             
        }

        if (!response.hasSolution()) {
            response.setElapsedTime(TicToc.getInstance().toc());
            callback.onSuccess(response);
        }
    }
    
    private HeuristicNode best(LinkedList<HeuristicNode> nodes, int minHeuristic) {
        HeuristicNode best = null;
        
        for (HeuristicNode node : nodes) {
            if (node.getHeuristic() < minHeuristic) {
                best = node;
                minHeuristic = node.getHeuristic();
            }
        }
        
        return best;
    }
    
}
