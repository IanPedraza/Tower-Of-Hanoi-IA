package smai.data.datasources;

import java.util.ArrayList;
import smai.common.utils.Callback;
import smai.domain.Answer;
import smai.domain.Instance;
import smai.domain.Operator;
import smai.domain.Successor;

public abstract class SearchLocalDataSource extends Thread {
    
    protected Instance instance;
    protected Callback<Answer> callback;
    
    public abstract void resolve();
    
    @Override
    public void run() {
        try {
            resolve();
        } catch (Exception e) {
            if (callback != null) callback.onFailed(e);
        }
    }

    public final void resolve(Instance instance, Callback<Answer> callback) {
        this.instance = instance;
        this.callback = callback;
        this.start();
    }    
    
    // TODO: move to another place
    public final ArrayList<Successor> getSuccessors(smai.domain.State state, Operator[] operators) {
        ArrayList<Successor> successors = new ArrayList();

        for (Operator operator : operators) {
            Successor successor = operator.apply(state);

            if (successor.getState() != null) {
                successors.add(successor);
            }
        }

        return successors;
    }

}
