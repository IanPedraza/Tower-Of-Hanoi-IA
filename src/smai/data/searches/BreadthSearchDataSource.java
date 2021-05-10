package smai.data.searches;

import java.util.LinkedList;
import smai.common.utils.PathFinder;
import smai.data.Callback;
import smai.common.utils.TicToc;
import smai.data.datasources.UninformedSearchLocalDataSource;
import smai.domain.Response;
import smai.domain.Instance;
import smai.domain.Node;
import smai.domain.Successor;

public class BreadthSearchDataSource extends UninformedSearchLocalDataSource {

    @Override
    public void process(Instance instance, Callback<Response> callback) {
        Response response = new Response(instance);

        TicToc.getInstance().tic();

        LinkedList<Node> open = new LinkedList();
        LinkedList<Node> closed = new LinkedList();
        LinkedList<Node> successors = new LinkedList();

        Node currentNode = new Node(instance.getInitialState());
        open.add(currentNode);

        while (!open.isEmpty()) {
            currentNode = open.remove(0);
            closed.add(currentNode);

            if (instance.isFinalState(currentNode.getState())) {
                response.setPath(PathFinder.getInstance().findPath(currentNode));
                response.setElapsedTime(TicToc.getInstance().toc());
                response.hasSolution(true);
                response.setAnalyzedNodes(closed.size());
                callback.onSuccess(response);
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

        if (!response.hasSolution() && open.isEmpty()) {
            response.setElapsedTime(TicToc.getInstance().toc());
            callback.onSuccess(response);
        }
    }

}
