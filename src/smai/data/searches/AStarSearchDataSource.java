package smai.data.searches;

import java.util.Comparator;
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

public class AStarSearchDataSource extends InformedSearchLocalDataSource {

    private final Comparator comparator;
    
    public AStarSearchDataSource() {
        comparator = (Comparator<HeuristicNode>) (HeuristicNode o1, HeuristicNode o2) -> {
            return ((Integer) o1.getPathCostPlusHeuristic()).compareTo((Integer) o2.getPathCostPlusHeuristic());
        };
    }
    
    @Override
    protected void process(Instance instance, Heuristic heuristic, Callback<Response> callback) {
        Response response = new Response(instance);

        TicToc.getInstance().tic();

        LinkedList<HeuristicNode> open = new LinkedList();
        LinkedList<HeuristicNode> closed = new LinkedList();
        LinkedList<HeuristicNode> successors = new LinkedList();

        HeuristicNode currentNode = new HeuristicNode(instance.getInitialState());
        open.add(currentNode);

        while (!open.isEmpty()) {
            currentNode = open.remove(0);
            closed.add(currentNode);

            if (instance.isFinalState(currentNode.getState())) {
                response.setElapsedTime(TicToc.getInstance().toc());
                response.setPath(PathFinder.getInstance().findPath(currentNode));
                response.hasSolution(true);
                response.setAnalyzedNodes(closed.size());
                callback.onSuccess(response);
                break;
            }

            successors.clear();

            for (Successor successor : currentNode.getState().getSuccessors(instance.getOperators())) {
                HeuristicNode successorNode = new HeuristicNode(successor.getState(), currentNode, successor.getOperator());

                if (!open.contains(successorNode) && !closed.contains(successorNode)) {
                    int h = heuristic.getHeuristic(successorNode.getState());
                    int costApplyOperator = heuristic.getCostOfApplyOperator(successorNode.getState(), successorNode.getOperator());
                    
                    successorNode.setPathCost(currentNode.getPathCost() + costApplyOperator);
                    successorNode.setPathCostPlusHeuristic(currentNode.getPathCost() + costApplyOperator + h);
                    
                    successors.add(successorNode);
                }
            }
            
            open.addAll(successors);
            successors.sort(comparator);
        }

        if (!response.hasSolution() && open.isEmpty()) {
            response.setElapsedTime(TicToc.getInstance().toc());
            callback.onSuccess(response);
        }
    }
}
