package smai.data.animations;

import java.util.LinkedList;
import smai.data.datasources.AnimationDataSource;
import smai.data.AnimationListener;
import smai.domain.Instance;
import smai.domain.Node;

public class StepAnimation extends AnimationDataSource {

    private boolean isPaused;
    private boolean hasFinished;

    public StepAnimation(AnimationListener callback) {
        super(callback);
        this.isPaused = false;
        this.hasFinished = false;
    }

    @Override
    public void start() {
        this.isPaused = false;
        this.hasFinished = false;
    }

    @Override
    public void render() {
        LinkedList<Node> path = new LinkedList();
        path.addAll(response.getPath());

        while (!hasFinished) {
            while (!isPaused && !path.isEmpty()) {
                render(path.removeFirst(), response.getInstance());
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

    private void render(Node step, Instance instance) {
        this.canvas.render(step, instance);
    }
;

}
