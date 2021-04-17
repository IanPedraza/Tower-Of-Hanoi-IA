package smai.data.datasources;

import smai.common.utils.Callback;
import smai.domain.Answer;
import smai.domain.Instance;

public abstract class SearchLocalDataSource extends Thread {
    
    private Instance instance;
    private Callback<Answer> callback;
    
    protected abstract void process(Instance instance, Callback<Answer> callback);
    
    @Override
    public void run() {
        try {
            process(instance, callback);
        } catch (Exception e) {
            if (callback != null) callback.onFailed(e);
        }
    }

    public final void resolve(Instance instance, Callback<Answer> callback) {
        this.instance = instance;
        this.callback = callback;
        this.start();
    }    

}
