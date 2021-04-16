package smai.data.searches;

import java.util.LinkedList;
import smai.common.utils.TicToc;
import smai.data.datasources.SearchLocalDataSource;
import smai.domain.Answer;
import smai.domain.Successor;

public class BreadthSearchDataSource extends SearchLocalDataSource {

    @Override
    public void resolve() {
        Answer answer = new Answer();

        TicToc.getInstance().markStart();

        LinkedList<smai.domain.State> open = new LinkedList();
        LinkedList<smai.domain.State> closed = new LinkedList();
        LinkedList<smai.domain.State> successors = new LinkedList();

        smai.domain.State currentState = instance.getInitialState();
        answer.addNode(currentState);
        open.add(currentState);

        while (!open.isEmpty()) {
            currentState = open.remove(0);
            closed.add(currentState);

            if (instance.isFinalState(currentState)) {
                answer.findPath(currentState);
                answer.hasAnswer(true);
                answer.setAnalyzedNodes(closed.size());
                answer.setElapsedTime(TicToc.getInstance().getElapsedTime());
                
                callback.onSuccess(answer);
                break;
            }

            successors.clear();

            for (Successor successor : this.getSuccessors(currentState, instance.getOperators())) {
                if (!open.contains(successor.getState()) && !closed.contains(successor.getState())) {
                    successors.add(successor.getState());
                    answer.addNode(currentState, successor);
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
