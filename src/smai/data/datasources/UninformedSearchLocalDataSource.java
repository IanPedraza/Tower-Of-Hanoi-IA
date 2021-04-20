package smai.data.datasources;

import java.util.LinkedList;
import smai.data.Callback;
import smai.domain.Response;
import smai.domain.Instance;
import smai.domain.Node;

public abstract class UninformedSearchLocalDataSource implements Runnable {

    private Instance instance;
    private Callback<Response> callback;
    private Thread thread;

    protected abstract void process(Instance instance, Callback<Response> callback);

    @Override
    public void run() {
        try {
            process(instance, callback);
        } catch (Exception e) {
            if (callback != null) {
                callback.onFailed(e);
            }
        }
    }

    public final void resolve(Instance instance, Callback<Response> callback) {
        this.instance = instance;
        this.callback = callback;
        
        this.thread = new Thread(this);
        this.thread.start();
    }

}
