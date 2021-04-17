package smai.data.renders;

import java.awt.Color;
import java.awt.Graphics;
import java.util.LinkedList;
import javax.swing.JPanel;
import smai.data.datasources.AnimationDataSource;
import smai.domain.Answer;
import smai.domain.Instance;
import smai.domain.Node;

public abstract class StepperRenderable extends Thread implements AnimationDataSource {

    private int fps;
    private int delay;
    private boolean isPaused;
    private JPanel canvas;
    private Answer answer;

    public StepperRenderable() {
        this.fps = 60;
        this.isPaused = false;
        this.canvas = null;
        setSleep();
    }

    protected abstract void render(JPanel canvas, Node step, Instance instance);

    public void setFps(int fps) {
        this.fps = fps;
        setSleep();
    }

    private void setSleep() {
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
    
    @Override
    public void play(Answer answer, JPanel panel) {
        this.isPaused = false;
        this.canvas = panel;
        this.answer = answer;
          
        try {
            this.start();
        } catch(Exception e) {
            // ignore
        }
    }

    @Override
    public void puase() {
        this.isPaused = true;
    }

    @Override
    public void run() {
        LinkedList<Node> path = new LinkedList();
        path.addAll(answer.getPath());
        
        while (!isPaused && !path.isEmpty()) {   
            clear();
            render(canvas, path.removeFirst(), answer.getInstance());
            sleep();
        }
    }

}
