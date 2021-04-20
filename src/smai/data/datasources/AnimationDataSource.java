package smai.data.datasources;

import smai.data.AnimationListener;
import smai.data.animations.AnimationPanel;
import smai.domain.Response;

public abstract class AnimationDataSource implements Runnable {

    private Thread thread;
    protected Response response;
    protected AnimationPanel canvas;
    protected AnimationListener callback;

    protected AnimationDataSource(AnimationListener callback) {
        this.callback = callback;
        thread = new Thread(this);
    }

    protected abstract void start();

    protected abstract void render();

    public abstract void pause();

    public abstract void resume();

    protected abstract void stop();

    public final void play() {
        if (response == null || canvas == null) {
            if (callback != null) callback.onAnimationError(new Exception("Response or canvas undefined"));
            return;
        }

        this.resume();
    }

    public final void play(Response response, AnimationPanel canvas) {
        this.response = response;
        this.canvas = canvas;

        try {
            if (thread.isAlive()) {
                kill();
            }

            thread = new Thread(this);
            thread.start();
        } catch (Exception e) {
            this.callback.onAnimationError(e);
        }
    }

    private void kill() throws InterruptedException {
        stop();
        thread.join();
    }

    @Override
    public final void run() {
        start();
        render();
        callback.onAnimationComplete();
    }

}
