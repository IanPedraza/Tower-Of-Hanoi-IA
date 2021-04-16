package smai.data;

import java.util.ArrayList;
import smai.common.utils.Callback;
import smai.domain.Answer;
import smai.domain.Instance;
import smai.domain.SearchMethod;
import smai.domain.State;

public class BreadthSearchDataSource extends SearchMethod implements SearchLocalDataSource {

    @Override
    public void resolve(Instance instance, Callback<Answer> callback) {
        Answer answer = new Answer(instance);

        long initialTime = System.currentTimeMillis();
        
        ArrayList<State> open = new ArrayList();
        ArrayList<State> closed = new ArrayList();
        ArrayList<State> successors = new ArrayList();

        State currentState = instance.getInitialState();
        open.add(currentState);

        while (!open.isEmpty()) {
            currentState = open.remove(0);
            closed.add(currentState);
            answer.addWay(currentState);

            if (instance.isFinalState(currentState)) {
                answer.setHasAnswer(true);
                answer.setElapsedTime(this.getElapsedTime(initialTime));
                callback.onSuccess(answer);
                break;
            }
            
            successors.clear();

            for (State successor : this.getSuccessors(currentState, instance.getOperators())) {
                if (!open.contains(successor) && !closed.contains(successor)) {
                    successors.add(successor);
                }
            }
            
            open.addAll(successors);
        }

        if (!answer.hasAnswer() && open.isEmpty()) {
            answer.setElapsedTime(this.getElapsedTime(initialTime));
            callback.onSuccess(answer);
        }
    }

}
