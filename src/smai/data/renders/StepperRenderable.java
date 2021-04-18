package smai.data.renders;

import java.awt.Color;
import java.awt.Graphics;
import java.util.LinkedList;
import javax.swing.JPanel;
import smai.data.datasources.AnimationDataSource;
import smai.data.datasources.AnimationListener;
import smai.domain.Instance;
import smai.domain.Node;

public abstract class StepperRenderable extends AnimationDataSource {

    private int fps;
    private int delay;
    private boolean isPaused;
    private boolean hasFinished;

    public StepperRenderable(AnimationListener callback) {
        super(callback);
        this.fps = 60;
        this.isPaused = false;
        this.hasFinished = false;
        this.setDelay();
    }

    protected abstract void render(JPanel canvas, Node step, Instance instance);

    @Override
    public void onStart() {
        this.isPaused = false;
        this.hasFinished = false;
    }

    @Override
    public void onRender() {
        LinkedList<Node> path = new LinkedList();
        path.addAll(answer.getPath());

        while (!hasFinished) {
            while (!isPaused && !path.isEmpty()) {
                clear();
                render(this.canvas, path.removeFirst(), this.answer.getInstance());
                sleep();
            }

            if (path.isEmpty()) {
                this.hasFinished = true;
            }
        }
    }
    
    @Override
    public void onPause() {
        this.isPaused = true;
    }

    @Override
    public void onResume() {
        this.isPaused = false;
    }

    @Override
    public void onStop() {
        this.hasFinished = true;
        this.isPaused = true;
    }

    public void setFps(int fps) {
        this.fps = fps;
        setDelay();
    }

    private void setDelay() {
        this.delay = (int) (1000 / fps);
    }

    private void sleep() {
        try {
            Thread.sleep(delay);
        } catch (InterruptedException ex) {
            // ignored
        }
    }

    private void clear() {
        Graphics graphics = canvas.getGraphics();
        graphics.setColor(Color.WHITE);
        graphics.fillRect(0, 0, canvas.getWidth(), canvas.getHeight());
    }

}
