package smai.data.renders;

import java.awt.Color;
import java.awt.Graphics;
import java.util.LinkedList;
import smai.data.datasources.AnimationDataSource;
import smai.data.datasources.AnimationListener;
import smai.domain.Instance;
import smai.domain.Node;

public class StepAnimation extends AnimationDataSource {

    private int fps;
    private int delay;
    private boolean isPaused;
    private boolean hasFinished;

    public StepAnimation(AnimationListener callback) {
        super(callback);
        this.fps = 10;
        this.isPaused = false;
        this.hasFinished = false;
        this.setDelay();
    }

    @Override
    public void start() {
        this.isPaused = false;
        this.hasFinished = false;
    }

    @Override
    public void render() {
        LinkedList<Node> path = new LinkedList();
        path.addAll(answer.getPath());       

        while (!hasFinished) {
            while (!isPaused && !path.isEmpty()) {
                clear();
                render(path.removeFirst(), answer.getInstance());
                sleep();
            }

            if (path.isEmpty()) {
                this.hasFinished = true;
            }
        }
    }
    
    @Override
    public void pause() {
        this.isPaused = true;
    }

    @Override
    public void resume() {
        this.isPaused = false;
    }

    @Override
    protected void stop() {
        this.hasFinished = true;
        this.isPaused = true;
    }

    public void setFps(int fps) {
        this.fps = fps;
        setDelay();
    }

    public int getFps() {
        return fps;
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
    
    private void render(Node step, Instance instance){
        this.canvas.render(step, instance);
    };

    private void clear() {
        Graphics graphics = canvas.getGraphics();        
        graphics.setColor(Color.WHITE);
        graphics.fillRect(0, 0, canvas.getWidth(), canvas.getHeight());
    }
    
}
