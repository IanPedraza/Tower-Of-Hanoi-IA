package smai.data.datasources;

import smai.common.utils.PathFinder;
import smai.data.Callback;
import smai.domain.Response;
import smai.domain.Heuristic;
import smai.domain.Instance;

public abstract class InformedSearchLocalDataSource implements Runnable {

    private Instance instance;
    private Callback<Response> callback;
    private Heuristic heuristic;
    private Thread thread;

    protected abstract void process(Instance instance, Heuristic heuristic, Callback<Response> callback);

    @Override
    public void run() {
        try {
            process(instance, heuristic, callback);
        } catch (Exception e) {
            if (callback != null) {
                callback.onFailed(e);
            }
        }
    }

    public final void resolve(Instance instance, Heuristic heuristic, Callback<Response> callback) {
        this.instance = instance;
        this.callback = callback;
        this.heuristic = heuristic;        
        
        this.thread = new Thread(this);
        this.thread.start();
    }

}
