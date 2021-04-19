package smai.data.searches;

import java.util.Comparator;
import java.util.LinkedList;
import smai.common.utils.Callback;
import smai.common.utils.TicToc;
import smai.data.datasources.InformedSearchLocalDataSource;
import smai.domain.Answer;
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
    protected void process(Instance instance, Heuristic heuristic, Callback<Answer> callback) {
        Answer answer = new Answer(instance);

        TicToc.getInstance().markStart();

        LinkedList<HeuristicNode> open = new LinkedList();
        LinkedList<HeuristicNode> closed = new LinkedList();
        LinkedList<HeuristicNode> successors = new LinkedList();

        HeuristicNode currentNode = new HeuristicNode(instance.getInitialState());
        open.add(currentNode);

        while (!open.isEmpty()) {
            currentNode = open.remove(0);
            closed.add(currentNode);

            if (instance.isFinalState(currentNode.getState())) {
                answer.setPath(findPath(currentNode));
                answer.hasAnswer(true);
                answer.setAnalyzedNodes(closed.size());
                answer.setElapsedTime(TicToc.getInstance().getElapsedTime());
                callback.onSuccess(answer);
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
            
            // Collections.sort(al, Collections.reverseOrder());
            open.addAll(successors);
            successors.sort(comparator);
        }

        if (!answer.hasAnswer() && open.isEmpty()) {
            answer.setElapsedTime(TicToc.getInstance().getElapsedTime());
            callback.onSuccess(answer);
        }
    }
}
