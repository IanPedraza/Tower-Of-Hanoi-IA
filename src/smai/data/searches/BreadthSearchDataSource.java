package smai.data.searches;

import java.util.LinkedList;
import smai.common.utils.Callback;
import smai.common.utils.TicToc;
import smai.data.datasources.UninformedSearchLocalDataSource;
import smai.domain.Answer;
import smai.domain.Instance;
import smai.domain.Node;
import smai.domain.Successor;

public class BreadthSearchDataSource extends UninformedSearchLocalDataSource {

    @Override
    public void process(Instance instance, Callback<Answer> callback) {
        Answer answer = new Answer(instance);

        TicToc.getInstance().markStart();

        LinkedList<Node> open = new LinkedList();
        LinkedList<Node> closed = new LinkedList();
        LinkedList<Node> successors = new LinkedList();

        Node currentNode = new Node(instance.getInitialState());
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
                Node successorNode = new Node(successor.getState(), currentNode, successor.getOperator());
                
                if (!open.contains(successorNode) && !closed.contains(successorNode)) {
                    successors.add(successorNode);
                }
            }

            open.addAll(successors);
        }

        if (!answer.hasAnswer() && open.isEmpty()) {
            answer.setElapsedTime(TicToc.getInstance().getElapsedTime());
            callback.onSuccess(answer);
        }
    }

}
