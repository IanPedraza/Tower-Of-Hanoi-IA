package smai.data.datasources;

import javax.swing.JPanel;
import smai.domain.Answer;

public abstract class AnimationDataSource implements Runnable {

    protected Answer answer;
    protected JPanel canvas;
    private Thread thread;
    private boolean hasStarted;
    protected AnimationListener callback;
    
    public abstract void onStart();

    public abstract void onRender();

    public abstract void onPause();

    public abstract void onResume();

    public abstract void onStop();

    public AnimationDataSource(AnimationListener callback) {
        this.callback = callback;
        this.thread = new Thread(this);
        this.hasStarted = false;
    }

    public final void play() {
        if (this.answer == null || this.canvas == null) {
            System.out.println("Answer or canvas undefined");
            return;
        }
        
        this.onResume();
    }
    
    public final void play(Answer answer, JPanel canvas) {
        this.answer = answer;
        this.canvas = canvas;

        if (hasStarted) {
            restart();
        } else {
            thread.start();
            hasStarted = true;
        }
    }

    private void restart() {
        try {
            this.onStop();
            thread.join();
            
            this.thread = new Thread(this);
            
            thread.start();
            hasStarted = true;
        } catch (Exception e) {
            this.callback.onAnimationError(e);
        }
    }

    @Override
    public final void run() {
        this.onStart();
        this.onRender();
        this.callback.onAnimationComplete();
    }

}
